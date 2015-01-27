package sk.ics.upjs.todo.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.ics.upjs.todo.entity.Notifikacia;
import sk.ics.upjs.todo.rowmappery.NotifikaciaRowMapper;

public class DatabazovyNotifikaciaDao implements NotifikaciaDao {

    private final JdbcTemplate jdbcTemplate;
    private static final NotifikaciaRowMapper mapovacNotifikacii = new NotifikaciaRowMapper();
    private static final String nazovTabulky = "NOTIFIKACIE";

    /**
     * Konstruktor, ktory si sam vyrobi JdbcTemplate - kvoli tomu, aby sme na
     * serveri nepotrebovali Factory
     */
    public DatabazovyNotifikaciaDao() {
        jdbcTemplate = new JdbcTemplate(dataSource());
    }

    /**
     * UlohaDao vyuziva NotifikaciaDao, a tak urobime aj taky konstruktor, ktory
     * vie pouzit aj jdbcTemplate z Factory
     *
     * @param jdbcTemplate
     */
    public DatabazovyNotifikaciaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Vsetky notifikacie joinuje aj s udajmi o ulohe a pouzivatelovi, aby sme
     * mali vsetky informacie pre odoslanie emailu
     *
     * @return zoznam vsetkych notifikacii
     */
    @Override
    public List<Notifikacia> dajVsetkyNotifikacie() {
        return jdbcTemplate.query("SELECT * FROM " + nazovTabulky + " n \n"
                + " JOIN ULOHY u ON n.id_ulohy = u.uloha_id \n"
                + " JOIN UZIVATELIA p ON u.vlastnik = p.Meno \n",
                mapovacNotifikacii);
    }

    /**
     * Prida notifikaciu do databazy
     *
     * @param idUlohy id ulohy, na ktoru sa viaze notifikacia
     */
    @Override
    public void pridajNotifikaciu(long idUlohy) {
        // pri uprave ulohy tiez vkladame notifikaciu, lebo sa mohlo stat, ze uz
        // bola odoslana, ale pouzivatel zmenil cas ulohy a bude ju treba poslat znovu,
        // preto najprv vymazeme tu, ktora uz mozno existuje a nahradime ju novou
        vymazNotifikaciu(idUlohy);
        String sqlNotifikacia = "INSERT INTO " + nazovTabulky + " \n"
                + "(id_ulohy)\n"
                + "VALUES(?)\n";
        jdbcTemplate.update(sqlNotifikacia, idUlohy);
    }

    /**
     * Vymaze notifikaciu z databazy
     *
     * @param idUlohy id ulohy, na ktoru sa viaze notifikacia
     */
    @Override
    public void vymazNotifikaciu(long idUlohy) {
        String sqlNotifikacia = "DELETE FROM " + nazovTabulky + " \n"
                + "WHERE id_ulohy = ?";
        jdbcTemplate.update(sqlNotifikacia, idUlohy);
    }

    /**
     * Pri behu na serveri by bolo zbytocne pouzivat Factory, tak si vytvorime
     * novy DataSource
     *
     * @return dataSource
     */
    private MysqlDataSource dataSource() {
        Properties properties = getProperties();
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL((String) properties.get("dataServer"));
        dataSource.setUser((String) properties.get("dataLogin"));
        dataSource.setPassword((String) properties.get("dataPass"));

        return dataSource;
    }

    /**
     * Vrati properties pre pripojenie k databaze a na email
     * @return 
     */
    private Properties getProperties() {
        try {
            String propertiesFile;

            propertiesFile = "/home/todo/todo.properties";

            InputStream in;

            try {
                // ak sme tu, tak sme u Martina
                in = new FileInputStream(propertiesFile);
            } catch (FileNotFoundException e1) {
                // ak sme tu, tak sme u Alice alebo na serveri
                // skusime najprv server
                propertiesFile = "~/todo/todo.properties";
            }
            
            try {
                // ak sme tu, tak sme na serveri
                in = new FileInputStream(propertiesFile);
            }           
            catch (FileNotFoundException e2) {
                // ak sme tu, tak sme u Alice
                propertiesFile = "C:/todo/todo.properties";
            }
            
            in = new FileInputStream(propertiesFile);

            Properties properties = new Properties();
            properties.load(in);

            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Nenašiel sa konfiguračný súbor!");
        }
    }
}
