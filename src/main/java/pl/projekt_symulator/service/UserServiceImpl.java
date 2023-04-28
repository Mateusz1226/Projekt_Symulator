package pl.projekt_symulator.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.MarketingData;
import pl.projekt_symulator.entity.Role;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.mapper.UserMapper;
import pl.projekt_symulator.repository.MarketingRepository;
import pl.projekt_symulator.repository.RoleRepository;
import pl.projekt_symulator.repository.UserRepository;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final MarketingRepository marketingRepository;

    private final UserMapper userMapper;




    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, MarketingRepository marketingRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.marketingRepository = marketingRepository;
        this.userMapper = userMapper;
    }


    @Override
    public void saveUser(UserDto userDto) {

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

       Role role = roleRepository.findByName("ROLE_USER");
        if(role == null){
          role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        user.setActive(true);
        userRepository.save(user);


        MarketingData marketingData = new MarketingData();
        marketingData.setUser(user);
        marketingData.setAge(userDto.getAge());
        marketingData.setMarketingAgreement(userDto.getMarketingAgreement());
        marketingRepository.save(marketingData);

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

  /*/  private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }*/

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }


    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(User user)  {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("strzelnicanozyno@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Symulator strzelecki nożyno - rejestracja");
        message.setText("Cześć"  + user.getFirstName() +" W celu potwierdzenia rejestracji kliknij w poniższy link");

        mailSender.send(message);

    }

}