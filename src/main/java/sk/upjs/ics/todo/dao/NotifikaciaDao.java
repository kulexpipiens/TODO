package sk.upjs.ics.todo.dao;

import java.util.List;
import sk.upjs.ics.todo.entity.Notifikacia;

public interface NotifikaciaDao {

    List<Notifikacia> dajVsetkyNotifikacie();

    void pridajNotifikaciu(long idUlohy);

    void vymazNotifikaciu(long idUlohy);
}
