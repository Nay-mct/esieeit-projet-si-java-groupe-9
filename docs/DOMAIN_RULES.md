# Domain Rules - TP 2.2

## 1) Validations techniques par entité

## User

- `email` obligatoire, non vide, format email valide, longueur 5..254
- `username` obligatoire, non vide, longueur 3..30
- `roles` obligatoire, au moins un rôle
- `passwordHash` optionnel à ce stade, mais si renseigné : longueur 10..255

## Project

- `name` obligatoire, non vide, longueur 1..80
- `description` optionnelle, si présente : longueur 1..500
- `owner` obligatoire

## Task

- `title` obligatoire, non vide, longueur 1..120
- `description` optionnelle, si présente : longueur 1..1000
- `project` obligatoire
- `priority` obligatoire
- `dueDate` optionnelle, mais interdite dans le passé

## Comment

- `content` obligatoire, non vide, longueur 1..500
- `task` obligatoire
- `author` obligatoire

## 2) Règles métier (invariants)

- Un `Project` a exactement un owner.
- Une `Task` appartient à un seul `Project`.
- Un `Comment` appartient à une `Task` et a un auteur.
- Un `User` doit posséder au moins un rôle (`USER` ou `ADMIN`).

## 3) Workflow `TaskStatus`

États : `TODO`, `IN_PROGRESS`, `DONE`, `ARCHIVED`

Transitions autorisées :

- `TODO -> IN_PROGRESS` via `start()`
- `IN_PROGRESS -> DONE` via `complete()`
- `IN_PROGRESS -> TODO` via `moveBackToTodo()`
- `TODO -> ARCHIVED` via `archive()`
- `DONE -> ARCHIVED` via `archive()`

Transitions interdites :

- `TODO -> DONE` direct
- `DONE -> TODO`
- toute transition depuis `ARCHIVED`

## 4) Règles d’autorisation (aperçu)

- Écriture réservée aux utilisateurs authentifiés.
- Modification/suppression d’un projet réservée à son owner (ou admin).
- Consultation globale des utilisateurs réservée à `ADMIN`.

## 5) Décisions d’implémentation

- Exceptions métier dédiées : `DomainException`, `ValidationException`, `BusinessRuleException`.
- Validations techniques mutualisées dans `Validators`.
- Aucune dépendance Spring/JPA/Lombok dans la couche domaine.