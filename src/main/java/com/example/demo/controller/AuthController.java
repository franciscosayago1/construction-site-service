package com.example.demo.controller;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.LoginResponse;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserAccountRepository userAccountRepository;

    public AuthController(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping("/register")
    public UserAccount register(@RequestBody UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserAccount user = userAccountRepository.findById(loginRequest.getUsername()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        LoginResponse response = new LoginResponse(
                user.getUsername(),
                user.getRole(),
                user.getEmployeeId()
        );

        return ResponseEntity.ok(response);
    }
}
