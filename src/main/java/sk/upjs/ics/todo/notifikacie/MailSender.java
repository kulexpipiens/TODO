package sk.upjs.ics.todo.notifikacie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sk.upjs.ics.todo.entity.Notifikacia;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Zdroj:
 * http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
 */
public class MailSender {

    // udaje odosielatela
    private String odosielatel;
    private String meno;
    private String heslo;

    private final Properties nastavenia;
    private final String smtp;
    private final Session session;

    public MailSender() {
        // inicializujeme prihlasovacie udaje
        inicializuj();
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

            // Od:
            sprava.setFrom(new InternetAddress(odosielatel));

            // Pre:
            sprava.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(notifikacia.getMail()));

            // Predmet:
            sprava.setSubject("Upozornenie na úlohu \"" + notifikacia.getNazov() + "\"");

            String textSpravy = "Nezabudnite na splnenie nasledovnej úlohy:"
                    + "\n\nNázov: " + notifikacia.getNazov()
                    + "\nPopis: " + notifikacia.getPopis()
                    + "\nDátum: " + vratStringDatumu(notifikacia.getDatum())
                    + "\nČas: " + vratStringCasu(notifikacia.getDatum())
                    + "\nTrvanie: " + notifikacia.getTrvanie() + " minút"
                    + "\n\nVáš TODO list Dori.";

            // Sprava:
            sprava.setText(textSpravy);

            // odosle spravu
            Transport.send(sprava);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String vratStringDatumu(Date d) {
        StringBuilder datum = new StringBuilder();
        datum.append(d.getDate());
        datum.append(".");
        datum.append(d.getMonth() + 1);
        datum.append(".");
        datum.append(d.getYear() + 1900);
        return datum.toString();
    }

    private String vratStringCasu(Date d) {
        StringBuilder cas = new StringBuilder();
        if (d.getHours() < 10) {
            cas.append(0);
        }
        cas.append(d.getHours());
        cas.append(":");
        if (d.getMinutes() < 10) {
            cas.append(0);
        }
        cas.append(d.getMinutes());
        return cas.toString();
    }

    private void inicializuj() {
        Properties properties = dajProperties();

        odosielatel = (String) properties.get("odosielatel");
        meno = (String) properties.get("meno");
        heslo = (String) properties.get("heslo");
    }

    private Properties dajProperties() throws IllegalStateException {
        try {
            String propertiesFile = "/home/todo/mail.properties";

            InputStream in;

            try {
                in = new FileInputStream(propertiesFile);
            } catch (FileNotFoundException e1) {
                // ak sme tu, tak sa nenasiel moj subor na disku, teda s projektom
                // pracuje Alica a ona ma ine cesty k properties suborom
                propertiesFile = "C:/todo/mail.properties";
                try {
                    in = new FileInputStream(propertiesFile);
                } catch (FileNotFoundException e2) {
                    // ak sme tu, tak program bezi na serveri
                    propertiesFile = "/home/akacengova/todo/mail.properties";
                    in = new FileInputStream(propertiesFile);
                }
            }

            Properties properties = new Properties();
            properties.load(in);

            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Nenašiel sa konfiguračný súbor!");
        }
    }
}
