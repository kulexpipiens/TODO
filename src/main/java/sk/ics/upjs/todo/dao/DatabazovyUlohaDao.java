package sk.ics.upjs.todo.dao;

import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import sk.ics.upjs.todo.home.Uloha;
import sk.ics.upjs.todo.home.UlohaRowMapper;

public class DatabazovyUlohaDao implements UlohaDao {

    private JdbcTemplate jdbcTemplate;
    // tabulka s ktorou pracujem
    private static final String tabulkaZDatabazy = "ULOHY";
    private UlohaRowMapper mapovacUloh = new UlohaRowMapper();

    public DatabazovyUlohaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//vrati vsetky ulohy z databazy
    @Override
    public List<Uloha> dajVsetky() {
        return jdbcTemplate.query("SELECT * FROM \n" + tabulkaZDatabazy
                + " ORDER BY datum\n", mapovacUloh);
    }

//prida ulohu do tabulky s ktorou pracujem
    @Override
    public void pridajUlohu(Uloha uloha) {
        String sql = "INSERT INTO " + tabulkaZDatabazy + "\n"
                + "(uloha_nazov,uloha_popis,priorita,datum,cas,\n"
                + "kategoria_id,kategoria_nazov,kategoria_popis,stav) \n"
                + "VALUES(?,?,?,?,?,?,?,?,?)\n";
        Object[] parametre = {uloha.getNazov(), uloha.getPopis(),
            uloha.getPriorita(), uloha.getDatum(), uloha.getCas(),
            uloha.getKategoria().getId(), uloha.getKategoria().getNazov(),
            uloha.getKategoria().getPopis(), "0"};
        jdbcTemplate.update(sql, parametre);

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
                + " kategoria_nazov = ?,\n"
                + " kategoria_popis = ?,\n"
                + " stav = ?\n"
                + " WHERE uloha_id = ?\n";
        String stav = new String();
        if (uloha.getStav()) {
            stav = "0";
        } else {
            stav = "1";
        }

        Object[] parametre = {uloha.getNazov(), uloha.getPopis(),
            uloha.getPriorita(), uloha.getDatum(), uloha.getCas(),
            uloha.getKategoria().getId(), uloha.getKategoria().getNazov(),
            uloha.getKategoria().getPopis(), stav, uloha.getId()};

        jdbcTemplate.update(dopyt, parametre);
    }

//oznaci ulohu za splnenu
    @Override
    public void oznacZaSplenenu(Uloha uloha) {
        String stav = "1";
        jdbcTemplate.update("update " + tabulkaZDatabazy
                + " set stav=? where uloha_id = ?", stav, uloha.getId());
    }

    //vráti zoznam úloh, ktorým končí termín dnes
    @Override
    public List<Uloha> dajDnesne() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy + "\n"
                + " WHERE date_format(datum, '%Y-%m-%d')\n"
                + " = date_format(curdate(), '%Y-%m-%d')\n"
                + " ORDER BY datum;\n", mapovacUloh);
    }

    //vráti zoznam úloh, ktoré treba splniť do týždňa
    @Override
    public List<Uloha> dajTydnove() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy + "\n"
                + " WHERE datum >=\n"
                + " SUBDATE(curdate(),INTERVAL DAYOFWEEK(curdate())-2 DAY)\n"
                + " AND datum <=\n"
                + " ADDDATE(curdate(), INTERVAL 8 - DAYOFWEEK(curdate()) DAY)\n"
                + " ORDER BY datum;\n",
                mapovacUloh);
    }

    //vráti zoznam úloh, ktorým končí termín do mesiaca
    @Override
    public List<Uloha> dajMesacne() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy + "\n"
                + " WHERE date_format(datum, '%Y-%m')\n"
                + " = date_format(curdate(), '%Y-%m')\n"
                + " ORDER BY datum;\n", mapovacUloh);

    }
}
