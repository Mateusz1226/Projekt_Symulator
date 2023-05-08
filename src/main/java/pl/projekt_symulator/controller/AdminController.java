package pl.projekt_symulator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/api/simulator/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


    // funkcjonalność umożliwiająca sortowanie użytkowników i wysyłkę masową maili

}

