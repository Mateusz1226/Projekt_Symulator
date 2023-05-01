package pl.projekt_symulator.service;

import org.springframework.stereotype.Service;
import pl.projekt_symulator.dto.ScheduleDto;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.Schedule;

import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.ScheduleMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.ScheduleRepository;

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

    public String book(ScheduleDto scheduleDto, Long userId) {


        Schedule Schedule = scheduleRepository.findByStartAndEnd(scheduleDto.getStart(), scheduleDto.getEnd());

        if ( Schedule != null) {
            throw new IllegalArgumentException("Termin jest już zajęty");
        }


        Schedule scheduleToEntity = new Schedule();

        scheduleToEntity.setId(scheduleDto.getId());
        scheduleToEntity.setStart(scheduleDto.getStart());
        scheduleToEntity.setEnd(scheduleDto.getEnd());

        //zabezpieczenie co jakby nie było id w bazie ???
        User user = userMapper.mapToEntity(userService.findUserById(userId));


        scheduleToEntity.setUser(user);

        scheduleRepository.save(scheduleToEntity);

        return "Termin został zarezerwowany";
    }

    public String unbook(Schedule schedule, Long userId) {

        UserDto user = userService.findUserById(userId);

        Schedule schedule1 = scheduleRepository.findByStartAndEndAndUser(schedule.getStart(), schedule.getEnd(), userMapper.mapToEntity(user));
        if (schedule == null) {
            throw new IllegalArgumentException("Nie możesz odwołać tego terminu");
        }
        scheduleRepository.deleteById(schedule.getId());
        return "Rezerwacja została odwołana";
    }
}



