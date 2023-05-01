package pl.projekt_symulator.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.ScheduleRepository;

@Service
public class ScheduleService {



    final private UserMapper userMapper;
    final private ScheduleRepository scheduleRepository;

    public ScheduleService(UserMapper userMapper, ScheduleRepository scheduleRepository) {
        this.userMapper = userMapper;
        this.scheduleRepository = scheduleRepository;
    }

    public ResponseEntity<String> book(Schedule schedule, UserDto userDto) {

        Schedule schedule1 = scheduleRepository.findByStartAndEnd( schedule.getStart(),schedule.getEnd());
        if (schedule1 != null) {
            throw new IllegalArgumentException("Termin jest już zajęty");
        }

        scheduleRepository.save(new Schedule(schedule.getStart(),schedule.getEnd(), userMapper.mapToEntity(userDto)));
        return ResponseEntity.ok("Termin został zarezerwowany");
    }

    public ResponseEntity<String> unbook(Schedule schedule, UserDto userDto) {

        Schedule schedule1 = scheduleRepository.findByStartAndEndAndUser(schedule.getStart(),schedule.getEnd(), userMapper.mapToEntity(userDto));
        if (schedule == null) {
            throw new IllegalArgumentException("Nie możesz odwołać teho terminu");
        }
        scheduleRepository.deleteById(schedule.getId());
        return ResponseEntity.ok("Rezerwacja została usunięta");
    }
}



