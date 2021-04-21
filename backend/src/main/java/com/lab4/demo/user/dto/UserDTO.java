package com.lab4.demo.user.dto;

import com.lab4.demo.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Builder.Default
    private Long id = -1L;

    @Builder.Default
    private String name = "";

    @Builder.Default
    private String email = "";

    @Builder.Default
    private String password = "";

    @Builder.Default
    private Set<Role> role =  new HashSet<>();

}
