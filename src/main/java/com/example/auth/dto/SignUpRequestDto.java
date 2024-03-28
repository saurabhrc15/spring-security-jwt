package com.example.auth.dto;

import com.example.auth.model.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String first_name;
    private String middle_name;
    private String last_name;
    private String gender;
    private String email;
    private String password;
}
