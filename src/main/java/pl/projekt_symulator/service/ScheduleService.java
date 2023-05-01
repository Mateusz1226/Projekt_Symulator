package pl.projekt_symulator.service;

import org.springframework.stereotype.Service;
import pl.projekt_symulator.dto.ScheduleDto;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.Schedule;

import pl.projekt_symulator.mapper.ScheduleMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class ScheduleService {


    private final ScheduleMapper scheduleMapper;
    private final UserMapper userMapper;
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    public ScheduleService(ScheduleMapper scheduleMapper, UserMapper userMapper, ScheduleRepository scheduleRepository, UserService userService) {
        this.scheduleMapper = scheduleMapper;
        this.userMapper = userMapper;
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
    }

    public String book(ScheduleDto schedule, Long userId) {

        Schedule schedule1 = scheduleRepository.findByStartAndEnd(LocalDateTime.parse(schedule.getStart()),LocalDateTime.parse(schedule.getEnd()));

        if (schedule1 != null) {
            throw new IllegalArgumentException("Termin jest już zajęty");
        }


        UserDto user = userService.findUserById(userId);

        scheduleRepository.save(new Schedule(LocalDateTime.parse(schedule.getStart()),LocalDateTime.parse(schedule.getEnd()),userMapper.mapToEntity(user)));

        userMapper.mapToEntity(user).setSchedule(Arrays.asList(schedule1));

        return "Termin został zarezerwowany";
    }

    public String unbook(Schedule schedule, Long userId) {

        UserDto user = userService.findUserById(userId);

        Schedule schedule1 = scheduleRepository.findByStartAndEndAndUser(schedule.getStart(),schedule.getEnd(), userMapper.mapToEntity(user));
        if (schedule == null) {
            throw new IllegalArgumentException("Nie możesz odwołać tego terminu");
        }
        scheduleRepository.deleteById(schedule.getId());
        return "Rezerwacja została odwołana";
    }
}



