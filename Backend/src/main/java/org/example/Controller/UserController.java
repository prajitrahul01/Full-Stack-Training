package org.example.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.Exception.EmailAlreadyExistsException;
import org.example.Exception.InvalidCredentialsException;
import org.example.Exception.NoAdmin;
import org.example.Exception.UserNotFoundException;
import org.example.Model.*;
import org.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> save(@RequestBody String userJson) throws JsonProcessingException {

        if (!userService.isValidJson(userJson)) {
            return ResponseEntity.badRequest().body("Invalid JSON format.");
        }

        String validationResult = userService.isValidUserJson(userJson);
        if (validationResult != null) {
            return ResponseEntity.badRequest().body(validationResult);
        }

        User user;
        try {
            user = userService.convertJsonToUser(userJson);
            System.out.println("user: "+user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing JSON to User object.");
        }

        try {
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("Email ID is already registered.");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            List<userForm> userList = userService.getAll();
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            String errorMessage = "Failed to fetch user data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/getAll2")
    public ResponseEntity<?> getAll2() {
        try {
            List<userForm> userList = userService.getAll();
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            String errorMessage = "Failed to fetch user data: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/gender-count")
    public ResponseEntity<String> getGenderCount() {
        String genderCountJson = userService.getGenderCount();
        return new ResponseEntity<>(genderCountJson, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            String token = userService.userLogin(user);
            return ResponseEntity.ok(token);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: User not found");
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: Invalid credentials");
        }catch (NoAdmin e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: Only admin is allowed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
