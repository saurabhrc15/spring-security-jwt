package com.example.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel {
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    @ManyToMany
    private List<Role> roles;
    private boolean isEmailVerified;

    public User() {
    }
}
