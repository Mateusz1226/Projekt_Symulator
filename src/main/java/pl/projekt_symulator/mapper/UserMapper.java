package pl.projekt_symulator.mapper;



import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.User;

import java.util.List;
@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

   User mapToEntity(UserDto userDto);
   UserDto mapToDto (User user);
  List<UserDto> mapToDto(List<User> users);

}
