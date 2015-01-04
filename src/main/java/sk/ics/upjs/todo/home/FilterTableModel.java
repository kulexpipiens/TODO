package sk.ics.upjs.todo.home;

import java.util.*;
import javax.swing.table.AbstractTableModel;
import sk.ics.upjs.todo.dao.Factory;
import sk.ics.upjs.todo.dao.FilterDao;

public class FilterTableModel extends AbstractTableModel {

    private static final int POCET_STLPCOV = 1;

    private static final String[] NAZVY_STLPCOV = {"Názov"};

    private static final Class[] TYPY_STLPCOV = {String.class};

    private static final FilterDao filterDao = Factory.INSTANCE.filterDao();

    private List<Filter> filtre = new LinkedList<>();

    @Override
    public int getRowCount() {
        return filtre.size();
    }

    @Override
    public int getColumnCount() {
        return POCET_STLPCOV;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return filtre.get(rowIndex).getNazov();
    }

    @Override
    public String getColumnName(int column) {
        return NAZVY_STLPCOV[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return TYPY_STLPCOV[columnIndex];
    }

    //refresh
    public void obnov() {
        filtre = filterDao.dajVsetky();
        fireTableDataChanged();
    }

    //vrati filter v danom riadku
    public Filter dajPodlaCislaRiadku(int riadok) {
        return filtre.get(riadok);
    }
}
