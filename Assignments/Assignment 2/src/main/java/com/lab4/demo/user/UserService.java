package com.lab4.demo.user;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::toDtoUserMinimal)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::toDtoUserList)
                .collect(toList());
    }


    public void delete(Long id) {
        userRepository.deleteById((id));
    }

    public UserMinimalDTO create(UserMinimalDTO user) {
        return userMapper.toDtoUserMinimal(userRepository
                .save(userMapper.fromDtoUserMinimal(user)));
    }

    public UserMinimalDTO edit(Long id, UserMinimalDTO userMinimalDTO) {
        User user = findById(id);

        if(userMinimalDTO.getName() != null)
            user.setUsername(userMinimalDTO.getName());

        return userMapper.toDtoUserList(userRepository.save(user));
    }
}
