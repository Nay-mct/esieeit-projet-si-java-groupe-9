# API Tasks - TP 3.1

## Base URL

- `http://localhost:8080`

## Ressource

- `/api/tasks`

## Endpoints CRUD

## 1) Créer une tâche

- **Method**: `POST`
- **URL**: `/api/tasks`
- **Body**:

```json
{
  "title": "Initialiser le repo",
  "description": "Créer Gradle + README"
}
```

- **Response**: `201 Created`

```json
{
  "id": 1,
  "title": "Initialiser le repo",
  "description": "Créer Gradle + README",
  "status": "TODO"
}
```

## 2) Lister les tâches

- **Method**: `GET`
- **URL**: `/api/tasks`
- **Response**: `200 OK`

```json
[
  {
    "id": 1,
    "title": "Initialiser le repo",
    "description": "Créer Gradle + README",
    "status": "TODO"
  }
]
```

## 3) Récupérer une tâche par id

- **Method**: `GET`
- **URL**: `/api/tasks/{id}`
- **Response**: `200 OK`
- **Not found**: `404 Not Found`

## 4) Mettre à jour une tâche

- **Method**: `PUT`
- **URL**: `/api/tasks/{id}`
- **Body**:

```json
{
  "title": "Repo + CI",
  "description": "Ajouter pipeline",
  "status": "IN_PROGRESS"
}
```

- **Response**: `200 OK`
- **Validation/Workflow error**: `400 Bad Request`
- **Not found**: `404 Not Found`

## 5) Supprimer une tâche

- **Method**: `DELETE`
- **URL**: `/api/tasks/{id}`
- **Response**: `204 No Content`
- **Not found**: `404 Not Found`

## Tests manuels (curl)

```bash
curl -i -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Initialiser le repo","description":"Créer Gradle + README"}'

curl -i http://localhost:8080/api/tasks

curl -i http://localhost:8080/api/tasks/1

curl -i -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Repo + CI","description":"Ajouter pipeline","status":"IN_PROGRESS"}'

curl -i -X DELETE http://localhost:8080/api/tasks/1
```

## Notes TP

- stockage en mémoire (`Map<Long, Task>`) uniquement pour la séance 3
- gestion d’erreurs centralisée prévue en TP 3.2 (`@ControllerAdvice`)
- DTOs utilisés pour ne pas exposer les entités domaine