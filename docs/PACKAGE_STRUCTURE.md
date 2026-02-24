# PACKAGE STRUCTURE

## Structure actuelle

com.esieeit.projetsi

- domain.model → Entités métier (User, Project, Task)
- repository → Accès aux données (in-memory)
- service → Logique métier
- exception → Exceptions métier
- api.controller → Controllers (préparation Spring)

---

## Architecture

Controller → Service → Repository → Domain

Le controller appelle le service.
Le service contient la logique métier.
Le repository stocke les données.
Les entités sont dans le domain.

---

## Règles

- Les controllers ne parlent jamais directement aux repositories.
- Toute règle métier doit être dans les services.
- Les entités ne contiennent pas de logique d’accès aux données.