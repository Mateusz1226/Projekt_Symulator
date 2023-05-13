package pl.projekt_symulator.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.projekt_symulator.entity.MarketingData;
import pl.projekt_symulator.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJpaTest
class MarketingRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarketingRepository marketingRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        marketingRepository = mock(MarketingRepository.class);
    }

    @Test
    void find_marketing_data_by_user_id() {

        User user = new User();
        user.setId(6l);
        user.setFirstName("name");
        user.setLastName("last name");
        user.setPassword("password");
        user.setEmail("mateuszkonkel132@gmail.com");

        when(userRepository.save(user)).thenReturn(user);
        User user1 = userRepository.save(user);
        MarketingData marketingData = marketingRepository.findByUserId(user1.getId());
        assertNotNull(marketingData);

    }
}
