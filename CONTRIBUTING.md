# Contribuer au projet

## Workflow de branches

- Base de travail : `develop`
- Une user story = une branche `feature/<id>-<nom>`
- Pas de commit direct sur `main`

## Commits

Format : `<type>(<scope>): <message>`

Exemples :

- `chore(init): bootstrap gradle wrapper and project structure`
- `docs(readme): add setup and workflow instructions`
- `test(app): add initial sanity test`

## Pull Request

- Cible : `develop`
- Utiliser le template PR
- Vérifier que `./gradlew test` passe avant soumission

## Issues

- Utiliser les templates `bug` et `task`
- Décrire les critères de fin
