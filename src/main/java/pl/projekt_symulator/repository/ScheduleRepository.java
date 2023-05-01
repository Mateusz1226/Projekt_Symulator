package pl.projekt_symulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;

import java.time.LocalDate;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findByStartAndEnd(LocalDate start, LocalDate end);

    Schedule findByStartAndEndAndUser(LocalDate start, LocalDate end, User user);
}


