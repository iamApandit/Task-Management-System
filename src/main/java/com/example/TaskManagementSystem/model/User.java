package com.example.TaskManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true )
    private String email;

    @OneToMany(mappedBy = "assignedUser")
    private List<Task> assignedTasks;

    @OneToMany(mappedBy = "createdBy")
    private List<Task> createdTasks;

}
