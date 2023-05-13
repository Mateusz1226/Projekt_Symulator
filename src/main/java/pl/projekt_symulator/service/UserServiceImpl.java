package pl.projekt_symulator.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.MarketingData;
import pl.projekt_symulator.entity.Role;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.MarketingDataMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.MarketingRepository;
import pl.projekt_symulator.repository.RoleRepository;
import pl.projekt_symulator.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;


/*
//@Service

public class UserServiceImpl implements UserService {


    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MarketingRepository marketingRepository;
    private final UserMapper userMapper;
    private final MarketingDataMapper marketingMapper;
    private final JavaMailSender mailSender;

    public UserServiceImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, MarketingRepository marketingRepository, UserMapper userMapper, MarketingDataMapper marketingMapper, JavaMailSender mailSender) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.marketingRepository = marketingRepository;
        this.userMapper = userMapper;
        this.marketingMapper = marketingMapper;
        this.mailSender = mailSender;
    }


    @Override
    public String saveUser(UserDto userDto) {

        User user = userMapper.mapToEntity(userDto);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");


        user.setRoles(Collections.singletonList(role));
        user.setActive(true);
        userRepository.save(user);

        MarketingData marketingDataUser = marketingMapper.mapToEntity(userDto);

        marketingDataUser.setUser(user);
        marketingDataUser.setAge(marketingDataUser.getAge());
        marketingDataUser.setPhoneNumber(marketingDataUser.getPhoneNumber());
        marketingDataUser.setMarketingAgreement(marketingDataUser.getMarketingAgreement());
        marketingRepository.save(marketingDataUser);

        sendEmail(user);

        return "Dodano użytkownika";
    }

    public void deleteUserWithAppointments(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());

        MarketingData marketingData = marketingRepository.findByUserId(user.getId());
        if (marketingData != null) {
            String DELETE_MARKETING_DATA = "DELETE FROM marketing_datas where user_id =" + user.getId();
            jdbcTemplate.update(DELETE_MARKETING_DATA);

            // marketingRepository.deleteById(marketingData.getId());
        }

        String DELETE_USER_ROLE = "DELETE FROM users_roles where user_id =" + user.getId();
        jdbcTemplate.update(DELETE_USER_ROLE);

        String DELETE_USER = "DELETE FROM users where id =" + user.getId();
        jdbcTemplate.update(DELETE_USER);

        // userRepository.deleteById(userDto.getId());

    }


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> userMapper.mapToDto(user))
                .collect(Collectors.toList());
    }


    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public void sendEmail(User user) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("strzelnicanozyno@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Symulator strzelecki nożyno - rejestracja");

        message.setText("Cześć " + user.getFirstName() + " W celu potwierdzenia rejestracji kliknij w poniższy link");

        mailSender.send(message);

    }


    public String updateUser(UserDto userDto, Long id) {
        Assert.notNull(userDto.getId(), "Id nie może być puste");
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(" Brak wskazanego id  " + id));

        if (!userDto.getId().equals(id)) {
            return "dane id się nie zgadzają";
        }

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        MarketingData marketingDataUser = marketingRepository.findByUserId(user.getId());


        marketingDataUser.setUser(user);
        marketingDataUser.setAge(marketingDataUser.getAge());
        marketingDataUser.setPhoneNumber(marketingDataUser.getPhoneNumber());
        marketingDataUser.setMarketingAgreement(marketingDataUser.getMarketingAgreement());


        return "Dane zostały zmodyfikowane";

    }

    public String creatUrlForeChangingPassword(String email) {
        User user = userRepository.findByEmail(email);
        if ( user == null){
            return " nie odnaleziono adresu email " + email;
        }

        String resetPasswordLink = UriComponentsBuilder.fromPath("localhost:8080/api/simulator/resetPassword/newPassword")
                .queryParam("id", user.getId())
                .build()
                .toUriString();

        sendChangePasswordEmail(resetPasswordLink, user);
        return "Link do zmiany hasła został wysłany";
    }


    public void sendChangePasswordEmail(String resetPasswordLink,User user) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("strzelnicanozyno@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Symulator strzelecki nożyno - rejestracja");

        message.setText("Cześć " + user.getFirstName() + " W celu zmiany hasła kliknij w link " + resetPasswordLink);
        mailSender.send(message);

    }


    public String saveNewPassword(UserDto userDto) {

        User user = userMapper.mapToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return "Hasło zostało zmienione";
    }

    public String changePassword(UserDetails userDetails,String oldPassword,String newPassword1,String newPassword2 ) {


        User user = userRepository.findByEmail(userDetails.getUsername());
      // String oldEncodePassword = passwordEncoder.encode(oldPassword);

       // szyfrowanie tego samego hasła daje zawsze inny wynik

      // boolean passwordsMatch = passwordEncoder.matches(user.getPassword(), oldPasswordEncode);
        //    if (!passwordsMatch){
         //       throw new IllegalArgumentException("błędne stare hasło");
        //    }

            if (!newPassword2.equals(newPassword2)){
                throw new IllegalArgumentException("nowe hasła nie są zgodne");
            }

           user.setLastName("nowyTest");
           user.setPassword(passwordEncoder.encode(newPassword1));
          userRepository.save(user);
        return "Hasło zostało zmienione";
    }


}

*/
