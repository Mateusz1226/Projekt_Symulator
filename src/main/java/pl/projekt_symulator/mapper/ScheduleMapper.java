package pl.projekt_symulator.mapper;


import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.projekt_symulator.dto.ScheduleDto;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ScheduleMapper {


    Schedule mapToEntity(ScheduleDto scheduleDto);

    ScheduleDto mapToDto (Schedule schedule);
    List<ScheduleDto> mapToDto(List<Schedule> schedules);
}




