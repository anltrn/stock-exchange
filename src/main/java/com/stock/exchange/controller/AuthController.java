package com.stock.exchange.controller;

import com.stock.exchange.model.UserLoginRequest;
import com.stock.exchange.model.UserSignupRequest;
import com.stock.exchange.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequest loginRequest) {
        String jwtToken = userService.signIn(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignupRequest signUpRequest) {
        userService.signUp(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.delete(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
