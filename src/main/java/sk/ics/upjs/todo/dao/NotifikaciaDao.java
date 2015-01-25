package sk.ics.upjs.todo.dao;

import java.util.List;
import sk.ics.upjs.todo.entity.Notifikacia;

public interface NotifikaciaDao {
    
    public List<Notifikacia> dajVsetkyNotifikacie();
    
    public void pridajNotifikaciu(long idUlohy);
    
    public void vymazNotifikaciu(long idUlohy);
}
