package pl.projekt_symulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.MarketingDataMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.MarketingRepository;
import pl.projekt_symulator.repository.RoleRepository;
import pl.projekt_symulator.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
class UserServiceImplTest {

    private UserServiceImpl service;
    private UserRepository repository;

    private PasswordEncoder passwordEncoder;
    private MarketingRepository marketingRepository;
    private UserMapper userMapper;
    private MarketingDataMapper marketingMapper;
    private JavaMailSender mailSender;
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        marketingRepository = mock(MarketingRepository.class);
        userMapper = mock(UserMapper.class);
        marketingMapper = mock(MarketingDataMapper.class);
        mailSender = mock(JavaMailSender.class);
        roleRepository = mock(RoleRepository.class);
      //  jdbcTemplate = mock(jdbcTemplate)
       // service = new UserServiceImpl(jdbcTemplate, repository, roleRepository, passwordEncoder, marketingRepository, userMapper, marketingMapper, mailSender);
    }



    @Test
    public void when_searching_user_by_mail_then_return_object() {
        User userByMail = new User("mateuszkonkel132@gmail.com");
        User user = service.findUserByEmail("mateuszkonkel132@gmail.com");
        when(repository.findByEmail("mateuszkonkel132@gmail.com")).thenReturn(userByMail);

        assertEquals("mateuszkonkel132@gmail.com", user.getEmail());
    }

    @Test
    public void when_searching_user_by_wrong_mail_then_return_null() {
        User user = service.findUserByEmail("mmateuszkonkel132@gmail.com");
        assertThat(user).isNull();;
    }
    @Test
    public void when_user_doesnt_exist_return_null() {
        // given
        when(repository.findById(1L)).thenReturn(Optional.empty());
        // when
        User user = service.findUserById(1L);
        // then
        assertThat(user).isNull();
    }

    @Test
    public void when_user_exist_return_user() {
        Long userId = 6l;
        String firstName = "name";
        String lastName = "last name";
        String password = "password";
        String email = "mateuszkonkel132@gmail.com";
        String phoneNumber ="555444666";

        User user = new User();
        user.setId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);

        when(repository.findByEmail( user.getEmail())).thenReturn(user);
        // when
       User user1 = service.findUserByEmail(email);
        // then
        assertThat(user1).isNotNull();
        assertThat(user1.getEmail()).isEqualTo(user.getEmail());
    }



    @Test
    public void find_all_students() {

        User userTest1 = new User("test1@gmail.com");
        User userTest2 = new User("2test2@gmail.com");
        when(repository.findAll()).thenReturn(List.of(userTest1, userTest2));

        List<UserDto> users = service.findAllUsers();
        assertThat(users).containsExactly(userMapper.mapToDto(userTest1), userMapper.mapToDto(userTest2));

    }


    @Test
    public void when_save_user_that_it_is_returned_correctly() {


        String firstName = "name";
        String lastName = "last name";
        String password = "password";
        String email = "mateuszkonkel132@gmail.com";
        String phoneNumber ="555444666";

        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);


        when(repository.save(user)).thenReturn(user);


        UserDto userDto = new UserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPhoneNumber("555666555");
        userDto.setMarketingAgreement(true);
         // wychodzi NullPointerException - do weryfikacji !!!!!
         //service.saveUser(userDto);

    }
    @Test
    public void delete_user() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("email@.pl");

        service.saveUser(userDto);

    }

}