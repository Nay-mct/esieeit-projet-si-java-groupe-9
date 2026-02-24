# DOMAIN MODEL — Projet SI Java

## Acteurs

- Visiteur
- Utilisateur
- Admin

---

## Entités principales

### User
- id : String
- email : String
- password : String

Un utilisateur peut posséder plusieurs projets.

---

### Project
- id : String
- name : String
- ownerUserId : String

Un projet appartient à un utilisateur.
Un projet contient plusieurs tâches.

---

### Task
- id : String
- projectId : String
- title : String
- description : String
- status : TODO | DONE

Une tâche appartient à un projet.

---

## Relations

User 1 → * Project  
Project 1 → * Task  

---

## Règles métier

- Un utilisateur ne peut modifier que ses propres projets.
- Un projet doit avoir un nom non vide.
- Une tâche doit avoir un titre non vide.
- Une tâche commence toujours avec le statut TODO.
- Seul le propriétaire peut supprimer un projet.

---

## Diagramme (simplifié)

User
  |
  | 1 → *
  |
Project
  |
  | 1 → *
  |
Task