package sk.upjs.ics.todo.dao;

import java.util.*;
import sk.upjs.ics.todo.entity.Uloha;

public interface UlohaDao {

    void pridajUlohu(Uloha uloha);

    void vymazUlohu(Uloha uloha);

    void upravUlohu(Uloha uloha);

    List<Uloha> dajVsetky();

    List<Uloha> dajDnesne();

    List<Uloha> dajTyzdnove();

    List<Uloha> dajMesacne();

    List<Uloha> dajZCasovehoIntervalu(Calendar datumOd, Calendar datumDo);

    void oznacZaSplnenu(Uloha uloha);

    void oznacZaNesplnenu(Uloha uloha);
}
