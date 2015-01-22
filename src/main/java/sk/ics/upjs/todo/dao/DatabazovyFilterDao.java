package sk.ics.upjs.todo.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.ics.upjs.todo.entity.Filter;
import sk.ics.upjs.todo.home.FilterRowMapper;

public class DatabazovyFilterDao implements FilterDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String tabulkaZDatabazy = "FILTRE";
    private static final FilterRowMapper filterRowMapper = new FilterRowMapper();

    public DatabazovyFilterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Filter> dajVsetky() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy
                + " JOIN KATEGORIE ON FILTRE.kategoria_id = KATEGORIE.kategoria_id"
                + " WHERE FILTRE.vlastnik='"
                + PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno() + "'",
                filterRowMapper);
    }

    @Override
    public void pridajFilter(Filter filter) {
        String sql = "INSERT INTO " + tabulkaZDatabazy + "\n"
                + "(filter_nazov,priorita,datumOd,datumDo,\n"
                + "kategoria_id,stav,vlastnik) \n"
                + "VALUES(?,?,?,?,?,?,?)\n";
        String stav = "";
        if (!filter.isStav()) {
            stav = "0";
        } else {
            stav = "1";
        }

        jdbcTemplate.update(sql, filter.getNazov(), filter.getPriorita(),
                filter.getDatumOd(), filter.getDatumDo(),
                filter.getKategoria().getId(), stav,
                PrihlasovaciARegistrovaciServis.INSTANCE.getPouzivatel().getMeno());
    }

    @Override
    public void vymazFilter(Filter filter) {
        jdbcTemplate.update("DELETE FROM " + tabulkaZDatabazy
                + " WHERE filter_id = ?", filter.getId());
    }

    @Override
    public void upravFilter(Filter filter) {
        String dopyt = "UPDATE " + tabulkaZDatabazy + "\n"
                + " SET filter_nazov = ?,\n"
                + " priorita = ?,\n"
                + " datumOd = ?,\n"
                + " datumDo = ?,\n"
                + " kategoria_id = ?,\n"
                + " stav = ?\n"
                + " WHERE filter_id = ?\n";
        String stav = new String();
        if (!filter.isStav()) {
            stav = "0";
        } else {
            stav = "1";
        }

        jdbcTemplate.update(dopyt, filter.getNazov(),
                filter.getPriorita(), filter.getDatumOd(), filter.getDatumDo(),
                filter.getKategoria().getId(), stav, filter.getId());
    }

}
