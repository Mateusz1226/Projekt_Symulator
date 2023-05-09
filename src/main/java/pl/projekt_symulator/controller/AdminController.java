package pl.projekt_symulator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.projekt_symulator.dto.ScheduleDto;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.mapper.ScheduleMapper;
import pl.projekt_symulator.service.ScheduleService;
import pl.projekt_symulator.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/api/simulator/admin")
public class AdminController {

     private final UserService userService;
     private final ScheduleService scheduleService;
     private final ScheduleMapper scheduleMapper;

    public AdminController(UserService userService, ScheduleService scheduleService, ScheduleMapper scheduleMapper) {
        this.userService = userService;
        this.scheduleService = scheduleService;

        this.scheduleMapper = scheduleMapper;
    }


    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/fullSchedule")
    public String fullSchedule(Model model){
        List<ScheduleDto> fullSchedule = scheduleService.fullSchedule();
        model.addAttribute("schedule", fullSchedule);
        return "fullSchedule";

    }


    // funkcjonalność umożliwiająca sortowanie użytkowników i wysyłkę masową maili
    // funkcjonalność pełen grafik

}

