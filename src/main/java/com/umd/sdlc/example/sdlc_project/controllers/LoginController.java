package com.umd.sdlc.example.sdlc_project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umd.sdlc.example.sdlc_project.models.RequestUser;
import com.umd.sdlc.example.sdlc_project.models.User;
import com.umd.sdlc.example.sdlc_project.query.UserRepository;
import com.umd.sdlc.example.sdlc_project.utils.Hashing;


@RestController
@RequestMapping("/api/login")
public class LoginController {
    
    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/unsecure")
    public ResponseEntity<String> loginUserGet(@RequestBody RequestUser user) {
        User tempUser = userRepository.findUserByEmailPassword(user.getEmail(), user.getPassword());
        if(tempUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(tempUser.toString());
        }
        return ResponseEntity.ok("No user found");
    }

    @PostMapping("/")
    public ResponseEntity<String> loginUserPost(@RequestBody RequestUser user) {
        String email = user.getEmail();
        String password = Hashing.hashString(user.getPassword(), Hashing.SHA_256_STRING);
        User tempUser = userRepository.findUserByEmailPassword(email, password);
        if(tempUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(tempUser.toString());
        }
        return ResponseEntity.ok("No user found");
    }
}
