package com.y.Y;

import com.y.Y.features.user.User;
import com.y.Y.features.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(UserControllerTest.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    void shouldReturnListOfUsers() throws Exception {
        when(service.getUsers()).thenReturn(List.of(new User(), new User()));

        this.mockMvc.perform(get("/api/users/"));
    }


}
