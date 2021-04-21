package com.lab4.demo.user.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserMinimalDTO {

    @Builder.Default
    private Long id = -1L;

    @Builder.Default
    private String email = "";
}