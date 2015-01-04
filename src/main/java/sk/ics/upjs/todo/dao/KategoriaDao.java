package sk.ics.upjs.todo.dao;

import java.util.List;
import sk.ics.upjs.todo.home.Kategoria;

public interface KategoriaDao {
    
    public List<Kategoria> dajVsetky();

    public void pridajKategoriu(Kategoria kategoria);

    public void vymazKategoriu(Kategoria kategoria);

    public void upravKategoriu(Kategoria kategoria);

}
