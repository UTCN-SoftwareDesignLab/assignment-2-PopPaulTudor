package com.lab4.demo.user;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    @GetMapping()
    public List<UserDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public void create(@RequestBody SignupRequest signupRequest) {
        authService.register(signupRequest);
    }

    @PatchMapping(ENTITY)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.edit(id, userDTO);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
