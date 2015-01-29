package sk.upjs.ics.todo.testy;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.todo.dao.DatabazovyKategoriaDao;
import sk.upjs.ics.todo.dao.Factory;
import sk.upjs.ics.todo.dao.KategoriaDao;
import sk.upjs.ics.todo.dao.PrihlasovaciARegistrovaciServis;
import sk.upjs.ics.todo.entity.Kategoria;
import sk.upjs.ics.todo.entity.Pouzivatel;
import sk.upjs.ics.todo.exceptions.ZleMenoAleboHesloException;

public class DatabazovyKategoriaDaoTest {

    private static JdbcTemplate jdbcTemplate;

    private static KategoriaDao kategoriaDao;

    private static final int POCET_KATEGORII_V_DATABAZE = 7;

    @BeforeClass
    public static void setUp() throws ZleMenoAleboHesloException, NoSuchAlgorithmException {
        System.setProperty("testovaciRezim", "true");
        jdbcTemplate = new JdbcTemplate(Factory.INSTANCE.dataSource());
        kategoriaDao = new DatabazovyKategoriaDao(jdbcTemplate);

        PrihlasovaciARegistrovaciServis.INSTANCE.prihlas("Admin", "qwerty123456");
    }

    @Test
    public void testDajVsetky() {
        List<Kategoria> zoznamKategorii = kategoriaDao.dajVsetky();
        assertEquals(POCET_KATEGORII_V_DATABAZE, zoznamKategorii.size());
    }

    @Test
    public void testPridajKategoriu() {
        Kategoria kategoriaPridaj = new Kategoria();
        List<Kategoria> zoznamKategorii = kategoriaDao.dajVsetky();
        int pocetKategoriiPovodne = zoznamKategorii.size();

        kategoriaPridaj.setNazov("NovaKategoria");
        kategoriaPridaj.setPopis("popis o novej kategórii");

        kategoriaDao.pridajKategoriu(kategoriaPridaj);
        zoznamKategorii = kategoriaDao.dajVsetky();
        kategoriaPridaj = zoznamKategorii.get(zoznamKategorii.size() - 1);
        assertEquals(pocetKategoriiPovodne + 1, zoznamKategorii.size());

        kategoriaDao.vymazKategoriu(kategoriaPridaj);
    }

    @Test
    public void testUpravKategoriu() {
        List<Kategoria> zoznamKategorii = kategoriaDao.dajVsetky();
        Kategoria kategoriaZmena
                = zoznamKategorii.get(zoznamKategorii.size() - 1);
        String povodnyPopis = kategoriaZmena.getPopis();
        kategoriaZmena.setPopis("popis o kategorii zmeneny");

        kategoriaDao.upravKategoriu(kategoriaZmena);

        zoznamKategorii = kategoriaDao.dajVsetky();
        assertEquals("popis o kategorii zmeneny",
                zoznamKategorii.get(zoznamKategorii.size() - 1).getPopis());

        kategoriaZmena.setPopis(povodnyPopis);
        kategoriaDao.upravKategoriu(kategoriaZmena);
    }

    @Test
    public void testVymazKategoriu() {
        List<Kategoria> zoznamKategorii = kategoriaDao.dajVsetky();
        int pocetKategoriiPovodne = zoznamKategorii.size();

        Kategoria kategoriaNaVymazanie = zoznamKategorii.get(pocetKategoriiPovodne - 1);
        kategoriaDao.vymazKategoriu(kategoriaNaVymazanie);

        zoznamKategorii = kategoriaDao.dajVsetky();
        assertEquals(pocetKategoriiPovodne - 1, zoznamKategorii.size());

        kategoriaDao.pridajKategoriu(kategoriaNaVymazanie);
    }

}
