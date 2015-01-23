package sk.ics.upjs.todo;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.ics.upjs.todo.dao.DatabazovyKategoriaDao;
import sk.ics.upjs.todo.dao.Factory;
import sk.ics.upjs.todo.dao.KategoriaDao;
import sk.ics.upjs.todo.dao.PrihlasovaciARegistrovaciServis;
import sk.ics.upjs.todo.entity.Kategoria;
import sk.ics.upjs.todo.entity.Pouzivatel;

public class DatabazovyKategoriaDaoTest {

    private JdbcTemplate jdbcTemplate;

    private KategoriaDao kategoriaDao;
    
    private Pouzivatel pouzivatel;

    private static final int POCET_KATEGORII_V_DATABAZE = 8;

    public DatabazovyKategoriaDaoTest() {
        this.jdbcTemplate = new JdbcTemplate(Factory.INSTANCE.dataSourceTest());
        kategoriaDao = new DatabazovyKategoriaDao(jdbcTemplate);
        
        pouzivatel = new Pouzivatel();
        pouzivatel.setMeno("Admin");
        pouzivatel.setHeslo("qwerty123456");
        PrihlasovaciARegistrovaciServis.INSTANCE.prihlas(pouzivatel);
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
        assertEquals(pocetKategoriiPovodne + 1, zoznamKategorii.size());
    }

    @Test
    public void testUpravKategoriu() {
        List<Kategoria> zoznamKategorii = kategoriaDao.dajVsetky();
        Kategoria kategoriaZmena
                = zoznamKategorii.get(zoznamKategorii.size() - 1);
        kategoriaZmena.setPopis("popis o kategorii zmeneny");

        kategoriaDao.upravKategoriu(kategoriaZmena);

        zoznamKategorii = kategoriaDao.dajVsetky();
        assertEquals("popis o kategorii zmeneny",
                zoznamKategorii.get(zoznamKategorii.size() - 1).getPopis());
    }

    @Test
    public void testVymazKategoriu() {
        List<Kategoria> zoznamKategorii = kategoriaDao.dajVsetky();
        int pocetKategoriiPovodne = zoznamKategorii.size();

        Kategoria kategoriaNaVymazanie = zoznamKategorii.get(pocetKategoriiPovodne - 1);
        kategoriaDao.vymazKategoriu(kategoriaNaVymazanie);

        zoznamKategorii = kategoriaDao.dajVsetky();
        assertEquals(pocetKategoriiPovodne - 1, zoznamKategorii.size());
    }

}
