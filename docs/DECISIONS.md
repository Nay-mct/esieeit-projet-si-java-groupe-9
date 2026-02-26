# DECISIONS - Domain Modeling (mini ADR)

## D-01 - Nom du package racine

- Décision : utiliser `com.esieeit.projetsi`.
- Justification : cohérence avec conventions Java et TP 1.1.

## D-02 - Nombre d’entités MVP

- Décision : démarrer avec `User`, `Project`, `Task`, `Comment`.
- Justification : couvre les user stories Must/Should sans sur-modélisation.

## D-03 - Rôles utilisateur

- Décision : `UserRole` en enum (`USER`, `ADMIN`) au lieu d’une entité `Role`.
- Justification : suffisant pour L2 et réduit la complexité initiale.

## D-04 - Statut et priorité des tâches

- Décision : utiliser `TaskStatus` et `TaskPriority` en enums.
- Justification : règles métier explicites et validation plus simple.

## D-05 - Type d’identifiant

- Décision : utiliser `Long` pour tous les identifiants métier.
- Justification : cohérence avec une intégration JPA future.

## D-06 - Type des dates

- Décision : `Instant` pour les timestamps (`createdAt`, `updatedAt`) et `LocalDate` pour `dueDate`.
- Justification : séparation claire entre date/heure système et date métier.

## D-07 - Relation Task <-> User

- Décision : `assignee` est optionnel côté `Task`.
- Justification : permet de créer une tâche non assignée puis d’affecter plus tard.

## D-08 - Complexité relationnelle

- Décision : éviter les relations Many-to-Many en MVP.
- Justification : simplifie mapping, requêtes et compréhension architecture.

## D-09 - Clean architecture simplifiée

- Décision : séparation `domain`, `application`, `api`, `infrastructure`.
- Justification : favorise testabilité et maintenabilité.

## D-10 - Règles de statut Task

- Décision : interdire les retours depuis `ARCHIVED` et interdire `DONE -> TODO`.
- Justification : workflow métier plus cohérent et traçable.
