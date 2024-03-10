package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.UserService;

import java.util.concurrent.ExecutionException;


@RestController
public class UserController {

   public UserService userService;
   public RouteController routeController;

   public UserController(UserService userService) {
       this.userService = userService;
   }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) throws InterruptedException, ExecutionException {
        return userService.createUser(user);
    }


    @GetMapping("/get")
   public User getUser(@RequestParam String documentId) throws InterruptedException, ExecutionException {
       return userService.getUser(documentId);
   }

   @PutMapping("/update")
   public String updateUser(@RequestBody User user) throws InterruptedException, ExecutionException {
       return userService.updateUser(user);
   }

   @DeleteMapping("/delete")
   public String deleteUser(@RequestParam String documentId) throws InterruptedException, ExecutionException {
       return userService.deleteUser(documentId);
   }

   @GetMapping("/test")
   public ResponseEntity<String> testGetEndPoint() {
       return ResponseEntity.ok("Teste Ok");
   }

}
