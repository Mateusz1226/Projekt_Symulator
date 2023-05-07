package pl.projekt_symulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.projekt_symulator.dto.ScheduleDto;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.MarketingDataMapper;
import pl.projekt_symulator.mapper.ScheduleMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.MarketingRepository;
import pl.projekt_symulator.repository.RoleRepository;
import pl.projekt_symulator.repository.ScheduleRepository;
import pl.projekt_symulator.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@DataJpaTest

class ScheduleServiceTest {


    private ScheduleService service;
    private ScheduleRepository repository;

    private UserServiceImpl userService;

    private ScheduleMapper scheduleMapper;
    private UserMapper userMapper;

    private JavaMailSender mailSender;




    @BeforeEach
    void setUp() {
        repository = mock(ScheduleRepository.class);
        userService = mock(UserServiceImpl.class);
        userMapper = mock(UserMapper.class);
        mailSender = mock(JavaMailSender.class);

        service = new ScheduleService(scheduleMapper, userMapper, repository, userService, mailSender);
    }


    @Test
    void add_appointment_to_schedule() {

        ScheduleDto schedule = new ScheduleDto();
        User user = new User(1l,"test@gmail.com");
        schedule.setStart(LocalDateTime.now());
        schedule.setEnd(LocalDateTime.of(2023,05,07,19,00,00));


        ArgumentCaptor<Schedule> argumentCaptor = ArgumentCaptor.forClass(Schedule.class);
        service.book(schedule,user);
        // sprawdza ile razy została wywołana metoda
        verify(repository, times(1)).save(argumentCaptor.capture());

    }

    @Test
    public void shouldFindAppointmentById() {
        ScheduleDto schedule = new ScheduleDto();
        schedule.setStart(LocalDateTime.now());
        schedule.setEnd(LocalDateTime.of(2023,05,07,19,00,00));
        schedule.setId(1L);
        Schedule schedule1 = scheduleMapper.mapToEntity(schedule);
        when(repository.findById(1L)).thenReturn(Optional.of(schedule1));

        assertEquals(Optional.of(schedule1).get().getId(), service.getScheduleById(1L).orElseThrow(() -> new IllegalArgumentException("Brak ID")));
        verify(repository, times(1)).findById(1L).orElseThrow(() -> new IllegalArgumentException("Brak ID"));


    }


    @Test
    void shouldThrowException() {
        User user = new User(1l,"test@gmail.com");
        ScheduleDto schedule = new ScheduleDto(1l,LocalDateTime.now(),LocalDateTime.of(2023,05,07,19,00,00));
        schedule.setId(1L);

        doThrow(new EmptyResultDataAccessException("Nie możesz odwołać tego terminu, ponieważ nie został jeszcze zarezerwowany", 1)).when(repository).deleteById(1L);
        // when
        assertThrows(EmptyResultDataAccessException.class, () -> service.unbook(schedule,user));


    }

    @Test
    void sendEmailAboutReservationStatusWithBookStatus() {

            User user = new User(1l,"test@gmail.com");
            Schedule schedule = new Schedule(LocalDateTime.now(),LocalDateTime.of(2023,05,07,19,00,00),user);
            String mailType = "book";

                  // błąd
               verify(service,times(1)).sendEmailAboutReservationStatus(schedule,user,mailType);
            //verify(service, times(1)).sendEmailAboutReservationStatus(schedule,user,mailType);


    }
}