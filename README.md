# Projet SI Java - ESIEE-IT (2025-2026)

## Contexte

Projet SI en Java : construire une API backend propre, structurée, documentée et testée, avec un workflow Git proche entreprise.

## Objectifs

- Mettre en place un dépôt Git propre (`main`/`develop`/`feature/*`)
- Implémenter un MVP (auth + gestion de ressources métier)
- Respecter une architecture claire (controller/service/repository)
- Ajouter des tests unitaires
- Produire une documentation exploitable (`README.md` + `BACKLOG.md`)

## Équipe

- Nom Prénom - rôle (PO / Lead Dev / Dev / QA)
- Nom Prénom - rôle
- Nom Prénom - rôle

## Stack

- Java 21
- Gradle (wrapper)
- JUnit 5
- (à venir) Spring Boot, DB, Docker

## Installation

### Prérequis

- Java 17/21
- Git

### Cloner

```bash
git clone <URL>
cd <repo>
```

## Lancer

### Tests

```bash
./gradlew test
```

### Run API Spring Boot

```bash
./gradlew bootRun
```

## Workflow Git

- `main` : stable
- `develop` : intégration
- `feature/*` : 1 user story = 1 branche
- PR obligatoire vers `develop`

## Convention de commits

- `chore(init): bootstrap gradle wrapper and project structure`
- `docs(readme): add setup and workflow instructions`
- `test(app): add initial sanity test`

Format recommandé : `<type>(<scope>): <message>`

Types courants : `feat`, `fix`, `chore`, `docs`, `refactor`, `test`.

## Backlog

Voir `BACKLOG.md`.

## Documentation de modélisation (TP 2.1)

- Modèle métier : `docs/DOMAIN_MODEL.md`
- Structure des packages : `docs/PACKAGE_STRUCTURE.md`
- Décisions d’architecture : `docs/DECISIONS.md`
- Règles métier et validations (TP 2.2) : `docs/DOMAIN_RULES.md`
- API REST Task CRUD (TP 3.1) : `docs/API_TASKS.md`
- Validation et gestion d’erreurs (TP 3.2) : `docs/API_ERRORS.md`

## API Tasks (TP 3.1)

Endpoints disponibles :

- `GET /api/tasks`
- `GET /api/tasks/{id}`
- `POST /api/tasks`
- `PUT /api/tasks/{id}`
- `DELETE /api/tasks/{id}`

Exemple rapide :

```bash
curl -i -X POST http://localhost:8080/api/tasks \
   -H "Content-Type: application/json" \
   -d '{"title":"Initialiser le repo","description":"Créer Gradle + README"}'
```

## Validation & erreurs (TP 3.2)

- validation technique des DTOs avec Bean Validation (`@NotBlank`, `@Size`, `@Pattern`)
- activation de validation via `@Valid` sur `POST`/`PUT`
- gestion globale des erreurs via `@RestControllerAdvice`
- format de réponse d’erreur JSON uniforme (`timestamp`, `status`, `error`, `message`, `path`, `details`)

## Explication du code (fichier par fichier)

### Vue d’ensemble du flux (requête -> réponse)

1. Le client envoie une requête HTTP sur `/api/tasks`.
2. `TaskController` reçoit le JSON et valide le DTO (`@Valid`).
3. `TaskService` applique la logique métier et les règles de workflow.
4. `TaskRepository` (implémenté en mémoire) persiste/récupère la donnée.
5. `TaskMapper` convertit l’entité `Task` en `TaskResponse`.
6. En cas d’erreur, `GlobalExceptionHandler` construit une réponse JSON standardisée.

### Démarrage et build

- `build.gradle`
   - Rôle : configuration du build Gradle.
   - Contenu clé : plugins Java/Spring Boot, dépendances web/validation/tests.
   - Impact : détermine ce qui compile, ce qui s’exécute et ce qui est testé.
- `settings.gradle`
   - Rôle : déclare le nom du projet Gradle.
- `src/main/java/com/esieeit/projetsi/ProjectSiApplication.java`
   - Rôle : point d’entrée de l’API Spring Boot (`main`).
   - Usage : démarrage via `./gradlew bootRun`.
- `src/main/java/com/esieeit/projetsi/App.java`
   - Rôle : ancien point d’entrée console du TP initial.
   - Note : n’est pas utilisé par Spring Boot mais conservé pour historique pédagogique.

