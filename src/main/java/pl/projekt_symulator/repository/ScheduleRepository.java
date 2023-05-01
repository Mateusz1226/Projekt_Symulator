package pl.projekt_symulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;

import java.time.LocalDateTime;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findByStartAndEnd(LocalDateTime start,LocalDateTime end);

    Schedule findByStartAndEndAndUser(LocalDateTime start, LocalDateTime end, User user);
}


