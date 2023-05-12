package pl.projekt_symulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import pl.projekt_symulator.dto.ScheduleDto;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.ScheduleMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService service;

    @Mock
    private ScheduleRepository repository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private ScheduleMapper scheduleMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JavaMailSender mailSender;


    @Test
    void add_appointment_to_schedule() {

        LocalDateTime start = LocalDateTime.of(2023, 05, 11, 18, 41);
        LocalDateTime stop = LocalDateTime.of(2023, 05, 11, 19, 41);
        ScheduleDto schedule = new ScheduleDto(null, start, stop);
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@email.pl");
        user.setId(1L);


        ArgumentCaptor<Schedule> argumentCaptor = ArgumentCaptor.forClass(Schedule.class);
        service.book(schedule, user);
        // sprawdza ile razy została wywołana metoda
        verify(repository, times(1)).save(argumentCaptor.capture());

    }


    @Test
    void sendEmailAboutReservationStatusWithBookStatus() {

        User user = new User(1l, "test@gmail.com");
        LocalDateTime start = LocalDateTime.of(2023, 05, 11, 18, 41);
        LocalDateTime stop = LocalDateTime.of(2023, 05, 11, 19, 41);
        Schedule schedule = new Schedule( start, stop,user);
        String mailType = "book";


        // błąd
        verify(service, times(1)).sendEmailAboutReservationStatus(schedule, user, mailType);
        //verify(service, times(1)).sendEmailAboutReservationStatus(schedule,user,mailType);

    }

}