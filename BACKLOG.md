\# BACKLOG - Projet SI Java



\##  Vision Produit

Application de gestion de projets et de tâches permettant aux utilisateurs

de créer des projets, ajouter des tâches et suivre leur avancement.



---



\##  Acteurs

\- Visiteur

\- Utilisateur



---



\##  User Stories (MVP)



---



\### US-01 — Inscription

\*\*Priorité : Must\*\*  

\*\*Estimation : M\*\*



En tant que \*\*Visiteur\*\*,  

je veux \*\*créer un compte\*\*,  

afin de \*\*pouvoir accéder à l’application\*\*.



\*\*Critères d’acceptation :\*\*

\- Given je suis sur la page d’inscription  

\- When je saisis un email valide et un mot de passe  

\- Then mon compte est créé



---



\### US-02 — Connexion

\*\*Priorité : Must\*\*  

\*\*Estimation : M\*\*



En tant que \*\*Utilisateur\*\*,  

je veux \*\*me connecter\*\*,  

afin de \*\*retrouver mes projets\*\*.



\*\*Critères d’acceptation :\*\*

\- Given mon compte existe  

\- When je saisis les bons identifiants  

\- Then je suis connecté



---



\### US-03 — Créer un projet

\*\*Priorité : Must\*\*  

\*\*Estimation : M\*\*



En tant que \*\*Utilisateur\*\*,  

je veux \*\*créer un projet\*\*,  

afin de \*\*organiser mes tâches\*\*.



\*\*Critères d’acceptation :\*\*

\- Given je suis connecté  

\- When je saisis un nom valide  

\- Then le projet apparaît dans ma liste



---



\### US-04 — Ajouter une tâche

\*\*Priorité : Must\*\*  

\*\*Estimation : M\*\*



En tant que \*\*Utilisateur\*\*,  

je veux \*\*ajouter une tâche à un projet\*\*,  

afin de \*\*planifier mon travail\*\*.



\*\*Critères d’acceptation :\*\*

\- Given je suis dans un projet  

\- When je crée une tâche  

\- Then elle apparaît dans la liste



---



\### US-05 — Marquer une tâche comme faite

\*\*Priorité : Must\*\*  

\*\*Estimation : S\*\*



En tant que \*\*Utilisateur\*\*,  

je veux \*\*marquer une tâche comme terminée\*\*,  

afin de \*\*suivre mon avancement\*\*.



\*\*Critères d’acceptation :\*\*

\- Given une tâche existe  

\- When je clique sur "Terminer"  

\- Then son statut devient "faite"



