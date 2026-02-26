# Backlog Produit - Projet SI Java (ESIEE-IT)

## Pitch produit

- Le produit permet de gérer des projets et des tâches dans une API Java structurée.
- Il vise des étudiants ou petites équipes qui veulent organiser leur travail.
- Le résultat attendu est un MVP sécurisé avec authentification, gestion projet/tâches et suivi d’avancement.

## Hypothèses & contraintes

- API Java avec workflow Git `main/develop/feature/*`.
- MVP réalisable sur le semestre, priorisé Must/Should/Nice.
- Authentification obligatoire (register/login au minimum).

## Acteurs

- Visiteur : non connecté
- Utilisateur : connecté, gère ses projets et ses tâches
- Admin : supervision (bonus)

## Acteurs -> objectifs -> permissions

| Acteur | Objectifs | Permissions |
|---|---|---|
| Visiteur | Découvrir et accéder à l’application | S’inscrire, se connecter |
| Utilisateur | Organiser son travail | CRUD projets, CRUD tâches, modifier profil |
| Admin | Superviser la plateforme | Lister utilisateurs, bloquer/supprimer utilisateur |

## Modules / Features

- A. Authentification & Profil
- B. Gestion des projets
- C. Gestion des tâches
- D. Recherche / Filtre
- E. Administration (bonus)

## User Stories (MoSCoW + estimation)

| ID | Module | User Story | Priorité | Estim (S/M/L) |
|---|---|---|---|---|
| US-01 | A - Auth | En tant que Visiteur, je veux créer un compte afin de pouvoir accéder à l’application. | Must | M |
| US-02 | A - Auth | En tant que Utilisateur, je veux me connecter afin de retrouver mes projets. | Must | M |
| US-03 | A - Auth | En tant que Utilisateur, je veux me déconnecter afin de sécuriser ma session. | Should | S |
| US-04 | A - Profil | En tant que Utilisateur, je veux modifier mon profil afin de mettre à jour mes informations. | Should | M |
| US-05 | B - Projets | En tant que Utilisateur, je veux créer un projet afin de structurer mon travail. | Must | M |
| US-06 | B - Projets | En tant que Utilisateur, je veux lister mes projets afin de voir tout ce que je gère. | Must | S |
| US-07 | B - Projets | En tant que Utilisateur, je veux modifier un projet afin de corriger ses informations. | Must | M |
| US-08 | B - Projets | En tant que Utilisateur, je veux supprimer un projet afin de nettoyer ma liste. | Should | S |
| US-09 | C - Tâches | En tant que Utilisateur, je veux ajouter une tâche dans un projet afin de planifier les actions à faire. | Must | M |
| US-10 | C - Tâches | En tant que Utilisateur, je veux changer le statut d’une tâche afin de suivre l’avancement. | Must | S |
| US-11 | C - Tâches | En tant que Utilisateur, je veux modifier une tâche afin d’ajuster son contenu. | Should | M |
| US-12 | C - Tâches | En tant que Utilisateur, je veux supprimer une tâche afin d’enlever les tâches inutiles. | Should | S |
| US-13 | D - Recherche | En tant que Utilisateur, je veux filtrer les tâches par statut afin de me concentrer sur les urgences. | Nice | M |
| US-14 | D - Recherche | En tant que Utilisateur, je veux rechercher une tâche par mot-clé afin de retrouver rapidement une information. | Nice | M |
| US-15 | E - Admin | En tant que Admin, je veux lister tous les utilisateurs afin de surveiller la plateforme. | Nice | M |

## Critères d’acceptation (Given / When / Then)

### US-01 - Créer un compte

- Given je suis visiteur sur l’écran d’inscription
- When je saisis un email valide et un mot de passe conforme
- Then le compte est créé
- And je peux ensuite me connecter avec ces identifiants

### US-02 - Se connecter

- Given un compte existe avec email et mot de passe valides
- When je soumets ces identifiants sur l’écran de connexion
- Then l’authentification réussit
- And j’accède à mes données utilisateur

### US-03 - Se déconnecter

- Given je suis connecté
- When je déclenche l’action de déconnexion
- Then ma session est invalidée
- And un accès protégé redemande une authentification

### US-04 - Modifier profil

- Given je suis connecté
- When je modifie mon nom d’affichage
- Then la modification est persistée
- And les nouvelles informations sont visibles au rechargement

### US-05 - Créer un projet

- Given je suis connecté
- When je crée un projet avec un nom valide
- Then le projet est enregistré
- And le projet apparaît dans ma liste

### US-06 - Lister mes projets

- Given je suis connecté et j’ai des projets
- When je demande la liste de mes projets
- Then seuls mes projets sont retournés
- And la liste contient les informations principales (id, nom, statut)

### US-07 - Modifier un projet

- Given un projet m’appartient
- When je modifie son nom ou sa description
- Then les changements sont sauvegardés
- And la version modifiée est visible dans la liste

### US-08 - Supprimer un projet

- Given un projet m’appartient
- When je confirme la suppression
- Then le projet est supprimé
- And il n’apparaît plus dans ma liste

### US-09 - Ajouter une tâche

- Given je suis connecté et un projet existe
- When je crée une tâche dans ce projet
- Then la tâche est enregistrée
- And la tâche est liée au bon projet

### US-10 - Changer le statut d’une tâche

- Given une tâche existe avec le statut `TODO`
- When je change son statut à `IN_PROGRESS` ou `DONE`
- Then le nouveau statut est sauvegardé
- And il est visible lors de la prochaine consultation

### US-11 - Modifier une tâche

- Given une tâche m’appartient
- When je modifie son titre ou son échéance
- Then les modifications sont persistées
- And les champs non modifiés restent inchangés

### US-12 - Supprimer une tâche

- Given une tâche m’appartient
- When je confirme la suppression
- Then la tâche est supprimée
- And elle n’apparaît plus dans le projet

### US-13 - Filtrer les tâches

- Given plusieurs tâches existent avec des statuts différents
- When je filtre par statut `DONE`
- Then seules les tâches `DONE` sont affichées
- And les autres tâches ne sont pas retournées

### US-14 - Rechercher une tâche par mot-clé

- Given plusieurs tâches existent dans mes projets
- When je recherche un mot-clé présent dans un titre
- Then seules les tâches correspondantes sont retournées
- And une recherche sans résultat retourne une liste vide

### US-15 - Lister les utilisateurs (Admin)

- Given je suis connecté en tant qu’admin
- When je demande la liste des comptes
- Then la liste des utilisateurs est retournée
- And un utilisateur non admin ne peut pas accéder à cette action

## Priorisation MVP (synthèse)

- Must : US-01, US-02, US-05, US-06, US-07, US-09, US-10
- Should : US-03, US-04, US-08, US-11, US-12
- Nice : US-13, US-14, US-15

## Definition of Done (DoD)

Une story est considérée Done si :

- le code compile et respecte l’architecture du projet
- les tests passent (`./gradlew test`)
- les critères d’acceptation sont validés
- une PR est ouverte vers `develop` avec description claire

## Option Issues GitHub/GitLab

- Créer une issue par story
- Labels priorité : `must`, `should`, `nice`
- Labels module : `module/auth`, `module/project`, `module/task`, `module/search`, `module/admin`
- Statuts board : `To do`, `Doing`, `Done`
