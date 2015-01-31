package sk.upjs.ics.todo.dao;

import java.util.List;
import sk.upjs.ics.todo.entity.Filter;

public interface FilterDao {

    List<Filter> dajVsetky();

    void pridajFilter(Filter filter);

    void vymazFilter(Filter filter);

    void upravFilter(Filter filter);

}
