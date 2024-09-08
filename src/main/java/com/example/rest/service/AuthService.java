package com.example.rest.service;

import com.example.rest.constant.Role;
import com.example.rest.dto.AuthTokenResponseDto;
import com.example.rest.dto.UserRequestDto;
import com.example.rest.dto.UserResponseDto;
import com.example.rest.entity.User;
import com.example.rest.error.UnAuthenticationException;
import com.example.rest.mapper.UserMapper;
import com.example.rest.repository.RoleRepository;
import com.example.rest.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        User user = userMapper.toUser(userRequestDto);

        user.setPassword(passwordEncoder.encode(userRequestDto.password()));

        user.setRoles(roleRepository.findByNameIn(userRequestDto.roles().stream().map(Role::name)
                .collect(Collectors.toSet())));

        User saved = userRepository.save(user);

        return userMapper.toUserResponseDto(saved);
    }


    public AuthTokenResponseDto loginUser(UserRequestDto userRequestDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userRequestDto.username());
        boolean isValidPassword = passwordEncoder.matches(userRequestDto.password(), userDetails.getPassword());
        if (!isValidPassword) throw new UnAuthenticationException("Invalid username or password");

        String accessToken = jwtService.buildAccessToken(userDetails, Collections.EMPTY_MAP);
        String refreshToken = jwtService.buildRefreshToken(userDetails, Collections.EMPTY_MAP);

        return new AuthTokenResponseDto(accessToken, refreshToken);
    }


    public AuthTokenResponseDto refreshToken(String refreshToken) {
        String username = jwtService.extractUsernameRefresh(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String accessToken = jwtService.buildAccessToken(userDetails, Collections.EMPTY_MAP);
        String newRefreshToken = jwtService.buildRefreshToken(userDetails, Collections.EMPTY_MAP);

        return new AuthTokenResponseDto(accessToken, newRefreshToken);
    }


}
