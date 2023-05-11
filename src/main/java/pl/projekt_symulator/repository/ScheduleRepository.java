package pl.projekt_symulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;

import java.time.LocalDateTime;
import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findByStartAndEnd(LocalDateTime start, LocalDateTime end);
    List<Schedule> findByUserId(Long id);

    Schedule findByStart(LocalDateTime start);

    Schedule findByStartAndEndAndUser(LocalDateTime start, LocalDateTime end, User user);
}


