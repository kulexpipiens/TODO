package sk.upjs.ics.todo.dao;

import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.todo.entity.Pouzivatel;
import sk.upjs.ics.todo.entity.Uloha;
import sk.upjs.ics.todo.rowmappery.UlohaRowMapper;

public class DatabazovyUlohaDao implements UlohaDao {

    private final JdbcTemplate jdbcTemplate;
    // tabulka, s ktorou pracujem
    private static final String tabulkaZDatabazy = "ULOHY";
    private static final String tabulkaSKategoriami = "KATEGORIE";
    private final UlohaRowMapper mapovacUloh = new UlohaRowMapper();
    private final NotifikaciaDao notifikaciaDao = Factory.INSTANCE.notifikaciaDao();

    public DatabazovyUlohaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return vsetky ulohy z databazy
     */
    @Override
    public List<Uloha> dajVsetky() {
        return jdbcTemplate.query("SELECT * FROM \n" + tabulkaZDatabazy
                + " JOIN KATEGORIE ON "
                + tabulkaZDatabazy + ".kategoria_id = "
                + tabulkaSKategoriami + ".kategoria_id\n"
                // zobrazujeme aj vyprsane ulohy, ktore nie su starsie ako 2 dni
                + " WHERE datum >= SUBDATE(curdate(),INTERVAL 2 DAY)\n"
                + " AND " + tabulkaZDatabazy + ".vlastnik='"
                + PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno() + "'"
                + " ORDER BY datum\n", mapovacUloh);
    }

    /**
     * prida ulohu do tabulky s ktorou pracujem, tiez prida id ulohy do zoznamu
     * notifikacii
     *
     * @param uloha
     */
    @Override
    public void pridajUlohu(Uloha uloha) {
        String sql = "INSERT INTO " + tabulkaZDatabazy + "\n"
                + "(uloha_nazov,uloha_popis,priorita,datum,cas,\n"
                + "kategoria_id,stav,trvanie,vlastnik) \n"
                + "VALUES(?,?,?,?,?,?,?,?,?)\n";

        jdbcTemplate.update(sql, uloha.getNazov(),
                uloha.getPopis(),
                uloha.getPriorita(),
                vratStringDatumu(uloha),
                vratStringCasu(uloha),
                uloha.getKategoria().getId(),
                "0",
                uloha.getTrvanie(),
                PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno());

        // je to takto trochu drevorubacske a krajsie by bolo zistovat id cez simpleJdbcInsert
        // a executeAndReturnKey, ale ten hadze vynimky, lebo ma problemy s ukladanim datumu a casu do databazy
        String sqlIdUlohy = "SELECT MAX(uloha_id) FROM " + tabulkaZDatabazy;
        long idUlohy = (long) jdbcTemplate.queryForObject(sqlIdUlohy, new Object[]{}, Long.class);
        notifikaciaDao.pridajNotifikaciu(idUlohy);

    }

    /**
     * vymaze ulohu z tabulky, s ktorou pracujem, a tiez vymaze zaznam v
     * notifikaciach
     *
     * @param uloha
     */
    @Override
    public void vymazUlohu(Uloha uloha) {
        jdbcTemplate.update("DELETE FROM " + tabulkaZDatabazy
                + " WHERE uloha_id = ?", uloha.getId());
        notifikaciaDao.vymazNotifikaciu(uloha.getId());
    }

    /**
     * upravi ulohu a tiez zaznam v notifikaciach
     *
     * @param uloha
     */
    @Override
    public void upravUlohu(Uloha uloha) {
        String dopyt = "UPDATE " + tabulkaZDatabazy + "\n"
                + " SET uloha_nazov = ?,\n"
                + " uloha_popis = ?,\n"
                + " priorita = ?,\n"
                + " datum = ?,\n"
                + " cas = ?,\n"
                + " kategoria_id = ?,\n"
                // ulohe pri uprave nastavime stav nesplnena
                + " stav = 0,\n"
                + " trvanie = ?\n"
                + " WHERE uloha_id = ?\n";

        jdbcTemplate.update(dopyt,
                uloha.getNazov(),
                uloha.getPopis(),
                uloha.getPriorita(),
                vratStringDatumu(uloha),
                vratStringCasu(uloha),
                uloha.getKategoria().getId(),
                uloha.getTrvanie(),
                uloha.getId());

        notifikaciaDao.pridajNotifikaciu(uloha.getId());
    }

    /**
     * oznaci ulohu za splnenu
     *
     * @param uloha
     */
    @Override
    public void oznacZaSplnenu(Uloha uloha) {
        String stav = "1";
        jdbcTemplate.update("UPDATE " + tabulkaZDatabazy
                + " SET stav = ? WHERE uloha_id = ?", stav, uloha.getId());
    }

