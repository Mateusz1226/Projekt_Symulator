package pl.projekt_symulator.service;



import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.MarketingData;
import pl.projekt_symulator.entity.Role;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.MarketingDataMapper;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.MarketingRepository;
import pl.projekt_symulator.repository.RoleRepository;
import pl.projekt_symulator.repository.UserRepository;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MarketingRepository marketingRepository;
    private final UserMapper userMapper;
    private final MarketingDataMapper marketingMapper;
    private final JavaMailSender mailSender;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, MarketingRepository marketingRepository, UserMapper userMapper, MarketingDataMapper marketingMapper, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.marketingRepository = marketingRepository;
        this.userMapper = userMapper;
        this.marketingMapper = marketingMapper;
        this.mailSender = mailSender;
    }


    @Override
    public void saveUser(UserDto userDto) {

        User user = userMapper.mapToEntity(userDto);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        user.setActive(true);
        userRepository.save(user);

        MarketingData marketingDataUser = marketingMapper.mapToEntity(userDto);
        // MarketingData marketingData = new MarketingData();
        marketingDataUser.setUser(user);
        marketingDataUser.setAge(marketingDataUser.getAge());
        marketingDataUser.setPhoneNumber(marketingDataUser.getPhoneNumber());
        marketingDataUser.setMarketingAgreement(marketingDataUser.getMarketingAgreement());
        marketingRepository.save(marketingDataUser);

        sendEmail(user);


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


   public UserDto findUserById (Long id) {
     User user =  userRepository.findById(id).orElse(null);

        return userMapper.mapToDto(user);
   }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }


    public void sendEmail(User user) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("strzelnicanozyno@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Symulator strzelecki nożyno - rejestracja");
        message.setText("Cześć" + user.getFirstName() + " W celu potwierdzenia rejestracji kliknij w poniższy link");

        mailSender.send(message);

    }

}