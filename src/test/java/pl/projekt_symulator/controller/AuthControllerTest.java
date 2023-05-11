package pl.projekt_symulator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.service.UserService;

@WebMvcTest(AuthController.class)
@WithMockUser(username = "admin", password = "pass", roles = "ADMIN,USER")
class AuthControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;
    @MockBean
    private AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void testLogin() throws Exception {
        mockMvc.perform(get("/api/simulator/login").with(user("admin").password("pass").roles("ADMIN,USER")))
                .andExpect(status().isOk());

    }



    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(get("/api/simulator/register").with(user("admin").password("pass").roles("ADMIN,USER")))
                .andExpect(status().isOk());

    }

    @Test
    void testRegistrationSaveController() throws Exception {
        mockMvc.perform(post("/api/simulator/register/save").with(user("admin").password("pass").roles("ADMIN,USER")))
                .andExpect(status().isOk());

    }

    @Test
    void testRegistrationSave() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("email@email.pl");
        userDto.setPhoneNumber("555666555");
        userDto.setAge(44);
        userDto.setMarketingAgreement(true);
        userDto.setId(1L);

      when(service.saveUser(userDto)).thenReturn("Dodano użytkownika");

        mockMvc.perform(post("/api/simulator/register/save")
                        .with(user("admin").password("pass").roles("ADMIN", "USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("email@email.pl")))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.phoneNumber", is("555666555")))
                .andExpect(jsonPath("$.age", is("44")));
    }

    }
