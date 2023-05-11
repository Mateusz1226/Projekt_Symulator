package pl.projekt_symulator.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;

import pl.projekt_symulator.service.ScheduleService;
import pl.projekt_symulator.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/api/simulator/admin")
public class AdminController {


    private final UserService userService;
    private final ScheduleService scheduleService;


    public AdminController(UserService userService, ScheduleService scheduleService) {
        this.userService = userService;
        this.scheduleService = scheduleService;

    }


    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/fullSchedule")
    public String fullSchedule(Model model) {
        List<Schedule> fullSchedule = scheduleService.fullSchedule();
        model.addAttribute("fullSchedule", fullSchedule);
        return "fullSchedule";

    }


    @GetMapping("/userDelete")
    public String userDelete(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "userDelete";
    }

    @PostMapping("/userDelete")
    public ResponseEntity<String> userDeletePost(@ModelAttribute("userDelete") UserDto userOnlyEmail,
                                                 BindingResult result,
                                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userDto", userOnlyEmail);
            return new ResponseEntity<String>("Problem z usunięciem użytkownika", HttpStatus.BAD_REQUEST);
        }

        User user = userService.findUserByEmail(userOnlyEmail.getEmail());
        if (user == null) {
            return new ResponseEntity<String>("nie odnaleziono użytkownika", HttpStatus.BAD_REQUEST);
        }

        User userWithId =  userService.findUserByEmail(userOnlyEmail.getEmail());
        scheduleService.deleteAllUserAppointments(userWithId);

        userService.deleteUserWithAppointments(userOnlyEmail);

        return new ResponseEntity<String>("Użytkownik usunięty", HttpStatus.OK);

    }



}

// funkcjonalność umożliwiająca sortowanie użytkowników i wysyłkę masową maili