package org.example.Controller;

import org.bson.types.ObjectId;
import org.example.Model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("movie-service/movie")
public interface MovieRestConsumer {
    @PostMapping("/save")
    public String save(@RequestBody Movie movie);

    @PostMapping("/update/{id}")
    public String update(@RequestBody Movie movie, @PathVariable("id") ObjectId id);

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") ObjectId id);

    @GetMapping("/get")
    public List<Movie> get();

}
