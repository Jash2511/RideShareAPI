package com.epicman.rideshare.mapper;

import com.epicman.rideshare.dto.UserRegisterRequestDto;
import com.epicman.rideshare.dto.UserResponseDto;
import com.epicman.rideshare.model.UserModel;

public class UserMapper {
    public static UserModel toEntity(UserRegisterRequestDto dto) {
        UserModel user = new UserModel();
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());

        return user;
    }

    public static UserResponseDto toResponse(UserModel userModel) {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(userModel.getId());
        dto.setUsername(userModel.getUsername());
        dto.setRole(userModel.getRole());

        return dto;
    }
}
