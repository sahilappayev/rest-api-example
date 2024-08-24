package com.example.rest.dto;

import com.example.rest.constant.Role;
import java.util.Set;

public record UserResponseDto(String username, Set<Role> roles) {

}
