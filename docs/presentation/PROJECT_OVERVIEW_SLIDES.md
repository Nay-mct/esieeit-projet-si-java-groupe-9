---
title: "Projet SI Java"
subtitle: "Presentation du backend, de l'architecture a la securite JWT"
author: "Equipe Groupe 9"
---

# Projet SI Java

## Vue d'ensemble du backend

- API REST Spring Boot
- Gestion des utilisateurs, projets et taches
- Authentification JWT et gestion des roles
- Persistance MySQL avec JPA / Hibernate

### Objectif de la presentation

Comprendre comment le projet fonctionne de A a Z, du demarrage de l'application jusqu'au traitement d'une requete protegee.

---

# 1. Problematique

## Ce que fait le projet

- creer et gerer des taches
- organiser les taches dans des projets
- authentifier les utilisateurs
- appliquer des droits differents selon le role

## Resultat attendu

- une API claire
- une logique metier separee de la technique
- une base MySQL persistante
- une securite stateless avec JWT

---

# 2. Stack technique

## Technologies principales

- Java 21
- Spring Boot 3.3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT avec `jjwt`
- MySQL 8
- Gradle
- Docker pour la base locale

## Fichiers clefs

- `build.gradle`
- `src/main/resources/application.yml`
- `docker-compose.yml`

---

# 3. Architecture du projet

## Organisation en 4 couches

- `api`
  - controllers, DTO, mapping, erreurs HTTP
- `application`
  - services metier et ports
- `domain`
  - entites et regles metier
- `infrastructure`
  - JPA, securite, seed, configuration

## Idee centrale

Le controleur ne parle pas directement a la base. Il passe par un service, puis par un port, puis par un adaptateur technique.

---

# 4. Modele metier

## Entites principales

- `User`
- `Project`
- `Task`
- `Comment`

## Enums

- `UserRole` : `USER`, `ADMIN`
- `TaskStatus` : `TODO`, `IN_PROGRESS`, `DONE`, `ARCHIVED`
- `TaskPriority`
- `ProjectStatus`

## Relations

- un `User` possede des `Project`
- un `Project` contient des `Task`
- une `Task` peut etre assignee a un `User`
- une `Task` peut recevoir des `Comment`

---

# 5. Regles metier importantes

## Exemples dans le domaine

- une tache est creee en `TODO`
- `TODO -> IN_PROGRESS` est autorise
- `IN_PROGRESS -> DONE` est autorise
- `ARCHIVED` est un etat final
- la date d'echeance ne peut pas etre dans le passe

## Pourquoi c'est important

Ces regles sont codees dans les entites metier. On evite ainsi de disperser les regles entre plusieurs couches.

---

# 6. Flux d'une requete Task

## Exemple : `GET /api/tasks`

1. le client appelle l'endpoint HTTP
2. Spring Security verifie l'acces
3. le `TaskController` recoit la requete
4. le `TaskService` applique la logique metier
5. le port `TaskRepository` abstrait l'acces aux donnees
6. `JpaTaskRepositoryAdapter` delegue a JPA
7. Hibernate lit MySQL
8. la reponse est convertie en DTO JSON

## Benefice

Chaque couche a une responsabilite precise.

---

# 7. Authentification

## Endpoints

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`

## Cycle de login

1. l'utilisateur envoie login + mot de passe
2. `AuthenticationManager` verifie les identifiants
3. `CustomUserDetailsService` recharge l'utilisateur
4. `PasswordEncoder` compare le mot de passe chiffre
5. `JwtService` genere un token signe
6. l'API renvoie le token au client

---

# 8. JWT et securite

## Ce que contient le token

- `sub` = username
- `email`
- `role`
- date d'emission
- date d'expiration

## Requete protegee

1. le client envoie `Authorization: Bearer <token>`
2. `JwtAuthenticationFilter` extrait et valide le token
3. l'utilisateur est place dans le `SecurityContext`
4. `@PreAuthorize` controle le role

## Exemples de droits

- `USER` et `ADMIN` peuvent lire / creer / modifier les taches
- seul `ADMIN` peut supprimer une tache

---

# 9. Persistance et base de donnees

## Comment les donnees sont stockees

- les entites `User`, `Project`, `Task`, `Comment` sont mappees en tables SQL
- JPA / Hibernate gere le mapping objet-relationnel
- la base utilisee localement est MySQL

## Au demarrage

- Spring ouvre la connexion
- Hibernate met a jour le schema avec `ddl-auto: update`
- en profil `dev`, un jeu de donnees est injecte si la base est vide

## Donnees de demo

- 1 admin
- 2 users
- 2 projets
- 4 taches

---

# 10. Validation et erreurs

## Validation d'entree

- DTO valides avec `@Valid`
- contraintes sur `title`, `description`, `status`, etc.

## Gestion centralisee des erreurs

- `400` : requete invalide
- `401` : non authentifie
- `403` : acces interdit
- `404` : ressource introuvable
- `409` : violation de regle metier
- `500` : erreur inattendue

## Benefice

Le client recoit des erreurs JSON coherentes et previsibles.

---

# 11. Ce qui est deja solide

## Points forts du projet

- architecture claire et lisible
- domaine metier non trivial
- securite JWT fonctionnelle
- gestion des roles `USER` / `ADMIN`
- seed de dev pratique
- tests metier presents

## Exemples de tests deja valides

- login OK / KO
- acces public / prive
- `401`, `403`, `404`
- suppression reservee a `ADMIN`

---

# 12. Point d'attention

## Limite actuelle a connaitre

La creation de tache utilise encore un `defaultProject` dans le service des taches.

## Interpretation

- c'est acceptable pour un TP et pour valider le pipeline technique
- pour une version plus aboutie, la creation de tache devrait cibler explicitement un vrai projet metier

---

# 13. Conclusion

## En resume

- le projet expose une API REST securisee
- la logique metier est separee de la couche HTTP
- la securite repose sur Spring Security + JWT
- la persistance repose sur JPA + MySQL
- l'ensemble est presentable, testable et evolutif

## Message final

Le projet n'est pas seulement une collection d'endpoints : c'est une architecture backend complete, avec regles metier, securite, persistence et separation des responsabilites.
