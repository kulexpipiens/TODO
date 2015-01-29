package sk.upjs.ics.todo.dao;

import java.util.List;
import sk.upjs.ics.todo.entity.Notifikacia;

public interface NotifikaciaDao {

    public List<Notifikacia> dajVsetkyNotifikacie();

    public void pridajNotifikaciu(long idUlohy);

    public void vymazNotifikaciu(long idUlohy);
}
