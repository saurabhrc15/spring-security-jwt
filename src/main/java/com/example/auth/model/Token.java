package com.example.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
public class Token extends BaseModel{
    private String value;
    private Date expiryAt;
    @OneToOne
    private User user;
}
