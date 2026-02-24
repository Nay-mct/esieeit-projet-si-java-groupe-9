package com.esieeit.projetsi.service;

import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.exception.BusinessException;
import com.esieeit.projetsi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setup() {
        taskService = new TaskService(new TaskRepository());
    }

    @Test
    void addTask_should_create_task_with_TODO_status() {
        var task = taskService.addTask("project-1", "Ma tâche", "desc");

        assertNotNull(task);
        assertEquals("project-1", task.getProjectId());
        assertEquals("Ma tâche", task.getTitle());
        assertEquals(Task.Status.TODO, task.getStatus());
    }

    @Test
    void markDone_should_set_status_DONE() {
        var task = taskService.addTask("project-1", "T1", null);

        var updated = taskService.markDone(task.getId());

        assertEquals(Task.Status.DONE, updated.getStatus());
    }

    @Test
    void reopen_should_set_status_TODO() {
        var task = taskService.addTask("project-1", "T1", null);
        taskService.markDone(task.getId());

        var updated = taskService.reopen(task.getId());

        assertEquals(Task.Status.TODO, updated.getStatus());
    }

    @Test
    void updateTask_should_update_title_and_description() {
        var task = taskService.addTask("project-1", "Old", "Old desc");

        var updated = taskService.updateTask(task.getId(), "New", "New desc");

        assertEquals("New", updated.getTitle());
        assertEquals("New desc", updated.getDescription());
    }

    @Test
    void deleteTask_should_remove_task() {
        var task = taskService.addTask("project-1", "T1", null);

        taskService.deleteTask(task.getId());

        assertEquals(0, taskService.listTasks("project-1").size());
    }

    @Test
    void markDone_should_fail_if_task_not_found() {
        assertThrows(BusinessException.class, () ->
                taskService.markDone("unknown-id")
        );
    }
    @Test
void filterTasksByStatus_should_return_only_matching_status() {
    taskService.addTask("project-1", "T1", null);
    var t2 = taskService.addTask("project-1", "T2", null);
    taskService.markDone(t2.getId());

    var todos = taskService.filterTasksByStatus("project-1", Task.Status.TODO);
    var dones = taskService.filterTasksByStatus("project-1", Task.Status.DONE);

    assertEquals(1, todos.size());
    assertEquals(Task.Status.TODO, todos.get(0).getStatus());

    assertEquals(1, dones.size());
    assertEquals(Task.Status.DONE, dones.get(0).getStatus());
}
@Test
void searchTasks_should_return_only_matching_tasks() {
    taskService.addTask("project-1", "Faire les courses", "acheter du lait");
    taskService.addTask("project-1", "Réviser Java", "JUnit et services");
    taskService.addTask("project-2", "Autre projet", "ne doit pas apparaître");

    var result1 = taskService.searchTasks("project-1", "java");
    assertEquals(1, result1.size());
    assertTrue(result1.get(0).getTitle().toLowerCase().contains("java"));

    var result2 = taskService.searchTasks("project-1", "lait");
    assertEquals(1, result2.size());
    assertTrue(result2.get(0).getDescription().toLowerCase().contains("lait"));
}
}