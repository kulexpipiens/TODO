package sk.ics.upjs.todo.rowmappery;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sk.ics.upjs.todo.entity.Pouzivatel;

public class PouzivatelRowMapper implements RowMapper<Pouzivatel> {

    @Override
    public Pouzivatel mapRow(ResultSet rs, int i) throws SQLException {
        Pouzivatel pouzivatel = new Pouzivatel();
        
        pouzivatel.setMeno(rs.getString("Meno"));
        pouzivatel.setHeslo(rs.getString("Heslo"));
        pouzivatel.setMail(rs.getString("Mail"));
        pouzivatel.setChceNotifikacie(rs.getBoolean("chce_notifikacie"));
        pouzivatel.setDobaNotifikacie(rs.getInt("doba_notifikacie"));
        
        return pouzivatel;
    }
    
}
