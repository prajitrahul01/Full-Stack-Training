package org.example.Controller;

import org.bson.types.ObjectId;
import org.example.Model.Movie;
import org.example.Model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consumer")
public class ConsumerController {

    @Autowired
    UserRestConsumer userRestConsumer;

    @Autowired
    MovieRestConsumer movieRestConsumer;

    @Autowired
    TokenConsumer tokenConsumer;

    @GetMapping("/user/getall")
    public List<User> findall(){
        return userRestConsumer.findall();
    }

    @PostMapping("/registration")
    public User save(@RequestBody User usr){
        return userRestConsumer.save(usr);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody User usr){
        User usr1 =  userRestConsumer.login(usr);
        String token = "";
        if(usr1 != null)
            token = tokenConsumer.createToken(usr1.getId());
        return token;
    }

    @PostMapping("/save")
    public String save(@RequestBody Movie movie){return movieRestConsumer.save(movie);}

    @PostMapping("/update/{id}")
    public String update(@RequestBody Movie movie, @PathVariable("id") ObjectId id){
        return movieRestConsumer.update(movie, id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") ObjectId id){
        movieRestConsumer.delete(id);
    }

    @GetMapping("/get")
    public List<Movie> get(){
        return movieRestConsumer.get();
    }

//    @GetMapping("/create/{id}")
//    public String createToken(@PathVariable("id") ObjectId id){
//        return tokenConsumer.createToken(id);
//    }
//
//    @PostMapping("/getuserid")
//    public String getuserid(String token){
//        return tokenConsumer.getuserid(token);
//    }
//
//    @PostMapping("/istokenvalid")
//    public boolean isTokenValid(String token){
//        return tokenConsumer.isTokenValid(token);
//    }
}
