# Package Structure - Clean Architecture simplifiée

## 1) Objectif

Définir une structure de packages lisible, testable et évolutive pour éviter un code monolithique non structuré.

## 2) Package racine

- `com.esieeit.projetsi`

## 3) Structure proposée

```text
com.esieeit.projetsi
├── domain
│   ├── model
│   ├── enums
│   ├── valueobject
│   └── exception
├── application
│   ├── service
│   ├── usecase
│   └── port
│       ├── in
│       └── out
├── api
│   ├── controller
│   └── dto
│       ├── request
│       └── response
└── infrastructure
    ├── persistence
    │   ├── entity
    │   ├── repository
    │   └── mapper
    └── config
```

## 4) Rôle de chaque couche

### domain

- porte les concepts métier purs (User, Project, Task, Comment)
- contient les règles métier et invariants
- ne dépend d’aucune couche applicative ou framework

### application

- orchestre les cas d’usage
- utilise les objets du domaine
- déclare les ports (interfaces) nécessaires vers l’extérieur

### api

- expose les endpoints HTTP
- convertit Request/Response DTO <-> modèle applicatif
- ne contient pas la logique métier centrale

### infrastructure

- implémente les ports sortants
- gère persistance, mapping technique, configuration framework

## 5) Règles de dépendances

Règles obligatoires :

- `api` peut dépendre de `application`
- `application` peut dépendre de `domain`
- `infrastructure` peut dépendre de `application` et `domain`
- `domain` ne dépend de personne

Règles interdites :

- `api` -> `infrastructure` direct
- `domain` -> Spring/JPA/HTTP
- `controller` -> `repository` direct

## 6) Conventions de nommage

- Entités : `User`, `Project`, `Task`, `Comment`
- Enums : `TaskStatus`, `TaskPriority`, `UserRole`
- Services de cas d’usage : `CreateProjectService`, `ChangeTaskStatusService`
- Ports : `ProjectRepositoryPort`, `TaskQueryPort`

## 7) Exemple de flux d’appel

`ProjectController` -> `CreateProjectService` -> `ProjectRepositoryPort` -> implémentation `JpaProjectRepositoryAdapter`

## 8) Checklist architecture

- [x] responsabilités séparées par couche
- [x] dépendances orientées vers le domaine
- [x] aucun accès controller -> repository direct
- [x] packages alignés avec le backlog fonctionnel
