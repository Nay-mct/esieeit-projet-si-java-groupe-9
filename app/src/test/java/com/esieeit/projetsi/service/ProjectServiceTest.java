package com.esieeit.projetsi.service;
import com.esieeit.projetsi.exception.BusinessException;
import com.esieeit.projetsi.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {

    private ProjectService projectService;

    @BeforeEach
    void setup() {
        projectService = new ProjectService(new ProjectRepository());
    }

    @Test
    void createProject_should_create_project() {
        String ownerId = "user-1";

        var project = projectService.createProject(ownerId, "Mon projet");

        assertNotNull(project);
        assertEquals(ownerId, project.getOwnerUserId());
        assertEquals("Mon projet", project.getName());
        assertNotNull(project.getId());
    }

    @Test
    void listProjects_should_return_only_owner_projects() {
        var p1 = projectService.createProject("user-1", "P1");
        projectService.createProject("user-2", "P2");

        var projectsUser1 = projectService.listProjects("user-1");

        assertEquals(1, projectsUser1.size());
        assertEquals(p1.getId(), projectsUser1.get(0).getId());
    }

    @Test
    void renameProject_should_fail_if_not_owner() {
        var project = projectService.createProject("user-1", "P1");

        assertThrows(BusinessException.class, () ->
                projectService.renameProject("user-2", project.getId(), "Nouveau nom")
        );
    }

    @Test
    void deleteProject_should_remove_project() {
        var project = projectService.createProject("user-1", "P1");

        projectService.deleteProject("user-1", project.getId());

        assertEquals(0, projectService.listProjects("user-1").size());
    }
}