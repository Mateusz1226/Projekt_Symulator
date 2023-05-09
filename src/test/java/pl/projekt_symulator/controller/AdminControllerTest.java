package pl.projekt_symulator.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.service.UserService;

@WebMvcTest(AdminController.class)
@WithMockUser(username = "admin", password = "pass", roles = "ADMIN,USER")
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;


    @Test
    void testGetAllUsers() throws Exception {
        List<UserDto> users = List.of(
                new UserDto(1L, "test1@gmail"));
        when(service.findAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/simulator/admin/users"))
                .andExpect(status().isOk());

    }

}