package com.example.demod.services.User;

import com.example.demod.dto.LoginDto;
import com.example.demod.dto.TokenDto;
import com.example.demod.dto.UserDto;
import com.example.demod.entities.User;
import com.example.demod.exceptions.EtAuthException;

public interface UserService {
    UserDto registerUser(UserDto user) throws EtAuthException;

    TokenDto getToken(LoginDto user) throws EtAuthException;

    User loginUser(TokenDto user) throws EtAuthException;

}
