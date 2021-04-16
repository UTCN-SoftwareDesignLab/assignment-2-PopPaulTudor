package com.lab4.demo.user;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.BOOKS;
import static com.lab4.demo.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }


    @PostMapping
    public UserMinimalDTO create(@RequestBody UserMinimalDTO user) {
        return userService.create(user);
    }

    @PutMapping
    public UserMinimalDTO edit(@PathVariable Long id, @RequestBody UserMinimalDTO userMinimalDTO) {
        return userService.edit(id, userMinimalDTO);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
