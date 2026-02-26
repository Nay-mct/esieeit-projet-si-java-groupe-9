# API Errors - TP 3.2

## Format JSON standard

Toutes les erreurs de l’API utilisent la structure suivante :

```json
{
  "timestamp": "2026-02-24T11:00:00Z",
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "La requête est invalide",
  "path": "/api/tasks",
  "details": [
    { "field": "title", "message": "title est obligatoire" }
  ]
}
```

## Mapping erreurs -> codes HTTP

- `MethodArgumentNotValidException` -> `400 BAD_REQUEST` (`VALIDATION_ERROR`)
- `InvalidDataException` -> `400 BAD_REQUEST` (`INVALID_DATA`)
- `ValidationException` (domaine) -> `400 BAD_REQUEST` (`INVALID_DATA`)
- `ResourceNotFoundException` -> `404 NOT_FOUND` (`NOT_FOUND`)
- `BusinessRuleException` -> `409 CONFLICT` (`BUSINESS_RULE_VIOLATION`)
- `Exception` -> `500 INTERNAL_SERVER_ERROR` (`INTERNAL_ERROR`)

## Scénarios manuels (curl)

## 1) Validation : title manquant

```bash
curl -i -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"description":"Sans titre"}'
```

Attendu : `400` + `error=VALIDATION_ERROR` + détail sur `title`.

## 2) Validation : title trop court

```bash
curl -i -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Hi","description":"test"}'
```

Attendu : `400` + message de taille sur `title`.

## 3) Not found : id inexistant

```bash
curl -i http://localhost:8080/api/tasks/9999
```

Attendu : `404` + `error=NOT_FOUND`.

## 4) Update : status invalide

```bash
curl -i -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"status":"INVALID_STATUS"}'
```

Attendu : `400` + `error=VALIDATION_ERROR`.

## 5) Conflit métier : transition interdite

Exemple typique : forcer une transition non autorisée de statut.

Attendu : `409` + `error=BUSINESS_RULE_VIOLATION`.

## Endpoints concernés

- `POST /api/tasks`
- `GET /api/tasks/{id}`
- `PUT /api/tasks/{id}`
- `DELETE /api/tasks/{id}`