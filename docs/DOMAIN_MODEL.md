# Modèle du Domaine - Gestion de Projets

## 1. Acteurs
* **User** : Utilisateur authentifié (crée des projets, des tâches, commente).
* **Admin** : Gère les utilisateurs et la modération.
* **System** : (Optionnel) Notifications automatiques.

## 2. Cas d'usage (Top 5 MVP)
1.  **UC-01** : En tant que User, je veux créer un projet pour organiser mes tâches.
2.  **UC-02** : En tant que User, je veux ajouter une tâche à un projet.
3.  **UC-03** : En tant que User, je veux changer le statut d'une tâche (ex: TODO -> DONE).
4.  **UC-04** : En tant que User, je veux commenter une tâche pour donner des précisions.
5.  **UC-05** : En tant que Admin, je veux désactiver un utilisateur.

## 3. Entités & Attributs
Les types choisis respectent les conventions Java et JPA futures.

### User
* `id` : Long (PK)
* `email` : String (Unique)
* `username` : String (Unique)
* `passwordHash` : String
* `roles` : Set<Role> (Enum)
* `createdAt` : Instant

### Project
* `id` : Long (PK)
* `name` : String
* `description` : String
* `ownerId` : Long (FK vers User)
* `createdAt` : Instant
* `updatedAt` : Instant

### Task
* `id` : Long (PK)
* `title` : String
* `description` : String
* `status` : TaskStatus (Enum)
* `priority` : TaskPriority (Enum - Optionnel)
* `projectId` : Long (FK vers Project)
* `assigneeId` : Long (FK vers User - Optionnel)
* `dueDate` : LocalDate (Optionnel)
* `createdAt` : Instant
* `updatedAt` : Instant

### Comment
* `id` : Long (PK)
* `content` : String
* `taskId` : Long (FK vers Task)
* `authorId` : Long (FK vers User)
* `createdAt` : Instant

## 4. Enums
* **Role** : `USER`, `ADMIN`
* **TaskStatus** : `TODO`, `IN_PROGRESS`, `DONE`, `ARCHIVED`
* **TaskPriority** : `LOW`, `MEDIUM`, `HIGH`

## 5. Règles Métier & Invariants

### Invariants (Toujours vrai)
* **User** : L'email doit être unique et valide.
* **Project** : Un projet a obligatoirement un `owner` (créateur).
* **Task** : Une tâche ne peut exister sans projet parent.
* **Task** : La date d'échéance (`dueDate`) ne peut pas être dans le passé à la création.

### Workflow (TaskStatus)
* `TODO` -> `IN_PROGRESS`
* `IN_PROGRESS` -> `DONE`
* `DONE` -> `ARCHIVED`
* *Interdit* : Passer de `DONE` à `TODO` directement.

## 6. Diagramme de Classes
```mermaid
classDiagram
    class User {
        +Long id
        +String email
        +String username
        +String passwordHash
        +Set~Role~ roles
    }

    class Project {
        +Long id
        +String name
        +String description
        +Long ownerId
        +Instant createdAt
    }

    class Task {
        +Long id
        +String title
        +String description
        +TaskStatus status
        +TaskPriority priority
        +LocalDate dueDate
    }

    class Comment {
        +Long id
        +String content
        +Instant createdAt
    }

    class TaskStatus {
        <<enumeration>>
        TODO
        IN_PROGRESS
        DONE
        ARCHIVED
    }

    User "1" --> "*" Project : creates/owns
    Project "1" *-- "*" Task : contains
    Task "1" *-- "*" Comment : has
    User "1" --> "*" Comment : writes
    User "1" --> "*" Task : assigned to
    Task ..> TaskStatus