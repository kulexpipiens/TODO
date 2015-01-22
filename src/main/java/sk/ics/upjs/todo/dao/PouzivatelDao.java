package sk.ics.upjs.todo.dao;

import sk.ics.upjs.todo.entity.Pouzivatel;
import sk.ics.upjs.todo.exceptions.NeplatneRegistracneMenoException;
import sk.ics.upjs.todo.exceptions.ZleMenoAleboHesloException;

public interface PouzivatelDao {
    
    Pouzivatel prihlas(Pouzivatel pouzivatel) throws ZleMenoAleboHesloException;
    
    void registruj(Pouzivatel pouzivatel) throws NeplatneRegistracneMenoException;
    
    
}
