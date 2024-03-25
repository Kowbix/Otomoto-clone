package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.user.UserLoginDTO;
import org.example.otomotoclon.dto.user.UserRegisterDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.serivce.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserRegisterDTO user) {
        try {
            authenticationService.registerUser(user);
        } catch (ObjectExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("User registered successfully", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody UserLoginDTO user) {
        String token = null;
        try {
            token = authenticationService.login(user);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response(token, HttpStatus.CREATED.value()));
    }


}
