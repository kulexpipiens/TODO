package sk.ics.upjs.todo;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.ics.upjs.todo.dao.DatabazovyFilterDao;
import sk.ics.upjs.todo.dao.Factory;
import sk.ics.upjs.todo.dao.FilterDao;
import sk.ics.upjs.todo.entity.Filter;
import sk.ics.upjs.todo.entity.Kategoria;

public class DatabazovyFilterDaoTest {

    private JdbcTemplate jdbcTemplate;

    private FilterDao filterDao;

    private static final int POCET_FILTROV_V_DATABAZE = 3;

    public DatabazovyFilterDaoTest() {
        this.jdbcTemplate = new JdbcTemplate(Factory.INSTANCE.dataSourceTest());
        filterDao = new DatabazovyFilterDao(jdbcTemplate);

    }

    @Test
    public void testDajVsetky() {
        List<Filter> zoznamFiltrov = filterDao.dajVsetky();
        assertEquals(POCET_FILTROV_V_DATABAZE, zoznamFiltrov.size());
    }

    @Test
    public void testPridajFilter() {
        Filter filterPridaj = new Filter();
        List<Filter> zoznamFiltrov = filterDao.dajVsetky();
        int pocetFiltrovPovodne = zoznamFiltrov.size();

        Kategoria kategoria = new Kategoria();
        kategoria.setId(5);
        kategoria.setNazov("Jedlo");
        kategoria.setPopis("o jedle");

        filterPridaj.setNazov("Novy Filter");
        filterPridaj.setKategoria(kategoria);
        filterPridaj.setPriorita("Vysoká");
        filterPridaj.setStav(false);
        filterPridaj.setDatumOd(null);
        filterPridaj.setDatumDo(null);

        filterDao.pridajFilter(filterPridaj);
        zoznamFiltrov = filterDao.dajVsetky();
        assertEquals(pocetFiltrovPovodne + 1, zoznamFiltrov.size());
    }

    @Test
    public void testUpravFilter() {
        List<Filter> zoznamFiltrov = filterDao.dajVsetky();
        Filter filterZmema
                = zoznamFiltrov.get(zoznamFiltrov.size() - 1);
        filterZmema.setPriorita("Nízka");

        filterDao.upravFilter(filterZmema);

        zoznamFiltrov = filterDao.dajVsetky();
        assertEquals("Nízka",
                zoznamFiltrov.get(zoznamFiltrov.size() - 1).getPriorita());
    }

    @Test
    public void testVymazFilter() {
        List<Filter> zoznamFiltrov = filterDao.dajVsetky();
        int pocetFiltrovPovodne = zoznamFiltrov.size();

        Filter filterNaVymazanie = zoznamFiltrov.get(pocetFiltrovPovodne - 1);
        filterDao.vymazFilter(filterNaVymazanie);

        zoznamFiltrov = filterDao.dajVsetky();
        assertEquals(pocetFiltrovPovodne - 1, zoznamFiltrov.size());
    }

}
