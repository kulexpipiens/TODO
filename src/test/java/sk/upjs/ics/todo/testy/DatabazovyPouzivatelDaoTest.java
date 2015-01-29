package sk.upjs.ics.todo.testy;

import java.security.NoSuchAlgorithmException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.todo.dao.DatabazovyPouzivatelDao;
import sk.upjs.ics.todo.dao.Factory;
import sk.upjs.ics.todo.dao.PouzivatelDao;
import sk.upjs.ics.todo.entity.Pouzivatel;
import sk.upjs.ics.todo.exceptions.NeplatneRegistracneMenoException;
import sk.upjs.ics.todo.exceptions.ZleMenoAleboHesloException;

public class DatabazovyPouzivatelDaoTest {

    private static JdbcTemplate jdbcTemplate;
    private static PouzivatelDao pouzivateliaDao;

    @BeforeClass
    public static void setUp() {
        System.setProperty("testovaciRezim", "true");
        jdbcTemplate = new JdbcTemplate(Factory.INSTANCE.dataSource());
        pouzivateliaDao = new DatabazovyPouzivatelDao(jdbcTemplate);
    }

    /**
     * Testujem, ci sa vyhodi vynimka, ak zadam pri registracii uz pouzite meno
     *
     * @throws java.security.NoSuchAlgorithmException vrati ak sa nenasiel
     * pozadovanu hashovaci algoritmus
     */
    @Test(expected = NeplatneRegistracneMenoException.class)
    public void registrujTestPreMenoKtoreExistuje() throws NeplatneRegistracneMenoException,
            NoSuchAlgorithmException {
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setMeno("Admin");
        pouzivatel.setHeslo("");
        pouzivatel.setMail("");
        pouzivateliaDao.registruj(pouzivatel);
    }

    /**
     * Testujem, ci sa podari zaregistrovat uzivatela s menom, ake este nie je
     * pouzite
     *
     * @throws java.security.NoSuchAlgorithmException vrati ak sa nenasiel
     * pozadovanu hashovaci algoritmus
     */
    @Test
    public void registrujTestPreMenoKtoreNeexistuje() throws NeplatneRegistracneMenoException,
            NoSuchAlgorithmException {
        // pre istotu dane meno prv vymazem z tabulky
        jdbcTemplate.execute("DELETE FROM UZIVATELIA WHERE Meno='Mato'");

        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setMeno("Mato");
        pouzivatel.setHeslo("hihihi");
        pouzivatel.setMail("mato@hihihi.sk");
        pouzivateliaDao.registruj(pouzivatel);
    }

    /**
     * Ak vsak spravim registraciu znova s tym istym menom, opat ocakavam
     * vynimku
     *
     * @throws java.security.NoSuchAlgorithmException vrati ak sa nenasiel
     * pozadovanu hashovaci algoritmus
     */
    @Test(expected = NeplatneRegistracneMenoException.class)
    public void registrujTestPreOpatovnuRegistraciu() throws NeplatneRegistracneMenoException,
            NoSuchAlgorithmException {
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setMeno("Mato");
        pouzivatel.setHeslo("hihihi");
        pouzivatel.setMail("mato@hihihi.sk");
        pouzivateliaDao.registruj(pouzivatel);
    }

    /**
     * Testujem, ci sa vyhodi vynimka, ak zadam pri prihlaseni neregistrovane
     * meno
     *
     * @throws java.security.NoSuchAlgorithmException vrati ak sa nenasiel
     * pozadovanu hashovaci algoritmus
     */
    @Test(expected = ZleMenoAleboHesloException.class)
    public void prihlasTestPreNeplatneMeno() throws ZleMenoAleboHesloException,
            NoSuchAlgorithmException {
        String meno = "Alfonz";
        String heslo = "password123";

        pouzivateliaDao.prihlas(meno, heslo);
    }

    /**
     * Testujem, ci sa vyhodi vynimka, ak zadam pri prihlaseni zle heslo
     *
     * @throws java.security.NoSuchAlgorithmException vrati ak sa nenasiel
     * pozadovanu hashovaci algoritmus
     */
    @Test(expected = ZleMenoAleboHesloException.class)
    public void prihlasTestPreNeplatneHeslo() throws ZleMenoAleboHesloException,
            NoSuchAlgorithmException {
        String meno = "Admin";
        String heslo = "password123";

        pouzivateliaDao.prihlas(meno, heslo);
    }

    /**
     * Testujem, ci prebehne bez komplikacii prihlasenie so spravnymi udajmi
     *
     * @throws java.security.NoSuchAlgorithmException vrati ak sa nenasiel
     * pozadovanu hashovaci algoritmus
     */
    @Test
    public void prihlasTestDobreUdaje() throws ZleMenoAleboHesloException,
            NoSuchAlgorithmException {
        String meno = "Mato";
        String heslo = "hihihi";

        pouzivateliaDao.prihlas(meno, heslo);
    }
}
