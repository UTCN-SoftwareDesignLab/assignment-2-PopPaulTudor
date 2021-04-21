package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.USERS;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService, authService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserDTO> userDTOS = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userDTOS);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTOS));
    }


    @Test
    void create() throws Exception {
        UserDTO dto = UserDTO.builder()
                .name(randomString())
                .email(randomEmail())
                .password(randomString())
                .id(randomLong())
                .build();

        when(userService.create(dto)).thenReturn(dto);

        ResultActions result = performPostWithRequestBody(USERS, dto);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(dto));

    }

    @Test
    void edit() throws Exception{
        long id = randomLong();
        UserDTO dto = UserDTO.builder()
                .id(id)
                .name(randomString())
                .build();

        when(userService.edit(id, dto)).thenReturn(dto);

        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS + ENTITY, dto, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(dto));
    }

    @Test
    void delete() throws Exception{
        long id = randomLong();
        doNothing().when(userService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(USERS + ENTITY, id);
        verify(userService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }
}