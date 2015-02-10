package sk.upjs.ics.todo.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.todo.entity.Kategoria;

public enum Factory {

    INSTANCE;

    private UlohaDao ulohaDao;

    private KategoriaDao kategoriaDao;

    private FilterDao filterDao;

    private NotifikaciaDao notifikaciaDao;

    private PouzivatelDao pouzivatelDao;

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate jdbcTemplate() {
        if (this.jdbcTemplate == null) {
            this.jdbcTemplate = new JdbcTemplate(dataSource());
        }
        return this.jdbcTemplate;
    }

    public UlohaDao ulohaDao() {
        if (this.ulohaDao == null) {
            this.ulohaDao = new DatabazovyUlohaDao(jdbcTemplate());
        }
        return this.ulohaDao;
    }

    public FilterDao filterDao() {
        if (this.filterDao == null) {
            this.filterDao = new DatabazovyFilterDao(jdbcTemplate());
        }
        return this.filterDao;
    }

    public KategoriaDao kategoriaDao() {
        if (this.kategoriaDao == null) {
            this.kategoriaDao = new DatabazovyKategoriaDao(jdbcTemplate());
        }
        return this.kategoriaDao;
    }

    public NotifikaciaDao notifikaciaDao() {
        if (this.notifikaciaDao == null) {
            this.notifikaciaDao = new DatabazovyNotifikaciaDao();
        }
        return this.notifikaciaDao;
    }

    public PouzivatelDao pouzivatelDao() {
        if (this.pouzivatelDao == null) {
            this.pouzivatelDao = new DatabazovyPouzivatelDao(jdbcTemplate());
        }
        return this.pouzivatelDao;
    }

    public MysqlDataSource dataSource() {
        Properties properties = getProperties();
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL((String) properties.get("dataServer"));
        dataSource.setUser((String) properties.get("dataLogin"));
        dataSource.setPassword((String) properties.get("dataPass"));

        return dataSource;
    }

    public ComboBoxModel getKategoryCmbModel() {
        List<Kategoria> kategorie = kategoriaDao().dajVsetky();
        String[] poleKategorii = new String[kategorie.size() + 1];
        for (int i = 0; i < kategorie.size(); i++) {
            poleKategorii[i + 1] = kategorie.get(i).getNazov();
        }
        poleKategorii[0] = " ";
        return new DefaultComboBoxModel(poleKategorii);
    }

    private Properties getProperties() {
        try {
            String propertiesFile;

            if ("true".equals(System.getProperty("testovaciRezim"))) {
                propertiesFile = "/home/todo/todo-test.properties";
            } else {
                propertiesFile = "/home/todo/todo.properties";
            }

            InputStream in;

            try {
                in = new FileInputStream(propertiesFile);
            } catch (FileNotFoundException e) {
                // ak sme tu, tak sa nenasiel moj subor na disku, teda s projektom
                // pracuje Alica a ona ma ine cesty k properties suborom
                if ("true".equals(System.getProperty("testovaciRezim"))) {
                    propertiesFile = "C:/todo/todo-test.properties";
                } else {
                    propertiesFile = "C:/todo/todo.properties";
                }
                in = new FileInputStream(propertiesFile);
            }

            Properties properties = new Properties();
            properties.load(in);

            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Nenašiel sa konfiguračný súbor!");
        }
    }
}
