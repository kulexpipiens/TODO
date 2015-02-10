package sk.upjs.ics.todo.modely;

import sk.upjs.ics.todo.entity.Uloha;
import sk.upjs.ics.todo.dao.Factory;
import sk.upjs.ics.todo.dao.UlohaDao;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class UlohaTableModel extends AbstractTableModel {

    private static final int POCET_STLPCOV = 2;

    private static final String[] NAZVY_STLPCOV = {"Úloha", "Ostáva"};

    private static final Class[] TYPY_STLPCOV = {String.class, String.class};

    private static final UlohaDao ulohaDao = Factory.INSTANCE.ulohaDao();

    private List<Uloha> ulohy = new LinkedList<>();

    @Override
    public int getRowCount() {
        return ulohy.size();
    }

    @Override
    public int getColumnCount() {
        return POCET_STLPCOV;
    }

    /**
     * metóda vráti buď názov úlohy, alebo dátum, ktorý zostáva na jej
     * vykonanie, podľa toho, v ktorom stĺpci sme
     *
     * @param rowIndex index polozky
     * @param columnIndex index stĺpca
     * @return objekt, ktorý má zobraziť v tabuľke
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Uloha uloha = ulohy.get(rowIndex);

        if (columnIndex == 0) {
            return uloha.getNazov();
        } else {
            // dátum úlohy
            Date datum = uloha.getDatum();
            long datumUlohy = datum.getTime();
            
            StringBuilder zostava = new StringBuilder();
            
            // počty daných období v milisekundách
            long minutaMs = 60000;
            long hodinaMs = 3600000;
            long denMs = 86400000;
            long tyzdenMs = denMs * 7;
            long mesiacMs = denMs * 30;
            long rokMs = denMs * 365;
            
            long pocet = 0;
            
            // aktuálny dátum
            long teraz = System.currentTimeMillis();

            long rozdiel = datumUlohy - teraz;

            /* oifované prípady, aby zobrazovalo iba 2 najväčšie časové obdobia,
             napr. rok a mesiace, mesiace a týždne, hodiny a minúty... 
             */
            if (rozdiel > rokMs) {
                pocet = (rozdiel / rokMs);
                zostava.append(pocet).append("r ");
                pocet = (rozdiel - pocet * rokMs) / mesiacMs;
                if (pocet != 0) {
                    zostava.append(Long.toString(pocet)).append("mes");
                }
                return zostava.toString();
            }
            if (rozdiel > mesiacMs) {
                pocet = (rozdiel / mesiacMs);
                zostava.append(Long.toString(pocet)).append("mes ");
                pocet = (rozdiel - pocet * mesiacMs) / tyzdenMs;
                if (pocet != 0) {
                    zostava.append(Long.toString(pocet)).append("týž");
                }
                return zostava.toString();
            }
            if (rozdiel > tyzdenMs) {
                pocet = (rozdiel / tyzdenMs);
                zostava.append(Long.toString(pocet)).append("týž ");
                pocet = (rozdiel - pocet * tyzdenMs) / denMs;
                if (pocet != 0) {
                    zostava.append(Long.toString(pocet)).append("d");
                }
                return zostava.toString();
            }
            if (rozdiel > denMs) {
                pocet = (rozdiel / denMs);
                zostava.append(Long.toString(pocet)).append("d ");
                pocet = (rozdiel - pocet * denMs) / hodinaMs;
                if (pocet != 0) {
                    zostava.append(Long.toString(pocet)).append("hod");
                }
                return zostava.toString();
            }
            if (rozdiel > hodinaMs) {
                pocet = (rozdiel / hodinaMs);
                zostava.append(Long.toString(pocet)).append("hod ");
                pocet = (rozdiel - pocet * hodinaMs) / minutaMs;
                if (pocet != 0) {
                    zostava.append(Long.toString(pocet)).append("min");
                }
                return zostava.toString();
            }
            if (rozdiel > minutaMs) {
                pocet = (rozdiel / minutaMs);
                zostava.append(Long.toString(pocet)).append("min");
                return zostava.toString();
            }
            return "Čas vypršal";

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

    // refresh
    public void obnov() {
        ulohy = ulohaDao.dajVsetky();
        fireTableDataChanged();
    }

    // vypíše iba úlohy, ktoré zodpovedajú filtru
    public void filtruj(List<Uloha> filtrovaneUlohy) {
        ulohy = filtrovaneUlohy;
        fireTableDataChanged();
    }

    // vráti úlohu v zadanom riadku
    public Uloha dajPodlaCislaRiadku(int riadok) {
        return ulohy.get(riadok);
    }
}
