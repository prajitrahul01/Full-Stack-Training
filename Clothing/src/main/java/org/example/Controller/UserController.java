package org.example.Controller;


import org.example.Model.User;
import org.example.Model.UserForms;
import org.example.Service.TokenService;
import org.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins ="*",allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    // Endpoint to register a new user
    @PostMapping("/registration")
    public User save(@RequestBody User usrForms){
        return userService.save(usrForms);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody User usr){
        return userService.userLogin(usr);
    }

}