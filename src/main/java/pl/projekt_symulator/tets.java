package pl.projekt_symulator;

import org.springframework.format.annotation.DateTimeFormat;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.MINUTES;

public class tets {

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.of(2023, 05, 9, 19, 50);
        System.out.println(start);
        LocalDateTime end = LocalDateTime.of(2023, 05, 9, 20, 50);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        if (start.getHour() < 12 || end.getHour() > 20) {
            System.out.println("działa");
        }

        System.out.println(start.getDayOfWeek().equals(DayOfWeek.MONDAY));
        System.out.println(LocalDateTime.now());


        User user = new User(1l, "test@gmail.com");
        LocalDateTime start1 = LocalDateTime.of(2023, 05, 11, 18, 41);
        LocalDateTime stop = LocalDateTime.of(2023, 05, 11, 19, 41);
        Schedule schedule = new Schedule(start1, stop, user);
        System.out.println(schedule.getUser());

    }
}