package com.umd.sdlc.example.sdlc_project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umd.sdlc.example.sdlc_project.models.RequestUser;
import com.umd.sdlc.example.sdlc_project.models.User;
import com.umd.sdlc.example.sdlc_project.query.UserRepository;
import com.umd.sdlc.example.sdlc_project.utils.Hashing;
import com.umd.sdlc.example.sdlc_project.utils.Utility;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/get/unsecure/")
    public User getUserUnsecure(@RequestBody RequestUser user) {
        User tempUser = userRepository.findUserByEmail(user.getEmail());
        return tempUser;
    }

    @GetMapping("/get/email-check")
    public User getUserEmailCheck(@RequestBody RequestUser user) {
        if(Utility.checkForValidEmail(user.getEmail())) {
            User tempUser = userRepository.findUserByEmail(user.getEmail());
            return tempUser;
        }
        return null;
    }

    @PostMapping("/add/unsecure/no-hash")
    public ResponseEntity<String> addUserPost(@RequestBody RequestUser user) {
        String email = user.getEmail();
        String name = user.getName();
        String password = user.getPassword();
        userRepository.insertNewUser(name, email, password);
        return ResponseEntity.ok("Successful");
    }

    @PutMapping("/add/unsecure/hash")
    public ResponseEntity<String> addUserPut(@RequestBody RequestUser user) {
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        String hashedPassword = Hashing.hashString(password, Hashing.SHA_1_STRING);
        userRepository.insertNewUser(name, email, hashedPassword);
        return ResponseEntity.ok("Successful");
    }

    @PostMapping("/add/unsecure/admin")
    public ResponseEntity<String> addUserAdminPost(@RequestBody RequestUser user) {
        User tempUser = new User(user.getName(), user.getEmail(), user.getPassword());
        tempUser.setIsAdmin(user.getIsAdmin());
        userRepository.insertNewUser(tempUser.getName(), tempUser.getEmail(), tempUser.getPassword(), tempUser.getIsAdmin());
        return ResponseEntity.ok("Added");
    }

    @DeleteMapping("/")
    public ResponseEntity<String> userDelete(@RequestBody RequestUser user) {
        User tempUser = userRepository.findUserByEmail(user.getEmail());
        if(tempUser != null) {
            userRepository.delete(tempUser);
        }
        return ResponseEntity.ok("Successful");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> userDeleteById(@PathVariable("id") long id) {
        userRepository.deleteUser(id);
        return ResponseEntity.ok("Successful");
    }
}
