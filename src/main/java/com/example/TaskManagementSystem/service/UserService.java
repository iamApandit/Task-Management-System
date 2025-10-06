package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.model.User;
import com.example.TaskManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository ur;

    public User createUser(User u){
        if (ur.existsByEmail(u.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        ur.save(u);
        return u;
    }

    public User getUserById(Long id){
        return ur.findById(id).orElseThrow(() -> new RuntimeException("User not found"));


    }
}
