package com.example.rest.dto;

import com.example.rest.constant.Role;
import java.util.List;
import java.util.Set;
import lombok.ToString;

public record UserRequestDto(String username, @ToString.Exclude String password, Set<Role> roles) {
}
