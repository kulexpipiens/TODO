package sk.upjs.ics.todo.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.todo.entity.Kategoria;
import sk.upjs.ics.todo.rowmappery.KategoriaRowMapper;

public class DatabazovyKategoriaDao implements KategoriaDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String tabulkaZDatabazy = "KATEGORIE";
    private final KategoriaRowMapper kategoriaRowMapper = new KategoriaRowMapper();
    private final PrihlasovaciARegistrovaciServis prihlasovaciARegistrovaciServis = PrihlasovaciARegistrovaciServis.INSTANCE;

    public DatabazovyKategoriaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //prida novu kategoriu do tabulky s ktorou pracujem
    @Override
    public void pridajKategoriu(Kategoria kategoria) {
        String sql = "INSERT INTO " + tabulkaZDatabazy
                + "(kategoria_nazov, kategoria_popis, vlastnik) VALUES(?,?,?)";
        jdbcTemplate.update(sql, kategoria.getNazov(), kategoria.getPopis(),
                prihlasovaciARegistrovaciServis.getPouzivatel().getMeno());
    }

    //vymaze kategoriu z tabulky s ktorou pracujem
    @Override
    public void vymazKategoriu(Kategoria kategoria) {
        jdbcTemplate.update("DELETE FROM " + tabulkaZDatabazy
                + " WHERE kategoria_id = ?", kategoria.getId());
    }

    //upravi kategoriu v tabulke s ktorou pracujem
    @Override
    public void upravKategoriu(Kategoria kategoria) {
        String sql = "UPDATE " + tabulkaZDatabazy + "\n"
                + " SET kategoria_nazov = ?,\n"
                + " kategoria_popis = ?\n"
                + " WHERE kategoria_id = ?;\n";
        jdbcTemplate.update(sql, kategoria.getNazov(), kategoria.getPopis(), kategoria.getId());
    }

    //vrati zoznam kategórií
    @Override
    public List<Kategoria> dajVsetky() {
        return jdbcTemplate.query("SELECT * FROM " + tabulkaZDatabazy
                + " WHERE vlastnik='"
                + prihlasovaciARegistrovaciServis.getPouzivatel().getMeno() + "'",
                kategoriaRowMapper);
    }
}
