package com.example.demod.controllers;



import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demod.dto.LoginDto;
import com.example.demod.dto.TokenDto;
import com.example.demod.dto.UserDto;
import com.example.demod.entities.User;
import com.example.demod.exceptions.EtAuthException;
import com.example.demod.services.User.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

UserService userService;
@Async
@PostMapping(path = "/regestier")
    public CompletableFuture< ResponseEntity<UserDto>> userRegister(@RequestBody UserDto user){
        try {
            return CompletableFuture.supplyAsync(()->{
                UserDto savedUser=userService.registerUser(user);

                return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
            });


        } catch (Exception e) {
               throw new EtAuthException(e.getMessage());
        }
    }

    @Async
    @PostMapping(path = "/token")
    public CompletableFuture<ResponseEntity<TokenDto>> getToken(@RequestBody LoginDto user){
        try {
            return CompletableFuture.supplyAsync(()->{
TokenDto tk=userService.getToken(user);
return new ResponseEntity<>(tk,HttpStatus.OK);
            });
        } catch (Exception e) {
            throw new EtAuthException(e.getMessage());
        }
    }





    @Async
    @GetMapping(path = "/login")
    public CompletableFuture< ResponseEntity<User>>userLogin(@RequestBody TokenDto user){
try {
    return CompletableFuture.supplyAsync(()->{
       
        User loged=userService.loginUser(user);
        return new ResponseEntity<>(loged,HttpStatus.FOUND);
    });

} catch (Exception e) {
    throw new EtAuthException(e.getMessage());
}
    }
}
