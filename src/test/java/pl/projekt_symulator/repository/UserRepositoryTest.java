package pl.projekt_symulator.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.MarketingDataMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);

    }



     @Test
     public void find_by_email_then_return_user() {

         Long userId = 6l;
         String firstName = "name";
         String lastName = "last name";
         String password = "password";
         String email = "mateuszkonkel132@gmail.com";

         User user = new User();
         user.setId(userId);
         user.setFirstName(firstName);
         user.setLastName(lastName);
         user.setPassword(password);
         user.setEmail(email);

     when(userRepository.findByEmail("mateuszkonkel132@gmail.com")).thenReturn(user);

     User result =  userRepository.findByEmail("mateuszkonkel132@gmail.com");


    }


    @Test
    void save_user() {

            Long userId = 6l;
            String firstName = "name";
            String lastName = "last name";
            String password = "password";
            String email = "mateuszkonkel132@gmail.com";

            User user = new User();
            user.setId(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setEmail(email);

             when(userRepository.save(user)).thenReturn(user);
    }
}