package sk.ics.upjs.todo.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.ics.upjs.todo.home.Kategoria;
import sk.ics.upjs.todo.home.KategoriaRowMapper;

public class DatabazovyKategoriaDao implements KategoriaDao {

    private JdbcTemplate jdbcTemplate;
    private static final String tabulkaZDatabazy = "KATEGORIE";
    private KategoriaRowMapper kategoriaRowMapper = new KategoriaRowMapper();
    
    public DatabazovyKategoriaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //prida novu kategoriu do tabulky s ktorou pracujem
    @Override
    public void pridajKategoriu(Kategoria kategoria) {
        String sql = "insert into " + tabulkaZDatabazy + "(kategoria_nazov, kategoria_popis) values(?,?)";
        jdbcTemplate.update(sql, kategoria.getNazov(), kategoria.getPopis());
    }

    //vymaze kategoriu z tabulky s ktorou pracujem
    @Override
    public void vymazKategoriu(Kategoria kategoria) {
        jdbcTemplate.update("delete from " + tabulkaZDatabazy
                + " where kategoria_id = ?", kategoria.getId());
    }

    //upravi kategoriu v tabulke s ktorou pracujem
    @Override
    public void upravKategoriu(Kategoria kategoria) {
        String sql = "update " + tabulkaZDatabazy +"\n"
                + " set kategoria_nazov = ?,\n"
                + " kategoria_popis = ?\n"
                + " where kategoria_id = ?;\n";
        jdbcTemplate.update(sql, kategoria.getNazov(), kategoria.getPopis(), kategoria.getId());
    }

    //vrati zoznam kategórií
    @Override
    public List<Kategoria> dajVsetky() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy, kategoriaRowMapper);
    }
}