    /**
     * oznaci ulohu za nesplnenu
     *
     * @param uloha
     */
    @Override
    public void oznacZaNesplnenu(Uloha uloha) {
        String stav = "0";
        jdbcTemplate.update("UPDATE " + tabulkaZDatabazy
                + " SET stav = ? WHERE uloha_id = ?", stav, uloha.getId());
    }

    /**
     * @return zoznam uloh, ktorym konci termin dnes
     */
    @Override
    public List<Uloha> dajDnesne() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy + "\n"
                + " JOIN " + tabulkaSKategoriami + " ON "
                + tabulkaZDatabazy + ".kategoria_id = "
                + tabulkaSKategoriami + ".kategoria_id\n"
                + " WHERE date_format(datum, '%Y-%m-%d')\n"
                + " = date_format(curdate(), '%Y-%m-%d')\n"
                + " AND " + tabulkaZDatabazy + ".vlastnik='"
                + PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno() + "'"
                + " ORDER BY datum;", mapovacUloh);
    }

    /**
     * @return zoznam uloh, ktore treba splnit v tomto tyzdni
     */
    @Override
    public List<Uloha> dajTyzdnove() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy + "\n"
                + " JOIN " + tabulkaSKategoriami + " ON "
                + tabulkaZDatabazy + ".kategoria_id = "
                + tabulkaSKategoriami + ".kategoria_id\n"
                + " WHERE datum >=\n"
                // DAYOFWEEK vrati 1 pre nedelu - ak chceme 0 pre pondelok, musime odpocitat 2 
                + " SUBDATE(curdate(),INTERVAL DAYOFWEEK(curdate())-2 DAY)\n"
                + " AND datum <=\n"
                + " ADDDATE(curdate(), INTERVAL 8 - DAYOFWEEK(curdate()) DAY)\n"
                + " AND " + tabulkaZDatabazy + ".vlastnik='"
                + PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno() + "'"
                + " ORDER BY datum;\n",
                mapovacUloh);
    }

    /**
     * @return zoznam uloh, ktore treba splnit v tomto mesiaci
     */
    @Override
    public List<Uloha> dajMesacne() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy + "\n"
                + " JOIN " + tabulkaSKategoriami + " ON "
                + tabulkaZDatabazy + ".kategoria_id = "
                + tabulkaSKategoriami + ".kategoria_id\n"
                + " WHERE date_format(datum, '%Y-%m')\n"
                + " = date_format(curdate(), '%Y-%m')\n"
                + " AND " + tabulkaZDatabazy + ".vlastnik='"
                + PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno() + "'"
                + " ORDER BY datum;\n", mapovacUloh);

    }

    @Override
    public List<Uloha> dajZCasovehoIntervalu(Calendar datumOd, Calendar datumDo) {
        String retazecOd = dajStringZCalendara(datumOd);
        String retazecDo = dajStringZCalendara(datumDo);

        return jdbcTemplate.query("SELECT * FROM \n" + tabulkaZDatabazy + "\n"
                + " JOIN " + tabulkaSKategoriami + " ON "
                + tabulkaZDatabazy + ".kategoria_id = "
                + tabulkaSKategoriami + ".kategoria_id\n"
                + " WHERE datum >=" + retazecOd
                + " AND datum <= " + retazecDo
                + " AND " + tabulkaZDatabazy + ".vlastnik='"
                + PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno() + "'"
                + " ORDER BY datum\n", mapovacUloh);
    }

    private String dajStringZCalendara(Calendar datum) {
        String mesiac, den;
        if ((datum.get(Calendar.MONTH) + 1) < 10) {
            mesiac = "0" + (datum.get(Calendar.MONTH) + 1);
        } else {
            mesiac = "" + (datum.get(Calendar.MONTH) + 1);
        }
        if (datum.get(Calendar.DAY_OF_MONTH) < 10) {
            den = "0" + datum.get(Calendar.DAY_OF_MONTH);
        } else {
            den = "" + datum.get(Calendar.DAY_OF_MONTH);
        }

        return "" + datum.get(Calendar.YEAR) + mesiac + den;
    }

    private String vratStringDatumu(Uloha uloha) {
        StringBuilder datum = new StringBuilder("");
        datum.append(uloha.getDatum().getYear() + 1900);
        if (uloha.getDatum().getMonth() + 1 < 10) {
            datum.append(0);
        }
        datum.append(uloha.getDatum().getMonth() + 1);
        if (uloha.getDatum().getDate() < 10) {
            datum.append(0);
        }
        datum.append(uloha.getDatum().getDate());
        return datum.toString();
    }

    private String vratStringCasu(Uloha uloha) {
        StringBuilder cas = new StringBuilder("");
        if (uloha.getDatum().getHours() < 10) {
            cas.append(0);
        }
        cas.append(uloha.getDatum().getHours());
        if (uloha.getDatum().getMinutes() < 10) {
            cas.append(0);
        }
        cas.append(uloha.getDatum().getMinutes());
        cas.append("00");
        return cas.toString();
    }
}
