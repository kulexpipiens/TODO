package sk.ics.upjs.todo.home;

import sk.ics.upjs.todo.entity.Uloha;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

public class UlohaRowMapper implements RowMapper<Uloha> {

    KategoriaRowMapper kategoriaRowMapper = new KategoriaRowMapper();

    @Override
    public Uloha mapRow(ResultSet rs, int i) throws SQLException {
        Uloha uloha = new Uloha();
        uloha.setId(rs.getInt("uloha_id"));
        uloha.setNazov(rs.getString("uloha_nazov"));
        uloha.setPopis(rs.getString("uloha_popis"));
        Date datum = new Date(rs.getDate("datum").getYear(), rs.getDate("datum").getMonth(),
                rs.getDate("datum").getDate(), rs.getTime("cas").getHours(), rs.getTime("cas").getMinutes());
        uloha.setDatum(datum);
        uloha.setPriorita(rs.getString("priorita"));
        uloha.setStav(rs.getBoolean("stav"));
        uloha.setKategoria(kategoriaRowMapper.mapRow(rs, i));

        return uloha;
    }

}
