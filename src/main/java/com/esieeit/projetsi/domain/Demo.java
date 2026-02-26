package com.esieeit.projetsi.domain;

import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.model.Project;
import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.domain.model.User;
import java.util.Set;

/**
 * Minimal CLI demo for the domain model lifecycle.
 */
public class Demo {

    public static void main(String[] args) {
        User owner = new User("alice@example.com", "alice", Set.of(UserRole.USER));
        Project project = new Project("Projet SI", "Démo domaine", owner);
        Task task = new Task("Initialiser le domaine", "Créer entités + validations", project);
        project.addTask(task);

        System.out.println(task);
        task.start();
        System.out.println(task);
        task.complete();
        System.out.println(task);
        task.archive();
        System.out.println(task);

        Task illegal = new Task("Transition invalide", null, project);
        try {
            illegal.complete();
        } catch (Exception ex) {
            System.out.println("Erreur attendue: " + ex.getMessage());
        }
    }
}