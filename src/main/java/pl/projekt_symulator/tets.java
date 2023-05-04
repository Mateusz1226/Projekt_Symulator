package pl.projekt_symulator;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class tets {

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();
        System.out.println(start);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");
        String formattedDateTime = start.format(formatter);
        System.out.println(formattedDateTime);

        System.out.println(LocalTime.now());
        DateTimeFormat.ISO starta = DateTimeFormat.ISO.DATE;
    }
}
