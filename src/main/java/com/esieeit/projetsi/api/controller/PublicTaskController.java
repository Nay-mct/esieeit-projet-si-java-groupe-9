package com.esieeit.projetsi.api.controller;

import com.esieeit.projetsi.api.dto.TaskResponse;
import com.esieeit.projetsi.api.mapper.TaskMapper;
import com.esieeit.projetsi.application.service.TaskService;
import com.esieeit.projetsi.domain.model.Task;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/tasks")
public class PublicTaskController {

    private final TaskService taskService;

    public PublicTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String keyword) {
        return taskService.getAll(status, projectId, keyword).stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return TaskMapper.toResponse(task);
    }
}
