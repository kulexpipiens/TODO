package sk.ics.upjs.todo.home;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class FilterRowMapper implements RowMapper<Filter> {

    KategoriaRowMapper kategoriaRowMapper = new KategoriaRowMapper();

    @Override
    public Filter mapRow(ResultSet rs, int i) throws SQLException {
        Filter filter = new Filter();
        filter.setId(rs.getInt("filter_id"));
        filter.setNazov(rs.getString("filter_nazov"));
        filter.setDatumOd(rs.getString("datumOd"));
        filter.setDatumDo(rs.getString("datumDo"));
        filter.setPriorita(rs.getString("priorita"));
        filter.setStav(rs.getBoolean("stav"));
        filter.setKategoria(kategoriaRowMapper.mapRow(rs, i));
        return filter;
    }

}
