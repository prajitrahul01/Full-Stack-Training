package org.example.Controller;

import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("token-service/auth")
public interface TokenConsumer {
    @PostMapping("/create/{id}")
    public String createToken(@PathVariable("id") ObjectId id);

    @PostMapping("/getuserid")
    public String getuserid(String token);

    @PostMapping("/istokenvalid")
    public boolean isTokenValid(String token);
}
