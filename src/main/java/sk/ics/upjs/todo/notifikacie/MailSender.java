package sk.ics.upjs.todo.notifikacie;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    // udaje odosielatela
    private static final String odosielatel = "todolistpaz1c@gmail.com";
    private static final String meno = "todolistpaz1c";
    private static final String heslo = "1x6cen9Cd0";

    private final Properties nastavenia;
    private final String smtp;
    private final Session session;

    public MailSender() {
        // server, cez ktory odosielame email
        smtp = "smtp.gmail.com";

        // nastavenia pre server
        nastavenia = new Properties();
        nastavenia.put("mail.smtp.auth", "true");
        nastavenia.put("mail.smtp.starttls.enable", "true");
        nastavenia.put("mail.smtp.host", smtp);
        nastavenia.put("mail.smtp.port", "587");

        // vytvori sa novy session objekt
        session = Session.getInstance(nastavenia,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(meno, heslo);
                    }
                });
    }

    public void odosliMail(Notifikacia notifikacia) {
        try {
            // vytvori sa novy objekt so spravou
            Message sprava = new MimeMessage(session);

            // From:
            sprava.setFrom(new InternetAddress(odosielatel));

            // To:
            sprava.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(notifikacia.getMail()));

            // Subject:
            sprava.setSubject("Upozornenie na úlohu " + notifikacia.getNazov());

            Date datum = notifikacia.getDatum();
            StringBuilder sb = new StringBuilder();
            sb.append("Nezabudnite na splnenie nasledovnej úlohy:")
                    .append("\n\nNázov: " + notifikacia.getNazov())
                    .append("\nPopis: " + notifikacia.getPopis())
                    .append("\nDátum: " + + datum.getDate() + "." + datum.getMonth() + "." + datum.getYear())
                    .append("\nČas: " + datum.getHours() + ":" + datum.getMinutes())
                    .append("\nTrvanie: " + notifikacia.getTrvanie() + " minút")
                    .append("\n\nVáš TODO list Dori.");
            
            // Message:
            sprava.setText(sb.toString());

            // odosle spravu
            Transport.send(sprava);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
