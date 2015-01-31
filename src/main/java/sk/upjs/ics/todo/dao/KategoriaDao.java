package sk.upjs.ics.todo.dao;

import java.util.List;
import sk.upjs.ics.todo.entity.Kategoria;

public interface KategoriaDao {

    List<Kategoria> dajVsetky();

    void pridajKategoriu(Kategoria kategoria);

    void vymazKategoriu(Kategoria kategoria);

    void upravKategoriu(Kategoria kategoria);

}
