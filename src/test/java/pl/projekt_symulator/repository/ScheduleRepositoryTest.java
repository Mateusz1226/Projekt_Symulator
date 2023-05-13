package pl.projekt_symulator.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
//@DataJpaTest
class ScheduleRepositoryTest {



    @Autowired
    private ScheduleRepository repository;


    @BeforeEach
    void setUp() {
        repository = mock(ScheduleRepository.class);

    }


    @Test
    void save_schedule() {

        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@email.pl");
        user.setId(1L);

        LocalDateTime start = LocalDateTime.of(2023,05,11, 18,41);
        LocalDateTime end = LocalDateTime.of(2023,05,11, 19,41);
        Schedule schedule = new Schedule(start,end,user);


        when(repository.save(schedule)).thenReturn(schedule);
    }

    @Test
    void findByStartAndEnd() {
        LocalDateTime start = LocalDateTime.of(2023,05,11, 18,41);
        LocalDateTime end = LocalDateTime.of(2023,05,11, 19,41);
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@email.pl");
        user.setId(1L);
        Schedule schedule = new Schedule(start,end,user);

        when(repository.findByStartAndEnd(start,end)).thenReturn(schedule);

    }
}*/