### API REST (couche HTTP)

- `src/main/java/com/esieeit/projetsi/api/controller/TaskController.java`
   - Rôle : expose les endpoints CRUD REST.
   - Endpoints :
      - `GET /api/tasks`
      - `GET /api/tasks/{id}`
      - `POST /api/tasks`
      - `PUT /api/tasks/{id}`
      - `DELETE /api/tasks/{id}`
   - Responsabilité : orchestration HTTP uniquement (pas de logique métier complexe).
   - Entrées : DTOs JSON (`TaskCreateRequest`, `TaskUpdateRequest`).
   - Sorties : `TaskResponse` + codes HTTP cohérents.

- `src/main/java/com/esieeit/projetsi/api/dto/TaskCreateRequest.java`
   - Rôle : contrat d’entrée pour la création.
   - Champs : `title`, `description`.
   - Validations techniques : `@NotBlank`, `@Size`.

- `src/main/java/com/esieeit/projetsi/api/dto/TaskUpdateRequest.java`
   - Rôle : contrat d’entrée pour la mise à jour partielle.
   - Champs : `title`, `description`, `status`.
   - Validations techniques : `@Size`, `@Pattern` pour le statut.

- `src/main/java/com/esieeit/projetsi/api/dto/TaskResponse.java`
   - Rôle : format de sortie stable envoyé au front.
   - Champs : `id`, `title`, `description`, `status`.
   - Intérêt : évite d’exposer directement les entités domaine.

- `src/main/java/com/esieeit/projetsi/api/mapper/TaskMapper.java`
   - Rôle : mapping explicite entre modèle domaine et DTO de réponse.
   - Méthode clé : `toResponse(Task)`.
   - Intérêt : centralise la transformation et simplifie le controller.

### Gestion d’erreurs API

- `src/main/java/com/esieeit/projetsi/api/error/FieldErrorDetail.java`
   - Rôle : représente une erreur de validation sur un champ précis.
   - Champs : `field`, `message`.

- `src/main/java/com/esieeit/projetsi/api/error/ErrorResponse.java`
   - Rôle : enveloppe JSON uniforme pour toutes les erreurs.
   - Champs : `timestamp`, `status`, `error`, `message`, `path`, `details`.

- `src/main/java/com/esieeit/projetsi/api/error/GlobalExceptionHandler.java`
   - Rôle : centralise le traitement des exceptions (`@RestControllerAdvice`).
   - Mapping principal :
      - validation DTO -> `400 VALIDATION_ERROR`
      - données invalides -> `400 INVALID_DATA`
      - ressource absente -> `404 NOT_FOUND`
      - règle métier violée -> `409 BUSINESS_RULE_VIOLATION`
      - erreur inattendue -> `500 INTERNAL_ERROR`
   - Intérêt : réponses homogènes et exploitables côté front.

### Application (logique d’orchestration)

- `src/main/java/com/esieeit/projetsi/application/service/TaskService.java`
   - Rôle : cœur applicatif des cas d’usage Task.
   - Opérations : `create`, `getAll`, `getById`, `update`, `delete`.
   - Règles métier : transitions de statut autorisées/interdites.
   - Exceptions métier : `ResourceNotFoundException`, `InvalidDataException`, `BusinessRuleException`.
   - Note design : utilise un `defaultProject` pour satisfaire les contraintes du domaine en mode in-memory (TP 3.x).

- `src/main/java/com/esieeit/projetsi/application/port/TaskRepository.java`
   - Rôle : interface (port) de persistance des tâches.
   - Méthodes : `save`, `findById`, `findAll`, `deleteById`, `existsById`.
   - Intérêt : découple le service de la technologie de stockage.

### Infrastructure (persistance temporaire)

- `src/main/java/com/esieeit/projetsi/infrastructure/repository/InMemoryTaskRepository.java`
   - Rôle : implémentation en mémoire de `TaskRepository`.
   - Stockage : `Map<Long, Task>`.
   - Génération ID : `AtomicLong` auto-incrémenté.
   - Limite : non persistant (données perdues au redémarrage).
   - But : préparer la transition vers base de données au TP suivant.

### Domaine métier

