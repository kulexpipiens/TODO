package sk.upjs.ics.todo.dao;

import java.util.List;
import sk.upjs.ics.todo.entity.Filter;

public interface FilterDao {

    public List<Filter> dajVsetky();

    public void pridajFilter(Filter filter);

    public void vymazFilter(Filter filter);

    public void upravFilter(Filter filter);

}
