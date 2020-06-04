package xyz.mvnconflicts.Extras;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContactObject {

    private String name;
    private String email;
    private String message;
    private String date;

    public ContactObject(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.date = getCurrentDate();
    }

    public String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
