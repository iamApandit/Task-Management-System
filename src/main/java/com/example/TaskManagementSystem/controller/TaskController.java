package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.model.Status;
import com.example.TaskManagementSystem.model.Task;
import com.example.TaskManagementSystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService ts;


    @PostMapping("/created-by/{createdById}")
    public ResponseEntity<?> createTask(@RequestBody Task task, @PathVariable Long createdById){
        try {
            Task createdTask = ts.createTask(task, createdById);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long taskId, @RequestParam Status newStatus,@RequestParam Long userId){
        try {
            Task updatedTask = ts.updateTaskStatus(taskId,userId,newStatus);
            return ResponseEntity.ok(updatedTask);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, @RequestParam Long userId){
        try {
            ts.deleteTask(taskId,userId);
            return ResponseEntity.ok("task has been deleted");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/created-by/{userId}")
    public ResponseEntity<?> createdTasksOfUser(@PathVariable Long userId, @RequestParam(required = false) Status status, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate){
        try {
            List<Task> t = ts.createdTasksOfUser(userId,status,dueDate);
            return ResponseEntity.ok(t);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/assigned-to/{userId}")
    public ResponseEntity<?> AssignedTasksOfUser(@PathVariable Long userId, @RequestParam(required = false) Status status, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate ){
        try {
            List<Task> t = ts.AssignedTasksOfUser(userId,status,dueDate);
            return ResponseEntity.ok(t);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
