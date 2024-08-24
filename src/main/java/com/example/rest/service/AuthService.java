package com.example.rest.service;

import com.example.rest.constant.Role;
import com.example.rest.dto.UserRequestDto;
import com.example.rest.dto.UserResponseDto;
import com.example.rest.entity.User;
import com.example.rest.mapper.UserMapper;
import com.example.rest.repository.RoleRepository;
import com.example.rest.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        User user = userMapper.toUser(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.password()));
        user.setRoles(roleRepository.findByNameIn(userRequestDto.roles().stream().map(Role::name).toList()));

        User saved = userRepository.save(user);

        return userMapper.toUserResponseDto(saved);
    }


}
