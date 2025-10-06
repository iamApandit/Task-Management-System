package com.example.TaskManagementSystem.repository;

import com.example.TaskManagementSystem.model.Status;
import com.example.TaskManagementSystem.model.Task;
import com.example.TaskManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByAssignedUserAndStatus(User assignedUser, Status status);



    List<Task> findByCreatedByAndStatusAndDueDate(User createdBy, Status status, LocalDate dueDate);

    List<Task> findByCreatedByAndStatus(User createdBy, Status status);

    List<Task> findByCreatedByAndDueDate(User createdBy, LocalDate dueDate);

    List<Task> findByCreatedBy(User createdBy);



    List<Task> findByAssignedUserAndStatusAndDueDate(User assignedUser,Status status, LocalDate dueDate);

    List<Task> findByAssignedUserAndDueDate(User assignedUser, LocalDate dueDate);

    List<Task> findByAssignedUserAndStatus(User assignedUser, Status status);

    List<Task> findByAssignedUser(User assignedUser);




}
