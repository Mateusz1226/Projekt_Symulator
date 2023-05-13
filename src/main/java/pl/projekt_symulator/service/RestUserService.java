package pl.projekt_symulator.service;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import pl.projekt_symulator.dto.UserDto;

import pl.projekt_symulator.entity.MarketingData;
import pl.projekt_symulator.entity.Role;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.MarketingDataMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.MarketingRepository;
import pl.projekt_symulator.repository.RoleRepository;
import pl.projekt_symulator.repository.UserRepository;

import java.util.Collections;

@Service
public class RestUserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final MarketingRepository marketingRepository;
    private final UserMapper userMapper;
    private final MarketingDataMapper marketingMapper;


    public RestUserService(UserRepository userRepository, RoleRepository roleRepository, MarketingRepository marketingRepository, UserMapper userMapper, MarketingDataMapper marketingMapper) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.marketingRepository = marketingRepository;
        this.userMapper = userMapper;
        this.marketingMapper = marketingMapper;

    }


    public UserDto addUser(UserDto userDto) {

        Assert.isNull(userDto.getId(), "Id musi być puste");

        User user = mapToEntity2(userDto);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());

        Role role = roleRepository.findByName("ROLE_ADMIN");

        user.setRoles(Collections.singletonList(role));
        user.setActive(true);
        userRepository.save(user);

        MarketingData marketingDataUser = mapToEntityMD(userDto);

        marketingDataUser.setUser(user);
        marketingDataUser.setAge(marketingDataUser.getAge());
        marketingDataUser.setPhoneNumber(marketingDataUser.getPhoneNumber());
        marketingDataUser.setMarketingAgreement(marketingDataUser.getMarketingAgreement());
        marketingRepository.save(marketingDataUser);

        return  mapToDto2(user);
    }

    public UserDto getById(Long id) {

        return mapToDto2(userRepository.findById(id).orElse(null));
        // return userMapper.mapToDto(userRepository.findById(id).orElse(null));
    }


    public UserDto deactivateUser(Long id, UserDto dto) {
        Assert.notNull(dto.getId(), "Id musi być puste");
        if (!dto.getId().equals(id)) {
            throw new IllegalArgumentException("Id nie pasuje");
        }
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Brak użytkownika w bazie");
        }

        User entity = userMapper.mapToEntity(dto);


        entity.setActive(false);
        userRepository.save(entity);

        return mapToDto2(entity);
    }


    public UserDto changeUserPhone(Long id, UserDto dto) {

        Assert.notNull(dto.getId(), "Id musi być puste");
        if (!dto.getId().equals(id)) {
            throw new IllegalArgumentException("Id nie pasuje");
        }
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Brak użytkownika w bazie");
        }

        User entity = userMapper.mapToEntity(dto);

        MarketingData marketingDataUser = marketingRepository.findByUserId(dto.getId());

        marketingDataUser.setPhoneNumber(dto.getPhoneNumber());
        marketingRepository.save(marketingDataUser);
        userRepository.save(entity);
       // mapToDto2(entity)

        return  userMapper.mapToDto(entity) ;
    }


    public UserDto changeMarketingAgreement(Long id, UserDto dto) {

        Assert.notNull(dto.getId(), "Id musi być puste");
        if (!dto.getId().equals(id)) {
            throw new IllegalArgumentException("Id nie pasuje");
        }
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Brak użytkownika w bazie");
        }

        User entity = userMapper.mapToEntity(dto);
        MarketingData marketingDataUser = marketingRepository.findByUserId(dto.getId());
        marketingDataUser.setMarketingAgreement(dto.getMarketingAgreement());
        marketingRepository.save(marketingDataUser);
        userRepository.save(entity);
        return mapToDto2(entity);
    }

    public UserDto mapToDto2(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());


        MarketingData marketingData = marketingRepository.findByUserId(userDto.getId());
        userDto.setMarketingAgreement(marketingData.getMarketingAgreement());
        userDto.setPhoneNumber(marketingData.getPhoneNumber());
        userDto.setAge(marketingData.getAge());
        return userDto;
    }


    public User mapToEntity2(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());


        return user;
    }

    public MarketingData mapToEntityMD(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        MarketingData marketingData = new MarketingData();
        marketingData.setId(userDto.getId());
        marketingData.setAge(userDto.getAge());
        marketingData.setMarketingAgreement(userDto.getMarketingAgreement());
        marketingData.setPhoneNumber(userDto.getPhoneNumber());
        return marketingData;
    }
}