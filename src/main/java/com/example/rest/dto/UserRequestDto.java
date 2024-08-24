package com.example.rest.dto;

import com.example.rest.constant.Role;
import java.util.List;
import lombok.ToString;

public record UserRequestDto(String username, @ToString.Exclude String password, List<Role> roles) {
}
