package com.example.auth.controller;

import com.example.auth.dto.LoginRequestDto;
import com.example.auth.dto.SignUpRequestDto;
import com.example.auth.model.Token;
import com.example.auth.model.User;
import com.example.auth.service.UserService;
import com.example.auth.wrapper.ValidateTokenResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("user/signup")
    public ResponseEntity<User> registerUser(@RequestBody SignUpRequestDto signUpRequestDto) {
        User user = userService.registerUser(signUpRequestDto.getFirst_name(), signUpRequestDto.getMiddle_name(),
                signUpRequestDto.getLast_name(), signUpRequestDto.getGender(), signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("user/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequestDto loginRequestDto) {
        Token token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("user/logout")
    public ResponseEntity<Void> login(@RequestHeader(value = "Authorization") String authorizationHeader) {
        String value = "";
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            value = authorizationHeader.substring(7);
        }

        userService.logout(value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("token/validateToken")
    public ResponseEntity<ValidateTokenResponseWrapper> validateToken(@RequestHeader(value = "Authorization") String authorizationHeader) {
        String value = "";
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            value = authorizationHeader.substring(7);
        }

        boolean result = userService.validateToken(value);
        ValidateTokenResponseWrapper validateTokenResponseWrapper = new ValidateTokenResponseWrapper();
        validateTokenResponseWrapper.setResult(result);
        return new ResponseEntity<>(validateTokenResponseWrapper, HttpStatus.OK);
    }
}
