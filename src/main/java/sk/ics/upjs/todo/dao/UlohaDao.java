package sk.ics.upjs.todo.dao;

import java.util.*;
import sk.ics.upjs.todo.entity.Uloha;

public interface UlohaDao {

    public void pridajUlohu(Uloha uloha);

    public void vymazUlohu(Uloha uloha);

    public void upravUlohu(Uloha uloha);

    public List<Uloha> dajVsetky();

    public List<Uloha> dajDnesne();

    public List<Uloha> dajTyzdnove();

    public List<Uloha> dajMesacne();
    
    public List<Uloha> dajZCasovehoIntervalu(Calendar datumOd, Calendar datumDo);

    public void oznacZaSplnenu(Uloha uloha);
    
    public void oznacZaNesplnenu(Uloha uloha);
}
