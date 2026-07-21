package com.example.course_ventures.dto;

import com.example.course_ventures.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private int id;
    private String name;
    private String email;
    private Role role;
    private boolean verified;
}