- `src/main/java/com/esieeit/projetsi/domain/model/User.java`
   - Rôle : entité utilisateur.
   - Invariants : email valide, username valide, au moins un rôle.

- `src/main/java/com/esieeit/projetsi/domain/model/Project.java`
   - Rôle : entité projet possédée par un owner.
   - Invariants : nom obligatoire, owner non null.
   - Méthode métier clé : `addTask(Task)` avec contrôle d’appartenance.

- `src/main/java/com/esieeit/projetsi/domain/model/Task.java`
   - Rôle : entité centrale du CRUD API.
   - Invariants : titre valide, projet non null.
   - Workflow : `TODO -> IN_PROGRESS -> DONE -> ARCHIVED` (+ règles additionnelles).
   - Méthodes métier : `start`, `complete`, `moveBackToTodo`, `archive`.

- `src/main/java/com/esieeit/projetsi/domain/model/Comment.java`
   - Rôle : commentaire lié à une tâche et un auteur.
   - Invariants : contenu non vide, task et author obligatoires.

- `src/main/java/com/esieeit/projetsi/domain/enums/TaskStatus.java`
   - Rôle : états de cycle de vie d’une tâche.

- `src/main/java/com/esieeit/projetsi/domain/enums/TaskPriority.java`
   - Rôle : niveau de priorité métier.

- `src/main/java/com/esieeit/projetsi/domain/enums/UserRole.java`
   - Rôle : rôles d’accès applicatif (`USER`, `ADMIN`).

- `src/main/java/com/esieeit/projetsi/domain/validation/Validators.java`
   - Rôle : utilitaires partagés de validation (null, blank, taille, email, positif).
   - Intérêt : éviter la duplication des validations dans toutes les entités/services.

- `src/main/java/com/esieeit/projetsi/domain/exception/DomainException.java`
   - Rôle : classe racine des exceptions domaine.

- `src/main/java/com/esieeit/projetsi/domain/exception/ValidationException.java`
   - Rôle : violation de validation technique/métier de base.

- `src/main/java/com/esieeit/projetsi/domain/exception/BusinessRuleException.java`
   - Rôle : violation de règle métier (workflow, transitions interdites).

- `src/main/java/com/esieeit/projetsi/domain/exception/InvalidDataException.java`
   - Rôle : données requête cohérentes JSON mais invalides pour l’application.

- `src/main/java/com/esieeit/projetsi/domain/exception/ResourceNotFoundException.java`
   - Rôle : ressource absente demandée par un id.

- `src/main/java/com/esieeit/projetsi/domain/Demo.java`
   - Rôle : scénario de démonstration locale du domaine sans HTTP.

### Tests

- `src/test/java/com/esieeit/projetsi/AppTest.java`
   - But : vérifier la base de projet et la chaîne de test.

- `src/test/java/com/esieeit/projetsi/domain/model/TaskWorkflowTest.java`
   - But : valider transitions autorisées/interdites du workflow `TaskStatus`.

- `src/test/java/com/esieeit/projetsi/domain/model/UserValidationTest.java`
   - But : valider les contraintes de création utilisateur (email/roles).

### Documentation

- `BACKLOG.md`
   - Contenu : user stories, priorité (Must/Should/Nice), estimation, critères.

- `docs/DOMAIN_MODEL.md`
   - Contenu : acteurs, cas d’usage, entités, cardinalités, diagramme Mermaid.

- `docs/PACKAGE_STRUCTURE.md`
   - Contenu : règles de dépendances entre couches.

- `docs/DECISIONS.md`
   - Contenu : décisions techniques/architecture (mini ADR).

- `docs/DOMAIN_RULES.md`
   - Contenu : invariants, validations, transitions et règles métier.

- `docs/API_TASKS.md`
   - Contenu : endpoints CRUD, exemples de requêtes/réponses, codes HTTP.

- `docs/API_ERRORS.md`
   - Contenu : catalogue des erreurs, format standard `ErrorResponse`, exemples JSON.

## Structure actuelle

```text
.
├─ build.gradle
├─ settings.gradle
├─ gradlew
├─ gradlew.bat
├─ gradle/wrapper/
└─ src/
   ├─ main/java/com/esieeit/projetsi/App.java
   └─ test/java/com/esieeit/projetsi/AppTest.java
```
