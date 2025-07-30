package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(new User("user1", "user1@example.com"));
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(get("/api/users/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user1"));
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("user2", "user2@example.com");
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user2"));
    }
}

