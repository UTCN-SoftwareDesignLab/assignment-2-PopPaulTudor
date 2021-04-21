package com.lab4.demo.user;


import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


import static com.lab4.demo.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        userRepository.saveAll(users);

        List<UserDTO> userMinimalDTOS = userService.allUsersForList();

        Assertions.assertEquals(userMinimalDTOS.size(), users.size());
    }


    @Test
    void create() {
        List<User> users = TestCreationFactory.listOf(User.class);
        userRepository.saveAll(users);

        List<UserDTO> userMinimalDTOS = userService.allUsersForList();
        Assertions.assertEquals(userMinimalDTOS.size(), users.size());
    }


    @Test
    void delete(){
        List<User> users = TestCreationFactory.listOf(User.class);
        UserDTO userInserted = userService.create(userMapper.toUserDTO(users.get(0)));
        userService.delete(userInserted.getId());
        assertFalse(userRepository.findById(userInserted.getId()).isPresent());

    }

    @Test
    void edit(){
        List<User> users = TestCreationFactory.listOf(User.class);
        String newName = randomString();
        UserDTO userInserted = userService.create(userMapper.toUserDTO(users.get(0)));

        UserDTO userMinimalDTO = UserDTO.builder()
                .id(userInserted.getId())
                .name(newName)
                .build();
        UserDTO result = userService.edit(userInserted.getId(), userMinimalDTO);
        assertEquals(result.getName(), newName);
    }







}