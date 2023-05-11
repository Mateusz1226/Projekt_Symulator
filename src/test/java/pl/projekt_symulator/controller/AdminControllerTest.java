package pl.projekt_symulator.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import pl.projekt_symulator.dto.ScheduleDto;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.Schedule;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.service.ScheduleService;
import pl.projekt_symulator.service.UserService;

@WebMvcTest(AdminController.class)
@WithMockUser(username = "admin", password = "pass", roles = "ADMIN,USER")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;
    @MockBean
    private ScheduleService scheduleService;

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDto> users = List.of(
                new UserDto(1L, "test1@gmail"));
        when(service.findAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/simulator/admin/users"))
                .andExpect(status().isOk());

    }


    @Test
    void testGetFullSchedule() throws Exception {

        LocalDateTime start = LocalDateTime.of(2023, 05, 11, 18, 41);
        LocalDateTime end = LocalDateTime.of(2023, 05, 11, 19, 41);
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@email.pl");
        user.setId(1L);


        List<Schedule> scheduleDtoList = List.of(
                new Schedule(1l, start, end,user));
        when(scheduleService.fullSchedule()).thenReturn(scheduleDtoList);
        mockMvc.perform(get("/api/simulator/admin/fullSchedule"))
                .andExpect(status().isOk());

    }

}