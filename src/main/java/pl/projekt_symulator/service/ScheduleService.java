package pl.projekt_symulator.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.projekt_symulator.dto.ScheduleDto;

import pl.projekt_symulator.entity.Schedule;

import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.ScheduleMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.ScheduleRepository;

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

    public String book(ScheduleDto scheduleDto, Long userId) {


        Schedule Schedule = scheduleRepository.findByStartAndEnd(scheduleDto.getStart(), scheduleDto.getEnd());

        if ( Schedule != null) {
            throw new IllegalArgumentException("Termin jest już zajęty");
        }


        Schedule schedule = new Schedule();
        schedule.setId(scheduleDto.getId());
        schedule.setStart(scheduleDto.getStart());
        schedule.setEnd(scheduleDto.getEnd());

        //zabezpieczenie co jakby nie było id w bazie ???
        User user = userService.findUserById(userId);


        schedule.setUser(user);
        scheduleRepository.save(schedule);

        String mailType = "book";
        sendEmailAboutReservationStatus(schedule,user,mailType);

        return "Termin został zarezerwowany";
    }

    public void unbook(ScheduleDto scheduleDto, Long userId) {

        User user = userService.findUserById(userId);
          // trzeba wyjąć na poziom kontrollera
        Schedule schedule = scheduleRepository.findByStartAndEndAndUser(scheduleDto.getStart(), scheduleDto.getEnd(), user);
        if (schedule == null) {
            throw new IllegalArgumentException("Nie możesz odwołać tego terminu");
        }
        scheduleRepository.deleteById(schedule.getId());

        String mailType = "unbook";

        sendEmailAboutReservationStatus(schedule,user,mailType);

    }




    public void sendEmailAboutReservationStatus(Schedule schedule, User user, String mailType) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("strzelnicanozyno@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Symulator strzelecki nożyno potwierdzenie rezerwacji");
        if (mailType.equals("book")) {
            message.setText("Cześć " + user.getFirstName() + ", dziękujemy za zarezerowanie wiyzty w terimnie od" + schedule.getStart() + " do " + schedule.getEnd()+".");
            mailSender.send(message);
            return;
        }
        message.setText("Cześć " + user.getFirstName() + ", rezerwacja w terminie " + schedule.getStart() + " do " + schedule.getEnd() + " została anulowana." );
        mailSender.send(message);

    }

}