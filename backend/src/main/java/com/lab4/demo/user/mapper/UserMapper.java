package com.lab4.demo.user.mapper;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "email", source = "user.email")
    })
    UserMinimalDTO toDtoUserMinimal(User user);

    @Mappings({
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO toDtoUserList(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles());
    }


    User fromDtoUserMinimal(UserMinimalDTO userDTO);

    User fromDtoUserList(UserListDTO userListDTO);

    User fromUserDTO(UserDTO userDTO);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "role", source = "roles")

    })
    UserDTO toUserDTO(User user);


}
