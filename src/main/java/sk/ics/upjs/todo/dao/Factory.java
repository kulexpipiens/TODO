package sk.ics.upjs.todo.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.awt.Color;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.ics.upjs.todo.entity.Kategoria;

//továreň na jdbcTemplate, ulohaDao, kategoriaDao, dataSource, filterDao
public enum Factory {

    INSTANCE;

    private UlohaDao ulohaDao;

    private KategoriaDao kategoriaDao;

    private FilterDao filterDao;

    private JdbcTemplate jdbcTemplate;

    //  private ComboBoxModel comboBoxModel;
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

    public MysqlDataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://www.db4free.net:3306/todolistproject");
        dataSource.setUser("todolist");
        dataSource.setPassword("hruska123");

        return dataSource;
    }

    public MysqlDataSource dataSourceTest() {
        MysqlDataSource dataSourceTest = new MysqlDataSource();
        dataSourceTest.setURL("jdbc:mysql://www.db4free.net:3306/todolisttestproj");
        dataSourceTest.setUser("todolisttest");
        dataSourceTest.setPassword("hruska123");

        return dataSourceTest;
    }

    public ComboBoxModel getKategoryCmbModel() {
        List<Kategoria> kategorie = new DatabazovyKategoriaDao(jdbcTemplate).dajVsetky();
        String[] poleKategorii = new String[kategorie.size() + 1];
        for (int i = 0; i < kategorie.size(); i++) {
            poleKategorii[i + 1] = kategorie.get(i).getNazov();
        }
        poleKategorii[0] = " ";
        return new DefaultComboBoxModel(poleKategorii);
    }

}
