package sk.ics.upjs.todo.dao;

import sk.ics.upjs.todo.entity.Pouzivatel;

public interface PouzivatelDao {
    
    Pouzivatel prihlas(Pouzivatel pouzivatel);
    
    void registruj(Pouzivatel pouzivatel);
    
    
}
