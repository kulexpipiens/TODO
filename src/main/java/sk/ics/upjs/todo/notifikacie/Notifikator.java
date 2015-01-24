package sk.ics.upjs.todo.notifikacie;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Notifikator {
    
    private static List<Notifikacia> notifikacie = new LinkedList<>();
    private static final NotifikaciaDao notifikaciaDao = new DatabazovyNotifikaciaDao();
    private static final MailSender mailSender = new MailSender();
    private static final int POCET_MILISEKUND_V_HODINE = 60*60*1000;

    public static void main(String args[]) {
        // prechadzame cez vsetky notifikacie
        notifikacie = notifikaciaDao.dajVsetkyNotifikacie();
        for(Notifikacia n: notifikacie)
        {
            // ak ich uzivatel nechce, koncime
            if(!n.isChceNotifikacie())
                continue;
            
            // ak je uloha splnena, koncime
            if(n.isStav())
                continue;
            
            Date aktualnyCas = new Date();
            // ak uz uloha mala byt splnena, vymazeme notifikaciu a koncime
            if(aktualnyCas.compareTo(n.getDatum()) < 0)
            {
                notifikaciaDao.vymazNotifikaciu(n.getIdUlohy());
                continue;
            }
            
            // ak rozdiel medzi aktualnym casom a casom ulohy je v rozpati, ktore si
            // definoval pouzivatel, posleme email s notifikaciou a vymazeme ju
            if((aktualnyCas.getTime() - n.getDatum().getTime())/POCET_MILISEKUND_V_HODINE
                    < n.getDobaNotifikacie())
            {
                mailSender.odosliMail(n);
                notifikaciaDao.vymazNotifikaciu(n.getIdUlohy());
            }
        }
        
    }
}
