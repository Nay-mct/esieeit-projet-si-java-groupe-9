# Projet SI Java - ESIEE IT

Backend Java/Spring Boot pour la gestion de projets et de taches.

## Stack

- Java 21
- Spring Boot 3.3
- Gradle
- Spring Data JPA / Hibernate
- MySQL 8.4 (Docker Compose)

## TP 4.1 - BDD et JPA/Hibernate

### Choix techniques

- SGBD : MySQL 8.4
- Port local DB : `3307`
- Interface DB : phpMyAdmin sur `8081`
- `spring.jpa.hibernate.ddl-auto=update`
- Logs SQL actifs (`org.hibernate.SQL=debug`)

### Variables d'environnement utiles

Par defaut, la config est dans `application.yml`.
Un profil `dev` est aussi disponible dans `application-dev.yml` avec :

- `DB_HOST` (default `localhost`)
- `DB_PORT` (default `3307`)
- `DB_NAME` (default `project_si_db`)
- `DB_USER` (default `project_user`)
- `DB_PASSWORD` (default `project_pass`)

### Lancer la base de donnees

```bash
docker compose up -d
docker compose ps
docker compose logs -f db
```

Arret :

```bash
docker compose down
```

Reset complet :

```bash
docker compose down -v
```

### Lancer l'application

```bash
./gradlew bootRun
```

Avec profil `dev` :

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Verifications realisees

- Entites JPA mappees : `users`, `projects`, `tasks`, `comments`
- Enums metier : `UserRole`, `ProjectStatus`, `TaskStatus`, `TaskPriority`
- Relations JPA :
  - `User 1-N Project` (owner)
  - `Project 1-N Task`
  - `Task 1-N Comment`
  - `Task N-1 User` (assignee)
  - `Comment N-1 User` (author)
- Contraintes `UNIQUE` sur `users.email` et `users.username`
- Script SQL de controle : `sql/check_schema.sql`

### Problemes rencontres / solutions

- Erreur de rapport stale (`build/reports/problems/problems-report.html`) :
  - cause : ancien rapport d'un ancien module `app`
  - solution : relancer `./gradlew clean build` sur l'etat courant.
- Erreurs de connexion JDBC possibles :
  - verifier `docker compose ps`
  - verifier les credentials et le port `3307`
  - verifier les logs avec `docker compose logs -f db`

## Commandes de verification locale

```bash
./gradlew clean test
```

## Documentation projet

- `docs/DOMAIN_MODEL.md`
- `docs/PACKAGE_STRUCTURE.md`
- `docs/DECISIONS.md`
- `docs/DOMAIN_RULES.md`
- `docs/API_TASKS.md`
- `docs/API_ERRORS.md`
