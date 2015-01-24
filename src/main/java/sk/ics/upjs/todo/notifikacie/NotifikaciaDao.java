package sk.ics.upjs.todo.notifikacie;

import java.util.List;

public interface NotifikaciaDao {
    
    public List<Notifikacia> dajVsetkyNotifikacie();
    
    public void pridajNotifikaciu(long idUlohy);
    
    public void vymazNotifikaciu(long idUlohy);
}
