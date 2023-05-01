package pl.projekt_symulator.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.MarketingData;
import pl.projekt_symulator.entity.User;

import java.util.List;


@Component
@Mapper(componentModel = "spring")
public interface MarketingDataMapper {

    MarketingData mapToEntity(UserDto userDto);
    UserDto mapToDto (MarketingData marketingData);
    List<UserDto> mapToDto(List<MarketingData> MarketingData);

}
