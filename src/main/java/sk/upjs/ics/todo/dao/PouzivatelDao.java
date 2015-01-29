package sk.upjs.ics.todo.dao;

import sk.upjs.ics.todo.entity.Pouzivatel;
import sk.upjs.ics.todo.exceptions.NeplatneRegistracneMenoException;
import sk.upjs.ics.todo.exceptions.ZleMenoAleboHesloException;

public interface PouzivatelDao {

    // prihlasi pouzivatela, ktory ma dane meno a heslo (ak su spravne)
    Pouzivatel prihlas(String meno, String heslo) throws ZleMenoAleboHesloException;

    // registruje noveho pouzivatela
    void registruj(Pouzivatel pouzivatel) throws NeplatneRegistracneMenoException;

    // ulozi nove nastavenia notifikacii pre uzivatela
    void upravNotifikacie(Pouzivatel pouzivatel);

}
