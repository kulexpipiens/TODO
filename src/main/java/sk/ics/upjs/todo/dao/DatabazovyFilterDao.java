package sk.ics.upjs.todo.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.ics.upjs.todo.home.Filter;
import sk.ics.upjs.todo.home.FilterRowMapper;

public class DatabazovyFilterDao implements FilterDao {

    private JdbcTemplate jdbcTemplate;
    private static final String tabulkaZDatabazy = "FILTRE";
    private static final FilterRowMapper filterRowMapper = new FilterRowMapper();

    public DatabazovyFilterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Filter> dajVsetky() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy, filterRowMapper);
    }

    @Override
    public void pridajFilter(Filter filter) {
        String sql = "INSERT INTO " + tabulkaZDatabazy + "\n"
                + "(filter_nazov,priorita,datumOd,datumDo,\n"
                + "kategoria_id,kategoria_nazov,kategoria_popis,stav) \n"
                + "VALUES(?,?,?,?,?,?,?,?)\n";
        String stav = "";
        if (!filter.isStav()) {
            stav = "0";
        } else {
            stav = "1";
        }
        long kategoriaId;
        String kategoriaNazov;
        String kategoriaPopis;
// musim rozlisit pripady kvoli null pointer exception
        if (filter.getKategoria() == null) {
            kategoriaId = -1;
            kategoriaNazov = "";
            kategoriaPopis = "";
        } else {
            kategoriaId = filter.getKategoria().getId();
            kategoriaNazov = filter.getKategoria().getNazov();
            kategoriaPopis = filter.getKategoria().getPopis();
        }
        Object[] parametre = {filter.getNazov(), filter.getPriorita(),
            filter.getDatumOd(), filter.getDatumDo(),
            kategoriaId, kategoriaNazov, kategoriaPopis, stav};

        jdbcTemplate.update(sql, parametre);
    }

    @Override
    public void vymazFilter(Filter filter) {
        jdbcTemplate.update("delete from " + tabulkaZDatabazy
                + " where filter_id = ?", filter.getId());
    }

    @Override
    public void upravFilter(Filter filter) {
        String dopyt = "UPDATE " + tabulkaZDatabazy + "\n"
                + " SET filter_nazov = ?,\n"
                + " priorita = ?,\n"
                + " datumOd = ?,\n"
                + " datumDo = ?,\n"
                + " kategoria_id = ?,\n"
                + " kategoria_nazov = ?,\n"
                + " kategoria_popis = ?,\n"
                + " stav = ?\n"
                + " WHERE filter_id = ?\n";
        String stav = new String();
        if (!filter.isStav()) {
            stav = "0";
        } else {
            stav = "1";
        }

        Object[] parametre = {filter.getNazov(),
            filter.getPriorita(), filter.getDatumOd(), filter.getDatumDo(),
            filter.getKategoria().getId(), filter.getKategoria().getNazov(),
            filter.getKategoria().getPopis(), stav, filter.getId()};

        jdbcTemplate.update(dopyt, parametre);
    }

}
