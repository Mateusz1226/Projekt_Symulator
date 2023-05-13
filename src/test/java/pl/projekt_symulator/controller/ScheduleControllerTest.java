package pl.projekt_symulator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.projekt_symulator.dto.ScheduleDto;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.service.ScheduleService;
import pl.projekt_symulator.service.UserService;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ScheduleService service;

    @MockBean
    private AuthenticationManager authenticationManager;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void testGetScheduleController() throws Exception {
        mockMvc.perform(get("/api/simulator/schedule").with(user("admin").password("pass").roles("ADMIN,USER")))
                .andExpect(status().isOk());

    }

    @Test
    void testPostScheduleController() throws Exception {

        LocalDateTime start = LocalDateTime.of(2023,05,11, 18,41);
        LocalDateTime stop = LocalDateTime.of(2023,05,11, 19,41);
        ScheduleDto schedule = new ScheduleDto(null, start, stop);
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email@email.pl");
        user.setId(1L);

        when(service.book(schedule,user)).thenReturn(new String("Termin został zarezerwowany"));
        // then
        mockMvc.perform(post("/api/simulator/schedule").with(user("admin").password("pass").roles("ADMIN,USER")))
                .andExpect(status().isOk());
    }


    @Test
    void testDeleteScheduleController() throws Exception {
        mockMvc.perform(get("/api/simulator/schedule/delete").with(user("admin").password("pass").roles("ADMIN,USER")))
                .andExpect(status().isOk());

    }

    }





