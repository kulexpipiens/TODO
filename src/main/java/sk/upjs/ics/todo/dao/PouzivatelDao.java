package sk.upjs.ics.todo.dao;

import java.security.NoSuchAlgorithmException;
import sk.upjs.ics.todo.entity.Pouzivatel;
import sk.upjs.ics.todo.exceptions.NeplatneRegistracneMenoException;
import sk.upjs.ics.todo.exceptions.ZleMenoAleboHesloException;

public interface PouzivatelDao {

    // prihlasi pouzivatela, ktory ma dane meno a heslo (ak su spravne)
    Pouzivatel prihlas(String meno, String heslo) throws ZleMenoAleboHesloException,
            NoSuchAlgorithmException;

    // registruje noveho pouzivatela
    void registruj(Pouzivatel pouzivatel) throws NeplatneRegistracneMenoException,
            NoSuchAlgorithmException;

    // ulozi nove nastavenia notifikacii pre uzivatela
    void upravNotifikacie(Pouzivatel pouzivatel);

}
