package sk.ics.upjs.todo.dao;

import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.ics.upjs.todo.entity.Uloha;
import sk.ics.upjs.todo.home.UlohaRowMapper;

public class DatabazovyUlohaDao implements UlohaDao {

    private final JdbcTemplate jdbcTemplate;
    // tabulka s ktorou pracujem
    private static final String tabulkaZDatabazy = "ULOHY";
    private final UlohaRowMapper mapovacUloh = new UlohaRowMapper();

    public DatabazovyUlohaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//vrati vsetky ulohy z databazy
    @Override
    public List<Uloha> dajVsetky() {
        return jdbcTemplate.query("SELECT * FROM \n" + tabulkaZDatabazy
                + " JOIN KATEGORIE ON ULOHY.kategoria_id = KATEGORIE.kategoria_id\n"
                + " WHERE datum >= SUBDATE(curdate(),INTERVAL 2 DAY)\n"
                + " AND " + tabulkaZDatabazy + ".vlastnik='"
                + PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno() + "'"
                + " ORDER BY datum\n", mapovacUloh);
    }

//prida ulohu do tabulky s ktorou pracujem
    @Override
    public void pridajUlohu(Uloha uloha) {
        String sql = "INSERT INTO " + tabulkaZDatabazy + "\n"
                + "(uloha_nazov,uloha_popis,priorita,datum,cas,\n"
                + "kategoria_id,stav,trvanie,vlastnik) \n"
                + "VALUES(?,?,?,?,?,?,?,?,?)\n";

        jdbcTemplate.update(sql, uloha.getNazov(),
                uloha.getPopis(),
                uloha.getPriorita(),
                vratStringDatumu(uloha), vratStringCasu(uloha),
                uloha.getKategoria().getId(), "0", uloha.getTrvanie(),
                PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno());

    }

//vymaze ulohu z tabulky s ktorou pracujem
    @Override
    public void vymazUlohu(Uloha uloha) {
        jdbcTemplate.update("DELETE FROM " + tabulkaZDatabazy
                + " WHERE uloha_id = ?", uloha.getId());
    }

//uprava ulohy z tabulky
    @Override
    public void upravUlohu(Uloha uloha) {
        String dopyt = "UPDATE " + tabulkaZDatabazy + "\n"
                + " SET uloha_nazov = ?,\n"
                + " uloha_popis = ?,\n"
                + " priorita = ?,\n"
                + " datum = ?,\n"
                + " cas = ?,\n"
                + " kategoria_id = ?,\n"
                + " stav = ?,\n"
                + " trvanie = ?\n"
                + " WHERE uloha_id = ?\n";
        String stav = new String();
        if (uloha.getStav()) {
            stav = "0";
        } else {
            stav = "1";
        }

        jdbcTemplate.update(dopyt,
                new Object[]{uloha.getNazov(), 
                uloha.getPopis(), 
                uloha.getPriorita(),
                vratStringDatumu(uloha), 
                vratStringCasu(uloha),
                uloha.getKategoria().getId(), 
                stav, 
                uloha.getTrvanie(), 
                uloha.getId()});
    }

//oznaci ulohu za splnenu
    @Override
    public void oznacZaSplnenu(Uloha uloha) {
        String stav = "1";
        jdbcTemplate.update("UPDATE " + tabulkaZDatabazy
                + " SET stav=? WHERE uloha_id = ?", stav, uloha.getId());
    }

    @Override
    public void oznacZaNesplnenu(Uloha uloha) {
        String stav = "0";
        jdbcTemplate.update("UPDATE " + tabulkaZDatabazy
                + " SET stav=? WHERE uloha_id = ?", stav, uloha.getId());
    }

    //vráti zoznam úloh, ktorým končí termín dnes
    @Override
    public List<Uloha> dajDnesne() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy + "\n"
                + " JOIN KATEGORIE ON ULOHY.kategoria_id = KATEGORIE.kategoria_id\n"
                + " WHERE date_format(datum, '%Y-%m-%d')\n"
                + " = date_format(curdate(), '%Y-%m-%d')\n"
                + " AND " + tabulkaZDatabazy + ".vlastnik='"
                + PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno() + "'"
                + " ORDER BY datum;\n", mapovacUloh);
    }

    //vráti zoznam úloh, ktoré treba splniť do týždňa
    @Override
    public List<Uloha> dajTyzdnove() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy + "\n"
                + " JOIN KATEGORIE ON ULOHY.kategoria_id = KATEGORIE.kategoria_id\n"
                + " WHERE datum >=\n"
                + " SUBDATE(curdate(),INTERVAL DAYOFWEEK(curdate())-2 DAY)\n"
                + " AND datum <=\n"
                + " ADDDATE(curdate(), INTERVAL 8 - DAYOFWEEK(curdate()) DAY)\n"
                + " AND " + tabulkaZDatabazy + ".vlastnik='"
                + PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno() + "'"
                + " ORDER BY datum;\n",
                mapovacUloh);
    }

    //vráti zoznam úloh, ktorým končí termín do mesiaca
    @Override
    public List<Uloha> dajMesacne() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy + "\n"
                + " JOIN KATEGORIE ON ULOHY.kategoria_id = KATEGORIE.kategoria_id\n"
                + " WHERE date_format(datum, '%Y-%m')\n"
                + " = date_format(curdate(), '%Y-%m')\n"
                + " AND " + tabulkaZDatabazy + ".vlastnik='"
                + PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno() + "'"
                + " ORDER BY datum;\n", mapovacUloh);

    }

    public String vratStringDatumu(Uloha uloha) {
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

    public String vratStringCasu(Uloha uloha) {
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

    @Override
    public List<Uloha> dajZCasovehoIntervalu(Calendar datumOd, Calendar datumDo) {
        String retazecOd = dajStringZCalendara(datumOd);

        String retazecDo = dajStringZCalendara(datumDo);

        return jdbcTemplate.query("SELECT * FROM \n" + tabulkaZDatabazy
                + " JOIN KATEGORIE ON ULOHY.kategoria_id = KATEGORIE.kategoria_id\n"
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

}
