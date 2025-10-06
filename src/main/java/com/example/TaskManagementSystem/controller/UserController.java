package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.model.User;
import com.example.TaskManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService us;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        try {
            User createdUser = us.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        try {
            User u = us.getUserById(id);
            return ResponseEntity.ok(u);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
