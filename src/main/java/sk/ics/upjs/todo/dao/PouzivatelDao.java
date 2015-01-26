package sk.ics.upjs.todo.dao;

import sk.ics.upjs.todo.entity.Pouzivatel;
import sk.ics.upjs.todo.exceptions.NeplatneRegistracneMenoException;
import sk.ics.upjs.todo.exceptions.ZleMenoAleboHesloException;

public interface PouzivatelDao {
    
    // prihlasi pouzivatela, ktory ma dane meno a heslo (ak su spravne)
    Pouzivatel prihlas(String meno, String heslo) throws ZleMenoAleboHesloException;
    
    // registruje noveho pouzivatela
    void registruj(Pouzivatel pouzivatel) throws NeplatneRegistracneMenoException;
    
    // ulozi nove nastavenia notifikacii pre uzivatela
    void upravNotifikacie(Pouzivatel pouzivatel);
      
}
