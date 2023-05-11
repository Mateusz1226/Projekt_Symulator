package pl.projekt_symulator.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.projekt_symulator.dto.ScheduleDto;

import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.Schedule;

import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.ScheduleMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.ScheduleRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class ScheduleService {


    private final ScheduleMapper scheduleMapper;
    private final UserMapper userMapper;
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    private final JavaMailSender mailSender;

    public ScheduleService(ScheduleMapper scheduleMapper, UserMapper userMapper, ScheduleRepository scheduleRepository, UserService userService, JavaMailSender mailSender) {
        this.scheduleMapper = scheduleMapper;
        this.userMapper = userMapper;
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
        this.mailSender = mailSender;
    }

    public String book(ScheduleDto scheduleDto, User user) {


        Schedule Schedule = scheduleRepository.findByStartAndEnd(scheduleDto.getStart(), scheduleDto.getEnd());

        if (Schedule != null) {
            return ("Termin jest już zajęty");
        }

        if (!termVerification(scheduleDto).equals("OK")) {
            return termVerification(scheduleDto);
        }


        Schedule schedule = new Schedule();
        schedule.setId(scheduleDto.getId());
        schedule.setStart(scheduleDto.getStart());
        schedule.setEnd(scheduleDto.getEnd());


        schedule.setUser(user);
        scheduleRepository.save(schedule);

        String mailType = "book";
        sendEmailAboutReservationStatus(schedule, user, mailType);

        return "Termin został zarezerwowany";
    }


    public String unbook(ScheduleDto scheduleDto, User user) {


        Schedule schedule = scheduleRepository.findByStartAndEnd(scheduleDto.getStart(), scheduleDto.getEnd());
        if (schedule == null) {
            //  throw new EmptyResultDataAccessException("Nie możesz odwołać tego terminu, ponieważ nie został jeszcze zarezerwowany", 1);
            return "Nie możesz odwołać tego terminu, ponieważ nie został jeszcze zarezerwowany";
        }
        if (!schedule.getUser().getId().equals(user.getId())) {
            return "Nie możesz odwołać tego terminu, ponieważ został zarezerwowany przez inną osobę";
        }


        scheduleRepository.deleteById(schedule.getId());
        String mailType = "unbook";
        sendEmailAboutReservationStatus(schedule, user, mailType);
        return "Anulowano termin";
    }


    public String termVerification(ScheduleDto scheduleDto) {

        // jak sprawdzić, czy wskazana data nie mieści się już w dacie innej rezerwacji
        // ?? może za pomocą pętli pobierać wszystkie rezerwacje i sprawdzać, czy ta nowa znajduje się w przedziale ??


        if (scheduleDto.getStart().getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return "Symulator jest zamknięty w poniedziałek";
        }
        if (MINUTES.between(scheduleDto.getStart(), scheduleDto.getEnd()) < 60) {
            return "Za krótki okres rezerwacji";
        }
        if (MINUTES.between(scheduleDto.getStart(), scheduleDto.getEnd()) > 600) {
            return "Za długi okres rezerwacji";
        }

        if (LocalDateTime.now().isAfter(scheduleDto.getStart())) {
            return "Nie można rezerwować dat przeszłych";
        }

        if (scheduleDto.getStart().getHour() < 12 || scheduleDto.getEnd().getHour() > 20) {
            return "Rezerwacja poza godzinami pracy";
        }
        return "OK";

    }


    public void sendEmailAboutReservationStatus(Schedule schedule, User user, String mailType) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String start = schedule.getStart().format(formatter);
        String end = schedule.getEnd().format(formatter);


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("strzelnicanozyno@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Symulator strzelecki nożyno potwierdzenie rezerwacji");
        if (mailType.equals("book")) {
            message.setText("Cześć " + user.getFirstName() + ", dziękujemy za zarezerwowanie terminu od" + start + " do " + end + ".");
            mailSender.send(message);
            return;
        }
        message.setText("Cześć " + user.getFirstName() + ", rezerwacja w terminie " + start + " do " + end + " została anulowana.");
        mailSender.send(message);

    }

    public List<Schedule> fullSchedule() {
        List<Schedule> fullSchedule = scheduleRepository.findAll();



        return new ArrayList<>(fullSchedule);

    }




    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

}