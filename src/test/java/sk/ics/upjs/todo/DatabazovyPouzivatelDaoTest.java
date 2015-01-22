package sk.ics.upjs.todo;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.ics.upjs.todo.dao.DatabazovyPouzivatelDao;
import sk.ics.upjs.todo.dao.Factory;
import sk.ics.upjs.todo.dao.PouzivatelDao;
import sk.ics.upjs.todo.entity.Pouzivatel;
import sk.ics.upjs.todo.exceptions.NeplatneRegistracneMenoException;
import sk.ics.upjs.todo.exceptions.ZleMenoAleboHesloException;

public class DatabazovyPouzivatelDaoTest {

    private static JdbcTemplate jdbcTemplate;
    private static PouzivatelDao pouzivateliaDao;

    @BeforeClass
    public static void setUp() {
        jdbcTemplate = new JdbcTemplate(Factory.INSTANCE.dataSourceTest());
        pouzivateliaDao = new DatabazovyPouzivatelDao(jdbcTemplate);
    }

    /**
     * Testujem, ci sa vyhodi vynimka ak zadam pri registracii uz pouzite meno
     */
    @Test(expected = NeplatneRegistracneMenoException.class)
    public void registrujTestPreMenoKtoreExistuje() {
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setMeno("Admin");
        pouzivatel.setHeslo("");
        pouzivateliaDao.registruj(pouzivatel);
    }

    /**
     * Testujem, ci sa podari zaregistrovat uzivatela s menom ake este nie je
     * pouzite
     */
    @Test
    public void registrujTestPreMenoKtoreNeexistuje() {
        // pre istotu dane meno prv vymazem z tabulky
        jdbcTemplate.execute("DELETE FROM UZIVATELIA WHERE Meno='Mato'");

        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setMeno("Mato");
        pouzivatel.setHeslo("hihihi");
        pouzivateliaDao.registruj(pouzivatel);
    }

    /**
     * Ak vsak spravim registraciu znova s tym istym menom, opat ocakavam
     * vynimku
     */
    @Test(expected = NeplatneRegistracneMenoException.class)
    public void registrujTestPreOpetovnuRegistraciu() {
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setMeno("Mato");
        pouzivatel.setHeslo("hihihi");
        pouzivateliaDao.registruj(pouzivatel);
    }

    /**
     * Testujem, ci sa vyhodi vynimka ak zadam pri prihlaseni neregistrovane
     * meno
     */
    @Test(expected = ZleMenoAleboHesloException.class)
    public void prihlasTestPreNeplatneMeno() {
        Pouzivatel pouzivatel = new Pouzivatel();

        pouzivatel.setMeno("Alfonz");
        pouzivatel.setHeslo("password123");

        pouzivateliaDao.prihlas(pouzivatel);
    }

    /**
     * Testujem, ci sa vyhodi vynimka ak zadam pri prihlaseni zle heslo
     */
    @Test(expected = ZleMenoAleboHesloException.class)
    public void prihlasTestPreNeplatneHeslo() {
        Pouzivatel pouzivatel = new Pouzivatel();

        pouzivatel.setMeno("Admin");
        pouzivatel.setHeslo("password123");

        pouzivateliaDao.prihlas(pouzivatel);
    }

    /**
     * Testujem, ci prebehne bez komplikacii prihlasenie so spravnymi udajmi
     */
    @Test
    public void prihlasTestDobreUdaje() {
        Pouzivatel pouzivatel = new Pouzivatel();

        pouzivatel.setMeno("Mato");
        pouzivatel.setHeslo("hihihi");

        pouzivateliaDao.prihlas(pouzivatel);
    }
}
