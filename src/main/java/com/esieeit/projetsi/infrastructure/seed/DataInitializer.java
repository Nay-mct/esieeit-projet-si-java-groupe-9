package com.esieeit.projetsi.infrastructure.seed;

import com.esieeit.projetsi.domain.enums.ProjectStatus;
import com.esieeit.projetsi.domain.enums.TaskPriority;
import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.model.Project;
import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.domain.model.User;
import com.esieeit.projetsi.infrastructure.repository.ProjectJpaRepository;
import com.esieeit.projetsi.infrastructure.repository.TaskJpaRepository;
import com.esieeit.projetsi.infrastructure.repository.UserJpaRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DataInitializer {

    private final UserJpaRepository userRepository;
    private final ProjectJpaRepository projectRepository;
    private final TaskJpaRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            UserJpaRepository userRepository,
            ProjectJpaRepository projectRepository,
            TaskJpaRepository taskRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initData() {
        return args -> {
            if (userRepository.count() > 0 || projectRepository.count() > 0 || taskRepository.count() > 0) {
                return;
            }

            String devPasswordHash = passwordEncoder.encode("DevPass123!");
            User admin = new User("admin@esiee.local", "admin", devPasswordHash, UserRole.ADMIN);
            User alice = new User("alice@esiee.local", "alice", devPasswordHash, UserRole.USER);
            User bob = new User("bob@esiee.local", "bob", devPasswordHash, UserRole.USER);
            userRepository.saveAll(List.of(admin, alice, bob));

            Project p1 = new Project(
                    "Projet SI - Gestion des taches",
                    "Plateforme de gestion de projets et taches",
                    ProjectStatus.ACTIVE,
                    admin);
            Project p2 = new Project(
                    "Projet SI - Support interne",
                    "Gestion des tickets de support",
                    ProjectStatus.ACTIVE,
                    alice);
            projectRepository.saveAll(List.of(p1, p2));

            Task t1 = new Task("Initialiser le repository Git", "Creer le depot et les conventions", p1);
            t1.setDueDate(LocalDate.now().plusDays(1));
            t1.start();
            t1.complete();

            Task t2 = new Task("Creer les entites JPA", "Mapper User, Project, Task", p1);
            t2.setDueDate(LocalDate.now().plusDays(3));
            t2.setPriority(TaskPriority.HIGH);
            t2.start();
            t2.assignTo(bob);

            Task t3 = new Task("Creer TaskRepository", "Ajouter les query methods metier", p1);
            t3.setDueDate(LocalDate.now().plusDays(2));

            Task t4 = new Task("Preparer donnees de demonstration", "Jeu de donnees pour soutenance", p2);
            t4.setDueDate(LocalDate.now().plusDays(5));
            t4.setPriority(TaskPriority.URGENT);
            t4.assignTo(alice);

            Task t5 = new Task("Configurer endpoints REST", "Ajouter les routes principales du backlog", p1);
            t5.setDueDate(LocalDate.now().plusDays(4));
            t5.setPriority(TaskPriority.HIGH);
            t5.start();
            t5.assignTo(alice);

            Task t6 = new Task("Implementer authentification JWT", "Creer login, register et generation du token", p1);
            t6.setDueDate(LocalDate.now().plusDays(2));
            t6.setPriority(TaskPriority.URGENT);
            t6.start();
            t6.assignTo(admin);

            Task t7 = new Task("Rediger tests unitaires", "Verifier validations User et workflow Task", p1);
            t7.setDueDate(LocalDate.now().plusDays(6));
            t7.setPriority(TaskPriority.MEDIUM);
            t7.assignTo(bob);

            Task t8 = new Task("Documenter l API", "Completer README et exemples d appels", p1);
            t8.setDueDate(LocalDate.now().plusDays(7));
            t8.setPriority(TaskPriority.LOW);

            Task t9 = new Task("Prioriser le backlog support", "Classer les demandes internes par urgence", p2);
            t9.setDueDate(LocalDate.now().plusDays(3));
            t9.setPriority(TaskPriority.HIGH);
            t9.start();
            t9.assignTo(alice);

            Task t10 = new Task("Preparer demo finale", "Verifier le parcours de soutenance et les comptes de test", p2);
            t10.setDueDate(LocalDate.now().plusDays(9));
            t10.setPriority(TaskPriority.URGENT);
            t10.assignTo(admin);

            taskRepository.saveAll(List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        };
    }
}
