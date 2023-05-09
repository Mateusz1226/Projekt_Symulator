package pl.projekt_symulator.service;


import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.User;

import java.util.List;

public interface UserService {
    String saveUser(UserDto userDto);

    User findUserByEmail(String email);

    User findUserById(Long id);

    List<UserDto> findAllUsers();
}
