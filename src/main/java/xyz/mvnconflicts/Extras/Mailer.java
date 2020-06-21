package xyz.mvnconflicts.Extras;

import xyz.mvnconflicts.Rest.DTO.ContactDTO;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Mailer {
    private ContactDTO contactDTO;

    public Mailer(ContactDTO contactDTO) {
        this.contactDTO = contactDTO;
    }

    public void sendMail(){
        String to = "kennethlindalen@gmail.com";
        String from = "kennethlindalen@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("MvnConflicts Contact");
            message.setText("From: " + this.contactDTO.getName() +  "\nEmail: " + this.contactDTO.getEmail() + "\nTime: " + getCurrentDate() + "\n \n" + this.contactDTO.getMessage());

            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    public String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
