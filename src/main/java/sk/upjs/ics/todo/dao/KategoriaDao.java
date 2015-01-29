package sk.upjs.ics.todo.dao;

import java.util.List;
import sk.upjs.ics.todo.entity.Kategoria;

public interface KategoriaDao {

    public List<Kategoria> dajVsetky();

    public void pridajKategoriu(Kategoria kategoria);

    public void vymazKategoriu(Kategoria kategoria);

    public void upravKategoriu(Kategoria kategoria);

}
