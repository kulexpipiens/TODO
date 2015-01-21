package sk.ics.upjs.todo.home;

import sk.ics.upjs.todo.entity.Kategoria;
import sk.ics.upjs.todo.dao.Factory;
import sk.ics.upjs.todo.dao.KategoriaDao;
import java.util.*;
import javax.swing.table.AbstractTableModel;

public class KategoriaTableModel extends AbstractTableModel {

    private static final int POCET_STLPCOV = 2;

    private static final String[] NAZVY_STLPCOV = {"Zoznam kategórií", "Popis"};

    private static final Class[] TYPY_STLPCOV = {String.class, String.class};

    private static final KategoriaDao kategoriaDao = Factory.INSTANCE.kategoriaDao();

    private List<Kategoria> kategorie = new LinkedList<>();

    @Override
    public int getRowCount() {
        return kategorie.size();
    }

    @Override
    public int getColumnCount() {
        return POCET_STLPCOV;
    }

    // vráti buď názov, alebo popis, podľa toho, v ktorom stĺpci sme
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Kategoria kategoia = kategorie.get(rowIndex);
        if (columnIndex == 0) {
            return kategoia.getNazov();
        } else {
            return kategoia.getPopis();
        }
    }

    @Override
    public String getColumnName(int column) {
        return NAZVY_STLPCOV[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return TYPY_STLPCOV[columnIndex];
    }

    //refesh
    public void obnov() {
        kategorie = kategoriaDao.dajVsetky();
        fireTableDataChanged();
    }

    //vráti kategóriu v danom riadku
    public Kategoria dajPodlaCislaRiadku(int riadok) {
        return kategorie.get(riadok);
    }
    
}
