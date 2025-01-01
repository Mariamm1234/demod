package com.example.demod.mapper;

import java.util.HashSet;

import com.example.demod.common.role;
import com.example.demod.dto.LoginDto;
import com.example.demod.dto.TokenDto;
import com.example.demod.dto.UserDto;
import com.example.demod.entities.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {

        return new UserDto(
                user.getUser_id(),
                user.getFullName(),
                user.getUserEmail(),
                user.getUserPassword(),
                user.getUserRole());
    }

    public static User maptoToUser(UserDto user) {
        return new User(
                user.getId(),
                user.getFullName(),
                user.getUserEmail(),
                user.getUserPassword(),
                user.getUserRole(),
                "");
    }

}
