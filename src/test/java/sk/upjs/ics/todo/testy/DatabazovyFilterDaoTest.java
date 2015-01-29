package sk.upjs.ics.todo.testy;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.todo.dao.DatabazovyFilterDao;
import sk.upjs.ics.todo.dao.Factory;
import sk.upjs.ics.todo.dao.FilterDao;
import sk.upjs.ics.todo.dao.PrihlasovaciARegistrovaciServis;
import sk.upjs.ics.todo.entity.Filter;
import sk.upjs.ics.todo.entity.Kategoria;
import sk.upjs.ics.todo.entity.Pouzivatel;
import sk.upjs.ics.todo.exceptions.ZleMenoAleboHesloException;

public class DatabazovyFilterDaoTest {

    private static JdbcTemplate jdbcTemplate;

    private static FilterDao filterDao;

    private static final int POCET_FILTROV_V_DATABAZE = 2;

    @BeforeClass
    public static void setUp() throws ZleMenoAleboHesloException, NoSuchAlgorithmException {
        System.setProperty("testovaciRezim", "true");
        jdbcTemplate = new JdbcTemplate(Factory.INSTANCE.dataSource());
        filterDao = new DatabazovyFilterDao(jdbcTemplate);

        PrihlasovaciARegistrovaciServis.INSTANCE.prihlas("Admin", "qwerty123456");
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
        filterPridaj = zoznamFiltrov.get(zoznamFiltrov.size() - 1);
        filterDao.vymazFilter(filterPridaj);
    }

    @Test
    public void testUpravFilter() {
        List<Filter> zoznamFiltrov = filterDao.dajVsetky();
        Filter filterZmena
                = zoznamFiltrov.get(zoznamFiltrov.size() - 1);
        String povodnaPriorita = filterZmena.getPriorita();

        filterZmena.setPriorita("Nízka");

        filterDao.upravFilter(filterZmena);

        zoznamFiltrov = filterDao.dajVsetky();
        assertEquals("Nízka",
                zoznamFiltrov.get(zoznamFiltrov.size() - 1).getPriorita());

        filterZmena.setPriorita(povodnaPriorita);
        filterDao.upravFilter(filterZmena);
    }

    @Test
    public void testVymazFilter() {
        List<Filter> zoznamFiltrov = filterDao.dajVsetky();
        int pocetFiltrovPovodne = zoznamFiltrov.size();

        Filter filterNaVymazanie = zoznamFiltrov.get(pocetFiltrovPovodne - 1);
        filterDao.vymazFilter(filterNaVymazanie);

        zoznamFiltrov = filterDao.dajVsetky();
        assertEquals(pocetFiltrovPovodne - 1, zoznamFiltrov.size());

        filterDao.pridajFilter(filterNaVymazanie);
    }

}
