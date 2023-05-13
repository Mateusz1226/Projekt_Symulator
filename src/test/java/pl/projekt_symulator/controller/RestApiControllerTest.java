package pl.projekt_symulator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.service.RestUserService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestApiController.class)
class RestApiControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestUserService service;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void testRegistrationSave() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("email@email.pl");
        userDto.setPhoneNumber("555666555");
        userDto.setPassword("hasło");
        userDto.setAge(44);
        userDto.setMarketingAgreement(true);


        when(service.addUser(userDto)).thenReturn(userDto);
        UserDto userDto1 = service.addUser(userDto);

        mockMvc.perform(post("/restapi/simulator/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("email@email.pl")))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.phoneNumber", is("555666555")))
                .andExpect(jsonPath("$.age", is("44")));
    }

    @Test
    void testGetUser() throws Exception  {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("email@email.pl");
        userDto.setPhoneNumber("555666555");
        userDto.setPassword("hasło");
        userDto.setAge(44);
        userDto.setMarketingAgreement(true);

        when(service.addUser(userDto)).thenReturn(userDto);
        UserDto userDto1 = service.addUser(userDto);

        mockMvc.perform(get("/"+userDto1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto1.getId())))
                .andExpect(jsonPath("$.email", is("email@email.pl")))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.phoneNumber", is("555666555")))
                .andExpect(jsonPath("$.age", is("44")));

    }

    @Test
    void testChangeMarketingAgreement() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("email@email.pl");
        userDto.setPhoneNumber("555666555");
        userDto.setPassword("hasło");
        userDto.setAge(44);
        userDto.setMarketingAgreement(false);

        when(service.addUser(userDto)).thenReturn(userDto);
        UserDto userDto1 = service.addUser(userDto);


        mockMvc.perform(put("change/MarketingAgreement/"+userDto1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto1.getId())))
                .andExpect(jsonPath("$.email", is("email@email.pl")))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.phoneNumber", is("555666555")))
                .andExpect(jsonPath("$.marketingAgreement", is(false)))
                .andExpect(jsonPath("$.age", is("44")));

    }

    @Test
    void changeUserPhone() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("email@email.pl");
        userDto.setPhoneNumber("111222333");
        userDto.setPassword("hasło");
        userDto.setAge(44);
        userDto.setMarketingAgreement(true);

        when(service.addUser(userDto)).thenReturn(userDto);
        UserDto userDto1 = service.addUser(userDto);


        mockMvc.perform(put("change/UserPhone/"+userDto1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto1.getId())))
                .andExpect(jsonPath("$.email", is("email@email.pl")))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.phoneNumber", is("111222333")))
                .andExpect(jsonPath("$.age", is("44")));

    }
    }

