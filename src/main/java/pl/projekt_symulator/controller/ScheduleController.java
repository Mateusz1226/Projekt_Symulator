package pl.projekt_symulator.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import pl.projekt_symulator.dto.ScheduleDto;

import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.service.ScheduleService;
import pl.projekt_symulator.service.UserService;

@Controller
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final UserService userService;

    public ScheduleController(ScheduleService scheduleService, UserService userService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    @GetMapping("/schedule")
    public String showScheduleForm(Model model){
        ScheduleDto scheduleDto = new ScheduleDto();
        model.addAttribute("scheduleDto", scheduleDto);

        return "schedule";
    }

    //Authentication authentication)

    @PostMapping("/schedule/save")
    public String schedule( @ModelAttribute("schedule") ScheduleDto schedule,
                                           BindingResult result,
                                           Model model,
                                           @AuthenticationPrincipal UserDetails userDetails) {

        if (result.hasErrors()) {
            model.addAttribute("schedule", schedule);
            return "schedule";
        }


           String email= userDetails.getUsername();
           User user = userService.findUserByEmail(email);


            scheduleService.book(schedule,user);

            return "schedule";
        }


    @GetMapping("/scheduleDelete")
    public String showscheduleDeleteForm(Model model){
        ScheduleDto scheduleDto = new ScheduleDto();
        model.addAttribute("scheduleDto", scheduleDto);

        return "scheduleDelete";
    }



    @PostMapping("/schedule/delete")
    public String unbookTerm(@ModelAttribute("schedule") ScheduleDto schedule,
                             BindingResult result,
                             Model model,
                             @AuthenticationPrincipal UserDetails userDetails) {

        String email= userDetails.getUsername();
        User user = userService.findUserByEmail(email);
        scheduleService.unbook(schedule,user);

        return "Przekierowanie info o  udanym anulowaniu rezerwacji";

    }
}
