package com.umd.sdlc.example.sdlc_project.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umd.sdlc.example.sdlc_project.models.User;
import com.umd.sdlc.example.sdlc_project.query.UserRepository;
import com.umd.sdlc.example.sdlc_project.utils.HttpClient;
import com.umd.sdlc.example.sdlc_project.utils.Nesting;

/**
 * 
 */
@RestController
@RequestMapping("/api/security")
public class SecurityController {

    UserRepository userRepository;

    public SecurityController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Example API call to try and trick SAST into thinking the data is sanitized by passing it through some transformations.
     * @param email - email to check
     * @return - User or null
     */
    @GetMapping("/nested")
    public User getNestedUser(@RequestParam("email") String email) {
        String tempEmail = email;
        Nesting nested = new Nesting(tempEmail);
        String nestedEmail = nested.getSanitizedEmail();
        User user = userRepository.findUserByEmail(nestedEmail);
        return user;
    }

    /**
     * Example API call to make an API call and return the result. This doesn't check for valid emails and SSL verification has been disabled by default
     * @param url - url to query
     * @return - response or stack trace
     */
    @GetMapping("/make-api-call")
    public ResponseEntity<String> makeApiCall(@RequestParam("url") String url) {
        return ResponseEntity.ok(HttpClient.makeHttpRequestSimple(url));
    }

    /**
     * Example API call to check for a valid API key in order to make an outgoing url request
     * @param headers - incoming headers
     * @param url - url to query
     * @return - response or stack trace
     */
    @GetMapping("/headers/make-api-call")
    public ResponseEntity<String> makeApiCallWithHeaders(@RequestHeader Map<String, String> headers, @RequestParam("url") String url) {
        boolean hasApiKey = headers.entrySet().stream()
            .anyMatch(entry -> entry.getKey().equals("X-API"));

        if(hasApiKey) {
            String response = HttpClient.makeHttpRequestSimple(url);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
    }
}
