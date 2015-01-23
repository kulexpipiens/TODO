package sk.ics.upjs.todo.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JDialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import sk.ics.upjs.todo.dao.Factory;
import sk.ics.upjs.todo.entity.Uloha;

public class GrafForm extends JDialog {

    static final long JEDNA_MINUTA_V_MILISEKUNDACH = 60000;

    private JFreeChart graf;
    private ChartPanel panelSGrafom;
    private final Calendar datumOd = Calendar.getInstance();
    private final Calendar datumDo = Calendar.getInstance();
    private List<Uloha> ulohy;
    private final Calendar zaciatok = Calendar.getInstance();
    private final Calendar koniec = Calendar.getInstance();

    public GrafForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        zaciatok.setTime(new Date(2015 - 1900, 0, 1, 0, 0));
        koniec.setTime(new Date(2015 - 1900, 0, 1, 23, 59));
    }

    GrafForm(Frame parent, boolean b, Date datumOd, Date datumDo) {
        this(parent, b);

        this.setTitle("Grafické znázornenie udalostí");
        // nastavime od akeho datumu po aky datum chceme zobrazit prehlad
        this.datumOd.setTime(datumOd);
        this.datumDo.setTime(datumDo);

        vytvorGraf();

        // pridame panel z grafom do JDialogu a dame ho do stredu okna
        this.getContentPane().add(panelSGrafom, BorderLayout.CENTER);
        // nastavime aby po zavreti ukoncilo JDialog
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // nastavime velkost a pociatocne umiestnenie okna
        this.setBounds(50, 50, 800, 500);
        // zviditelnime okno
    }

    private void vytvorGraf() {
        // vytvorime podla priorit uchovavatelov jednotlivych uloh z danej priority
        TaskSeries prioritaVysoka = new TaskSeries("vysoká priorita");
        TaskSeries prioritaStredna = new TaskSeries("stredná priorita");
        TaskSeries prioritaNizka = new TaskSeries("nízka priorita");

        // poziadame UlohyDao o ulohy, ktore ideme zobrazit
        ulohy = Factory.INSTANCE.ulohaDao().dajZCasovehoIntervalu(datumOd, datumDo);

        // prejdem vsetkymi ulohami a vlozim ich do prislusnej mnoziny dat
        for (Uloha uloha : ulohy) {
            Task ulohaPreGraf = dajUlohu(uloha);

            switch (uloha.getPriorita()) {
                case "Nízka":
                    prioritaNizka.add(ulohaPreGraf);
                    break;
                case "Stredná":
                    prioritaStredna.add(ulohaPreGraf);
                    break;
                case "Vysoká":
                    prioritaVysoka.add(ulohaPreGraf);
                    break;
            }
        }

        // vytvorime mnozinu dat na jednotlive mnoziny uloh s prioritami
        TaskSeriesCollection mnozinaDat = new TaskSeriesCollection();
        // pridame jednotlive priority k datam
        mnozinaDat.add(prioritaVysoka);
        mnozinaDat.add(prioritaStredna);
        mnozinaDat.add(prioritaNizka);

        /* vytvorime samotny graf, nazov mu nedame, y/ova suradnica budu dni, 
         x-ova časy v jednotlivé dni, legendu zobrazíme, tooltip nechceme, 
         ani generovat url
         */
        graf = ChartFactory.createGanttChart("", "Deň", "Čas", mnozinaDat, true, false, false);
        /*
         Vytvorime chardPanel, ale jemu dovolime iba niktore moznosti
         po kliknuti pravym tlacidlom mysi (mozeme zobrazit nastavenia - 
         pisma..., mozeme graf ulozit, mozeme graf tlacit, nemozeme graf 
         zoomovat - lebo nechceme aby niekto videl, že v skutocnosti xova 
         suradnica je jeden skutocny den, tiez nechceme zobrazovat tooltipy)
         */
        panelSGrafom = new ChartPanel(graf, true, true, true, false, false);
    }

    private Task dajUlohu(Uloha uloha) {
        // zistim cas zacatia ulohy
        Date casZacatia = uloha.getDatum();
        Calendar cal = Calendar.getInstance();
        cal.setTime(casZacatia);

        // zistim o aky den ide a pripravim si jeho string hodnotu
        String den = cal.get(Calendar.YEAR) + " "
                + (cal.get(Calendar.MONTH) + 1)
                + " " + cal.get(Calendar.DAY_OF_MONTH);

        /* pripravim si cas zaciatku, kedze graf by bral iny den ako iny den
         teda sa nepohybuje v rozpati 24 hodin, ale v neurcitom rozpati,
         tak si musim vytvorit pomocny datum zaciatku a konca kde bude datum
         pre vsetky ulohy rovnaky iba cas sa bude menit podla ulohy
         */
        Date casZaciatku = new Date(zaciatok.get(Calendar.YEAR) - 1900,
                zaciatok.get(Calendar.MONTH), zaciatok.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
        long casZaciatkuUdalosti = casZaciatku.getTime();

        // zistim cas skoncenia ulohy
        Date casKonca = new Date(casZaciatkuUdalosti + (uloha.getTrvanie() * JEDNA_MINUTA_V_MILISEKUNDACH));

        // vratim ulohu pre graf
        return new Task(den, casZaciatku, casKonca);
    }
}
