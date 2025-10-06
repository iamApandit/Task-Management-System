package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.model.Status;
import com.example.TaskManagementSystem.model.Task;
import com.example.TaskManagementSystem.model.User;
import com.example.TaskManagementSystem.repository.TaskRepository;
import com.example.TaskManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository tr;

    @Autowired
    private UserService us;

    public Task createTask(Task task, Long createdById) {
        User u = us.getUserById(createdById);
        task.setCreatedBy(u);
        task.setUpdatedBy(u);

        if (task.getAssignedUser() != null && task.getStatus() == Status.PENDING) {
            long pendingTasks = tr.countByAssignedUserAndStatus(task.getAssignedUser(), Status.PENDING);

            if (pendingTasks >= 10) {
                throw new RuntimeException("user can't be assigned to more than 10 tasks"+pendingTasks);
            }
        }

        return tr.save(task);
    }

    public Task updateTaskStatus(Long taskId,Long userId, Status newStatus) {

        Task t = tr.findById(taskId).orElseThrow(()->new RuntimeException("task not found"));
        User u = us.getUserById(userId);

        boolean isAssignedUser = t.getAssignedUser()!=null &&
                               t.getAssignedUser().getId().equals(userId);
        boolean isCreator = t.getCreatedBy().getId().equals(userId);

        if (!isAssignedUser && !isCreator) {
            throw new RuntimeException("only assigned user or the creator can change status");
        }

        if (newStatus == Status.PENDING && t.getAssignedUser() != null) {

            long pendingTasks = tr.countByAssignedUserAndStatus(t.getAssignedUser(), Status.PENDING);
            if (pendingTasks >= 10) {
                throw new RuntimeException("user can't be assigned to more than 10 tasks"+pendingTasks);
            }
        }
        t.setStatus(newStatus);
        t.setUpdatedBy(u);
        return tr.save(t);
    }

    public void deleteTask(Long taskId, Long userId) {
        Task t = tr.findById(taskId).orElseThrow(()->new RuntimeException("task not found"));

        boolean isCreator = t.getCreatedBy().getId().equals(userId);
        if (!isCreator) {
            throw new RuntimeException("only the creator can delete the task");
        }
        tr.delete(t);
    }

    public List<Task> createdTasksOfUser(Long userId, Status status, LocalDate dueDate) {
        User u = us.getUserById(userId);

        if (status != null && dueDate != null) {
            return tr.findByCreatedByAndStatusAndDueDate(u, status, dueDate);
        }else if (status != null) {
            return tr.findByCreatedByAndStatus(u, status);
        } else if (dueDate != null) {
            return tr.findByCreatedByAndDueDate(u, dueDate);
        }else  {
            return tr.findByCreatedBy(u);
        }
    }

    public List<Task> AssignedTasksOfUser(Long userId, Status status, LocalDate dueDate) {
        User u = us.getUserById(userId);
        if (status != null && dueDate != null) {
            return tr.findByAssignedUserAndStatusAndDueDate(u, status, dueDate);
        } else if (status != null) {
            return tr.findByAssignedUserAndStatus(u, status);
        } else if (dueDate != null) {
            return tr.findByAssignedUserAndDueDate(u, dueDate);
        }else   {
            return tr.findByAssignedUser(u);
        }
    }
}
