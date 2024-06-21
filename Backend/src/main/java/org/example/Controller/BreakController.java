//package org.example.Controller;
//
//import org.example.Model.Break;
//import org.example.Repository.BreakRepository;
//import org.example.Service.BreakService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@RequestMapping("/api/break")
//public class BreakController {
//    @Autowired
//    private BreakService breakService;
//
//    @PostMapping("/breakRegister")
//    public Break save(@RequestBody Break break1){
//        return breakService.save(break1);
//    }
//}
