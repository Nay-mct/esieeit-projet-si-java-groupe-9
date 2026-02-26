package com.esieeit.projetsi.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import org.junit.jupiter.api.Test;

class TaskWorkflowTest {

    @Test
    void complete_shouldFail_whenStatusIsTodo() {
        User owner = new User("owner@example.com", "owner", "temporary-hash", UserRole.USER);
        Project project = new Project("Projet", "Description", owner);
        Task task = new Task("Tache", null, project);

        assertThrows(BusinessRuleException.class, task::complete);
    }

    @Test
    void workflow_shouldPass_forValidTransitions() {
        User owner = new User("owner2@example.com", "owner2", "temporary-hash", UserRole.USER);
        Project project = new Project("Projet 2", "Description", owner);
        Task task = new Task("Tache 2", null, project);

        task.start();
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());

        task.complete();
        assertEquals(TaskStatus.DONE, task.getStatus());

        task.archive();
        assertEquals(TaskStatus.ARCHIVED, task.getStatus());
    }
}
