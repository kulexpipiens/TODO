package sk.upjs.ics.todo.gui;

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
import sk.upjs.ics.todo.entity.Uloha;

public class GrafForm extends JDialog {

    /**
     * Jedna sekunda v milisekundach (bude potrebna pri pripocitavani trvania
     * udalosti k datumu zacatia)
     */
    static final long JEDNA_MINUTA_V_MILISEKUNDACH = 60000;
    /**
     * Instancia grafu
     */
    private JFreeChart graf;
    /**
     * Panel do ktoreho vlozime graf
     */
    private ChartPanel panelSGrafom;
    /**
     * Zoznam uloh, ktore ideme zobrazit
     */
    private List<Uloha> ulohy;

    /**
     * Datum OD ktoreho zobrazujeme prehlad
     */
    private Date datumOd;
    /**
     * Datum DO ktoreho zobrazujeme prehlad
     */
    private Date datumDo;

    /**
     * Konstruktor, ktory nastavi rodica a modalitu
     *
     * @param parent rodic JDialogu (u nas bude hlavny formular programu)
     * @param modal modalita okna (teda s oknami pod nevieme pocas zobrazenia
     * tohto okna manipulovat)
     */
    public GrafForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }

    /**
     * Konstruktor v ktorom su udaje z konstruktora vyssie a aj zoznam uloh, a
     * datum OD a DO
     *
     * @param parent rodic JDialogu (u nas bude hlavny formular programu)
     * @param modal modalita okna (teda s oknami pod nevieme pocas zobrazenia
     * tohto okna manipulovat)
     * @param ulohy ulohy, ktore ideme zobrazit
     * @param datumOd datum OD ktoreho chceme zobrazit ulohy
     * @param datumDo datum DO ktoreho chceme zobrazit ulohy
     */
    public GrafForm(Frame parent, boolean modal, List<Uloha> ulohy,
            Date datumOd, Date datumDo) {
        // volame konstruktor na nastavenie rodica a modality
        this(parent, modal);

        // nastavime nadpis okna
        setTitle("Grafické znázornenie udalostí");

        // ulozime si ulohy, ktore ideme zobrazit, do vlastnej premennej
        this.ulohy = ulohy;
        // tie ulozime aj datumy OD a DO
        this.datumOd = datumOd;
        this.datumDo = datumDo;

        // vytvorime graf
        vytvorGraf();

        // pridame panel s grafom do JDialogu a dame ho do stredu okna
        getContentPane().add(panelSGrafom, BorderLayout.CENTER);
        // nastavime aby po zavreti ukoncilo JDialog
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // nastavime velkost a pociatocne umiestnenie okna (9/10 sirky a 1/3 vysky)
        setBounds(0, 0,
                (int) (GuiFactory.INSTANCE.getSirkaObrazovky() * 0.9),
                (int) (GuiFactory.INSTANCE.getSirkaObrazovky() * 0.3));

        // vycentrujeme okno (do stredu obrazovky)
        GuiFactory.INSTANCE.centruj(this);
    }

    private void vytvorGraf() {
        boolean bolaAsponJednaSVysokouPrioritou = false;
        boolean bolaAsponJednaSoStrednouPrioritou = false;
        boolean bolaAsponJednaSNizkouPrioritou = false;
        // vytvorime podla priorit uchovavatelov jednotlivych uloh z danej priority
        TaskSeries prioritaVysoka = new TaskSeries("vysoká priorita");
        TaskSeries prioritaStredna = new TaskSeries("stredná priorita");
        TaskSeries prioritaNizka = new TaskSeries("nízka priorita");

        Task prioritaVysokaTask = new Task("vysoká priorita", datumOd, datumDo);
        Task prioritaStrednaTask = new Task("stredná priorita", datumOd, datumDo);
        Task prioritaNizkaTask = new Task("nízka priorita", datumOd, datumDo);

        prioritaVysoka.add(prioritaVysokaTask);
        prioritaStredna.add(prioritaStrednaTask);
        prioritaNizka.add(prioritaNizkaTask);

        // prejdem vsetkymi ulohami a vlozim ich do prislusneho uchovavatela uloh
        for (Uloha uloha : ulohy) {
            Task ulohaPreGraf = dajUlohu(uloha);
            switch (uloha.getPriorita()) {
                case "Nízka":
                    prioritaNizkaTask.addSubtask(ulohaPreGraf);
                    bolaAsponJednaSNizkouPrioritou = true;
                    break;
                case "Stredná":
                    prioritaStrednaTask.addSubtask(ulohaPreGraf);
                    bolaAsponJednaSoStrednouPrioritou = true;
                    break;
                case "Vysoká":
                    prioritaVysokaTask.addSubtask(ulohaPreGraf);
                    bolaAsponJednaSVysokouPrioritou = true;
                    break;
            }
        }

        // vytvorime mnozinu dat na jednotlive mnoziny uloh s prioritami
        TaskSeriesCollection mnozinaDat = new TaskSeriesCollection();
        // pridame jednotlive priority k datam
        if (bolaAsponJednaSVysokouPrioritou) {
            mnozinaDat.add(prioritaVysoka);
        }
        if (bolaAsponJednaSoStrednouPrioritou) {
            mnozinaDat.add(prioritaStredna);
        }
        if (bolaAsponJednaSNizkouPrioritou) {
            mnozinaDat.add(prioritaNizka);
        }
        /* vytvorime samotny graf, nazov mu nedame, y-ova suradnica budu tri priority, 
         x-ova casy v jednotlive dni (tiez zbytocne nezobrazime nazvy), 
         legendu zobrazime, tooltip zobrazime, ani generovat url
         */
        graf = ChartFactory.createGanttChart("", "", "", mnozinaDat, true, true, false);

        /*
         Vytvorime chartPanel, a dovolime mu vsetky moznosti: po kliknuti pravym 
         tlacidlom mysi (mozeme zobrazit nastavenia - pisma..., mozeme graf 
         ulozit, mozeme graf tlacit, mozeme graf zoomovat, mozeme zobrazit tooltipy)
         */
        panelSGrafom = new ChartPanel(graf, true, true, true, true, true);
    }

    private Task dajUlohu(Uloha uloha) {
        // zistim cas zacatia ulohy
        Date casZacatia = uloha.getDatum();
        Calendar cal = Calendar.getInstance();
        cal.setTime(casZacatia);

        long casZaciatkuUdalosti = casZacatia.getTime();

        // zistim cas skoncenia ulohy
        Date casKonca = new Date(casZaciatkuUdalosti + (uloha.getTrvanie() * JEDNA_MINUTA_V_MILISEKUNDACH));

        // vratim ulohu pre graf
        return new Task(uloha.getNazov(), casZacatia, casKonca);
    }
}
