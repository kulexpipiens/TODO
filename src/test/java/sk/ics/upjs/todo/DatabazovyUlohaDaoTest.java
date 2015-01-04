package sk.ics.upjs.todo;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.ics.upjs.todo.dao.DatabazovyUlohaDao;
import sk.ics.upjs.todo.dao.UlohaDao;
import sk.ics.upjs.todo.home.Kategoria;
import sk.ics.upjs.todo.home.Uloha;

public class DatabazovyUlohaDaoTest {

    private JdbcTemplate jdbcTemplate;

    private UlohaDao ulohaDao;

    private static final int POCET_ULOH_V_DATABAZE = 3;
    private static final int POCET_DNESNYCH_ULOH_V_DATABAZE = 0;
    private static final int POCET_TYZDNOVYCH_ULOH_V_DATABAZE = 0;
    private static final int POCET_MESACNYCH_ULOH_V_DATABAZE = 0;

    public DatabazovyUlohaDaoTest() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://www.db4free.net:3306/todolisttestproj");
        dataSource.setUser("todolisttest");
        dataSource.setPassword("hruska123");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        ulohaDao = new DatabazovyUlohaDao(jdbcTemplate);
    }

    @Test
    public void testDajVsetky() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(POCET_ULOH_V_DATABAZE, zoznamUloh.size());
    }

    @Test
    public void testPridajFilter() {
        Uloha ulohaPridaj = new Uloha();
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        int pocetUlohPovodne = zoznamUloh.size();

        Kategoria kategoria = new Kategoria();
        kategoria.setId(5);
        kategoria.setNazov("Jedlo");
        kategoria.setPopis("o jedle");

        ulohaPridaj.setNazov("Nova Uloha");
        ulohaPridaj.setKategoria(kategoria);
        ulohaPridaj.setPriorita("Vysoká");
        ulohaPridaj.setStav(false);
        ulohaPridaj.setDatum("20150102");
        ulohaPridaj.setCas("120000");
        ulohaPridaj.setPopis("popis o novej ulohe");

        ulohaDao.pridajUlohu(ulohaPridaj);
        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(pocetUlohPovodne + 1, zoznamUloh.size());
    }

    @Test
    public void testUpravFilter() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        Uloha ulohaZmena
                = zoznamUloh.get(zoznamUloh.size() - 1);
        ulohaZmena.setPopis("Zmeneny popis");

        ulohaDao.upravUlohu(ulohaZmena);

        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals("Zmeneny popis",
                zoznamUloh.get(zoznamUloh.size() - 1).getPopis());
    }

    @Test
    public void testVymazFilter() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        int pocetUlohPovodne = zoznamUloh.size();

        Uloha ulohaNaVymazanie = zoznamUloh.get(pocetUlohPovodne - 1);
        ulohaDao.vymazUlohu(ulohaNaVymazanie);

        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(pocetUlohPovodne - 1, zoznamUloh.size());
    }

    @Test
    public void testOznacZaSplnenu() {
        List<Uloha> zoznamUloh = ulohaDao.dajVsetky();
        Uloha ulohaZmena = zoznamUloh.get(0);
        ulohaDao.oznacZaSplenenu(ulohaZmena);

        zoznamUloh = ulohaDao.dajVsetky();
        assertEquals(true, zoznamUloh.get(0).getStav());
    }
    /*
    * POZOR pri nasledujucich testoch, ako plynie cas, tak pocty dnesnych, 
    * tyzdnovych a mesacnych uloh sa meni !!!
    */
    
    @Test
    public void testDajDnesne() {
        List<Uloha> zoznamUloh = ulohaDao.dajDnesne();
        assertEquals(POCET_DNESNYCH_ULOH_V_DATABAZE, zoznamUloh.size());

    }

    @Test
    public void testDajTyzdnove() {
        List<Uloha> zoznamUloh = ulohaDao.dajTydnove();
        assertEquals(POCET_TYZDNOVYCH_ULOH_V_DATABAZE, zoznamUloh.size());
    }

    @Test
    public void testDajMesacne() {
        List<Uloha> zoznamUloh = ulohaDao.dajMesacne();
        assertEquals(POCET_MESACNYCH_ULOH_V_DATABAZE, zoznamUloh.size());
    }

}
