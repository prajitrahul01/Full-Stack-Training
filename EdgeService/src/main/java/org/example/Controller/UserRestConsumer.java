package org.example.Controller;

import org.example.Model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient("user-service/api/user")
public interface UserRestConsumer {

    @PostMapping("/registration")
    public User save(@RequestBody User usr);

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User login(@RequestBody User usr);

    @GetMapping("/getall")
    public List<User> findall();
}
