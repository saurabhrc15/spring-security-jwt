package com.example.auth.service;

import com.example.auth.model.BaseModel;
import com.example.auth.model.Token;
import com.example.auth.model.User;
import com.example.auth.repository.TokenRepository;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends BaseModel {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(String firstName, String middleName, String lastName, String gender, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setGender(gender);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setIsDeleted(false);

        return userRepository.save(user);
    }

    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }

        User user = userOptional.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        Token token = new Token();
        token.setValue(UUID.randomUUID().toString());
        Date expiredDate = getExpiryDate();
        token.setExpiryAt(expiredDate);
        token.setUser(user);
        token.setIsDeleted(false);

        return tokenRepository.save(token);
    }

    public Date getExpiryDate() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(new Date());

        calendarDate.add(Calendar.DAY_OF_MONTH, 30);
        Date expiredDate = calendarDate.getTime();

        return expiredDate;
    }

    public void logout(String value) {
        Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeletedEquals(value, false);

        if (optionalToken.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        Token token = optionalToken.get();
        token.setIsDeleted(true);
        tokenRepository.save(token);
    }

    public boolean validateToken(String token) {
        Optional<Token> optionalToken = tokenRepository.findByValueAndIsDeletedEqualsAndExpiryAtGreaterThan(token, false, new Date());

        if (optionalToken.isEmpty()) {
            return false;
        }

        return true;
    }
}
