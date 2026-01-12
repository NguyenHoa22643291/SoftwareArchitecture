package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest request) {
        if ("admin".equals(request.getUsername()) && "123".equals(request.getPassword())) {
            return tokenProvider.generateToken(request.getUsername());
        }
        return "Sai tài khoản hoặc mật khẩu!";
    }
}