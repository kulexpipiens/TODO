package sk.ics.upjs.todo.notifikacie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

public class NotifikaciaRowMapper implements RowMapper<Notifikacia> {

    @Override
    public Notifikacia mapRow(ResultSet rs, int i) throws SQLException {
        Notifikacia notifikacia = new Notifikacia();
        notifikacia.setId(rs.getLong("id"));
        notifikacia.setIdUlohy(rs.getLong("id_ulohy"));
        notifikacia.setNazov(rs.getString("uloha_nazov"));
        notifikacia.setPopis(rs.getString("uloha_popis"));
        notifikacia.setPriorita(rs.getString("priorita"));
        
        Date datum = new Date(rs.getDate("datum").getYear(), rs.getDate("datum").getMonth(),
                rs.getDate("datum").getDate(), rs.getTime("cas").getHours(), rs.getTime("cas").getMinutes());
        notifikacia.setDatum(datum);
       
        notifikacia.setStav(rs.getBoolean("stav"));
        notifikacia.setTrvanie(rs.getInt("trvanie"));
        notifikacia.setVlastnik(rs.getString("vlastnik"));
        notifikacia.setMail(rs.getString("Mail"));
        notifikacia.setChceNotifikacie(rs.getBoolean("chce_notifikacie"));
        notifikacia.setDobaNotifikacie(rs.getInt("doba_notifikacii"));
        
        return notifikacia;
    }

}
