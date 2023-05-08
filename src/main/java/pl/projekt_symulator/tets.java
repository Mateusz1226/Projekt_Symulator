package pl.projekt_symulator;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.MINUTES;

public class tets {

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();
        System.out.println(start);
        LocalDateTime end = LocalDateTime.of(2023,05,9, 18,50);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = start.format(formatter);
        System.out.println(formattedDateTime);

        System.out.println(LocalTime.now());
        DateTimeFormat.ISO starta = DateTimeFormat.ISO.DATE;
     // ok  MINUTES.between(start, end) < 60
        if ( MINUTES.between(start, end) > 600){
            System.out.println(MINUTES.between(start, end));
        }
        System.out.println(start.getDayOfWeek().equals(DayOfWeek.MONDAY));
        System.out.println(LocalDateTime.now());
    }
}
