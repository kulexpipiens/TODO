package sk.upjs.ics.todo.rowmappery;

import sk.upjs.ics.todo.entity.Kategoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class KategoriaRowMapper implements RowMapper<Kategoria> {

    @Override
    public Kategoria mapRow(ResultSet rs, int i) throws SQLException {
        Kategoria kategoria = new Kategoria();
        kategoria.setId(rs.getInt("kategoria_id"));
        kategoria.setNazov(rs.getString("kategoria_nazov"));
        kategoria.setPopis(rs.getString("kategoria_popis"));
        return kategoria;
    }

}
