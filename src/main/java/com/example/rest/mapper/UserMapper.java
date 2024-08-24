package com.example.rest.mapper;

import com.example.rest.constant.Role;
import com.example.rest.dto.UserRequestDto;
import com.example.rest.dto.UserResponseDto;
import com.example.rest.entity.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User toUser(UserRequestDto requestDto);

    @Mapping(target = "roles", expression = "java(toRoles(user))")
    UserResponseDto toUserResponseDto(User user);

    default Set<Role> toRoles(User user) {
        return user.getRoles().stream().map(r -> Role.valueOf(r.getName())).collect(Collectors.toSet());
    }

}
