package pl.projekt_symulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.MarketingDataMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.MarketingRepository;
import pl.projekt_symulator.repository.RoleRepository;
import pl.projekt_symulator.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestUserServiceTest {

   private RestUserService service;
    private UserRepository repository;

    private MarketingRepository marketingRepository;
    private UserMapper userMapper;
    private MarketingDataMapper marketingMapper;

    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        marketingRepository = mock(MarketingRepository.class);
        userMapper = mock(UserMapper.class);
        marketingMapper = mock(MarketingDataMapper.class);
        service = new RestUserService(repository, roleRepository, marketingRepository, userMapper, marketingMapper);
    }

    @Test
    void when_give_user_should_return_new_User() {

        UserDto user = new UserDto();
        user.setFirstName("name");
        user.setLastName("last name");
        user.setPassword("password");
        user.setEmail("mateuszkonkel132@gmail.com");
        user.setMarketingAgreement(false);
        user.setAge(44);
        user.setPhoneNumber("999666333");

        when(service.addUser(user)).thenReturn(user);
        UserDto userDto = service.addUser(user);
        assertNotNull(userDto);

    }

    @Test
    public  void when_give_ID_searching_for_User() {

        UserDto user = new UserDto();

        user.setFirstName("name");
        user.setLastName("last name");
        user.setPassword("password");
        user.setEmail("mateuszkonkel132@gmail.com");

        when(service.addUser(user)).thenReturn(user);

        UserDto userDto = service.getById(user.getId());
        assertNotNull(userDto);
        assertEquals(user.getEmail(), userDto.getEmail());

    }
    @Test
    void  when_give_user_then_user_marketing_agreement_on_false() {

        UserDto user = new UserDto();
        user.setFirstName("name");
        user.setLastName("last name");
        user.setPassword("password");
        user.setEmail("mateuszkonkel132@gmail.com");
        user.setMarketingAgreement(false);
        user.setAge(44);
        user.setPhoneNumber("999666333");


       when(service.addUser(user)).thenReturn(user);
       UserDto userDto = service.addUser(user);
        assertNotNull(userDto);
       UserDto userDto1 =  service.changeMarketingAgreement(user.getId(),userDto);
       assertEquals(false, userDto1.getMarketingAgreement());

    }

    @Test
    void when_give_new_phoneNumber_then_user_number_should_change() {

        UserDto user = new UserDto();
        user.setFirstName("name");
        user.setLastName("last name");
        user.setPassword("password");
        user.setEmail("mateuszkonkel132@gmail.com");
        user.setMarketingAgreement(false);
        user.setAge(44);
        user.setPhoneNumber("999666333");

        when(service.addUser(user)).thenReturn(user);
        UserDto userDto = service.addUser(user);
        assertNotNull(userDto);
        UserDto userDto1 = service.changeUserPhone(user.getId(),userDto);
        assertEquals("999666333", userDto1.getPhoneNumber());

    }

    @Test
    void when_deactivate_user_then_activate_parameter_equal_false() {

        UserDto user = new UserDto();
        user.setFirstName("name");
        user.setLastName("last name");
        user.setPassword("password");
        user.setEmail("mateuszkonkel132@gmail.com");
        user.setMarketingAgreement(false);
        user.setAge(44);
        user.setPhoneNumber("999666333");

        when(service.addUser(user)).thenReturn(user);
        UserDto userDto = service.addUser(user);
        assertNotNull(userDto);

        UserDto userDto1 = service.deactivateUser(user.getId(),userDto);
        assertNotNull(userDto1);

        User userEntity = repository.findByEmail(userDto1.getEmail());
        assertNotNull(userEntity);
        assertEquals(false, userEntity.getActive());
    }
}