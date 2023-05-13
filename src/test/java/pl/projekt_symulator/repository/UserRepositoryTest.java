package pl.projekt_symulator.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import pl.projekt_symulator.entity.User;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);

    }

     @Test
     public void find_by_email_then_return_user() {

         User user = new User();
         user.setId(6l);
         user.setFirstName("name");
         user.setLastName("last name");
         user.setPassword("password");
         user.setEmail("mateuszkonkel132@gmail.com");

     when(userRepository.findByEmail("mateuszkonkel132@gmail.com")).thenReturn(user);
     User result =  userRepository.findByEmail("mateuszkonkel132@gmail.com");
     assertNotNull(user);
     assertEquals(user.getFirstName(), result.getFirstName());
    }

    @Test
    void save_user() {

            User user = new User();
            user.setId(6l);
            user.setFirstName("name");
            user.setLastName("last name");
            user.setPassword("password");
            user.setEmail("mateuszkonkel132@gmail.com");

             when(userRepository.save(user)).thenReturn(user);

             User user1 = userRepository.save(user);
             assertNotNull(user1);
             assertEquals(user.getEmail(),user1.getEmail());
             assertEquals(user.getFirstName(),user1.getFirstName());
             assertEquals(user.getLastName(),user1.getLastName());
             assertEquals(user.getPassword(),user1.getPassword());

    }
}