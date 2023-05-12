package pl.projekt_symulator.service;


import org.springframework.security.core.userdetails.UserDetails;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.User;

import java.util.List;

public interface UserService {
    String saveUser(UserDto userDto);

    User findUserByEmail(String email);

    User findUserById(Long id);

    List<UserDto> findAllUsers();

    void  deleteUserWithAppointments(UserDto userDto);

    String updateUser(UserDto userDto, Long id);

    String creatUrlForeChangingPassword (String email);


   void  sendChangePasswordEmail( String resetPasswordLink,User user);

    void sendEmail(User user);

    String saveNewPassword(UserDto userDto);

    String changePassword(UserDetails userDetails, String oldPassword, String newPassword1, String newPassword2);

}
