package pl.projekt_symulator.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import pl.projekt_symulator.dto.ScheduleDto;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.service.ScheduleService;

@Controller
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
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
                                           Model model) {

        if (result.hasErrors()) {
            model.addAttribute("schedule", schedule);
            return "/schedule";
        }
            scheduleService.book(schedule, schedule.getId());

            return "zrealizowane";
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
                             Model model) {
        return scheduleService.unbook(schedule, schedule.getId());
    }
}
