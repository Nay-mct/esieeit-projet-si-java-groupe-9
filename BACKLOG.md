# BACKLOG — Projet SI Java (Groupe 9)

## Vision produit
[Décrire en 2–4 lignes le produit : à quoi il sert, pour qui, et pourquoi.]

## Acteurs
- Visiteur (non connecté)
- Utilisateur (connecté)
- Admin (bonus)

## Conventions
- Priorité (MoSCoW) : Must / Should / Nice
- Estimation : S (≤2h) / M (3–5h) / L (>5h, à découper)

---

# Module A — Authentification & Profil

## US-01 — [Must] [M] Inscription
En tant que **Visiteur**, je veux **créer un compte** afin de **pouvoir accéder à l’application**.

**Critères d’acceptation**
- Given je suis sur la page d’inscription  
  When je saisis un email valide et un mot de passe conforme  
  Then mon compte est créé et je suis redirigé vers la connexion
- Given l’email est déjà utilisé  
  When je valide le formulaire  
  Then un message d’erreur s’affiche et le compte n’est pas créé

## US-02 — [Must] [M] Connexion
En tant que **Utilisateur**, je veux **me connecter** afin de **retrouver mes projets**.

**Critères d’acceptation**
- Given mon compte existe  
  When je saisis les bons identifiants  
  Then je suis connecté et j’accède à la liste de mes projets
- Given les identifiants sont incorrects  
  When je tente de me connecter  
  Then je reste sur la page et une erreur s’affiche

## US-03 — [Should] [S] Déconnexion
En tant que **Utilisateur**, je veux **me déconnecter** afin de **sécuriser ma session**.

**Critères d’acceptation**
- Given je suis connecté  
  When je clique sur “Déconnexion”  
  Then je suis déconnecté et renvoyé vers l’accueil

## US-04 — [Should] [S] Modifier mon profil
En tant que **Utilisateur**, je veux **modifier mon profil** afin de **mettre à jour mes informations**.

**Critères d’acceptation**
- Given je suis connecté  
  When je modifie mon nom et j’enregistre  
  Then les informations sont sauvegardées et affichées

---

# Module B — Gestion des projets

## US-05 — [Must] [M] Créer un projet
En tant que **Utilisateur**, je veux **créer un projet** afin de **structurer mon travail**.

**Critères d’acceptation**
- Given je suis connecté  
  When je saisis un nom de projet valide  
  Then le projet apparaît dans ma liste
- Given le nom est vide  
  When je valide  
  Then le projet n’est pas créé et une erreur s’affiche

## US-06 — [Must] [S] Lister mes projets
En tant que **Utilisateur**, je veux **lister mes projets** afin de **voir tout ce que je gère**.

**Critères d’acceptation**
- Given j’ai au moins un projet  
  When j’ouvre la page “Mes projets”  
  Then je vois la liste de mes projets
- Given je n’ai aucun projet  
  When j’ouvre la page “Mes projets”  
  Then je vois un message “Aucun projet”

## US-07 — [Must] [M] Modifier un projet
En tant que **Utilisateur**, je veux **modifier un projet** afin de **corriger ou compléter ses infos**.

**Critères d’acceptation**
- Given un projet existe  
  When je modifie son nom et j’enregistre  
  Then les modifications sont visibles dans la liste

## US-08 — [Should] [M] Supprimer un projet
En tant que **Utilisateur**, je veux **supprimer un projet** afin de **nettoyer ma liste**.

**Critères d’acceptation**
- Given un projet existe  
  When je confirme la suppression  
  Then le projet est supprimé et n’apparaît plus dans la liste

---

# Module C — Gestion des tâches

## US-09 — [Must] [M] Ajouter une tâche
En tant que **Utilisateur**, je veux **ajouter une tâche dans un projet** afin de **planifier les actions à faire**.

**Critères d’acceptation**
- Given je suis dans un projet  
  When je crée une tâche avec un titre valide  
  Then la tâche apparaît dans la liste du projet

## US-10 — [Must] [M] Changer le statut d’une tâche
En tant que **Utilisateur**, je veux **changer le statut d’une tâche** afin de **suivre l’avancement**.

**Critères d’acceptation**
- Given une tâche est “à faire”  
  When je la marque “terminée”  
  Then son statut devient “faite”
- Given une tâche est “faite”  
  When je la “réouvre”  
  Then son statut redevient “à faire”

## US-11 — [Should] [M] Modifier une tâche
En tant que **Utilisateur**, je veux **modifier une tâche** afin de **ajuster son contenu**.

**Critères d’acceptation**
- Given une tâche existe  
  When je modifie son titre (et description)  
  Then les changements sont sauvegardés

## US-12 — [Should] [M] Supprimer une tâche
En tant que **Utilisateur**, je veux **supprimer une tâche** afin de **enlever les tâches inutiles**.

**Critères d’acceptation**
- Given une tâche existe  
  When je confirme la suppression  
  Then la tâche disparaît de la liste

---

# Module D — Recherche / Filtre

## US-13 — [Nice] [M] Filtrer les tâches par statut
En tant que **Utilisateur**, je veux **filtrer les tâches par statut** afin de **me concentrer sur les tâches urgentes**.

**Critères d’acceptation**
- Given des tâches “à faire” et “faites” existent  
  When je filtre “à faire”  
  Then seules les tâches “à faire” sont affichées

## US-14 — [Nice] [M] Rechercher une tâche par mot-clé
En tant que **Utilisateur**, je veux **rechercher une tâche par mot-clé** afin de **retrouver rapidement une information**.

**Critères d’acceptation**
- Given plusieurs tâches existent  
  When je tape un mot-clé  
  Then seules les tâches correspondantes sont affichées

---

# Module E — Administration (bonus)

## US-15 — [Nice] [L] Gestion des utilisateurs
En tant que **Admin**, je veux **voir/manager les utilisateurs** afin de **superviser l’application**.

**Critères d’acceptation**
- Given je suis admin  
  When j’ouvre la page “Utilisateurs”  
  Then je vois la liste des utilisateurs
- Given je ne suis pas admin  
  When j’essaie d’accéder à “Utilisateurs”  
  Then l’accès est refusé
