```markdown
# Structure des Packages - Clean Architecture Simplifiée

Nous adoptons une architecture en couches pour séparer la logique métier (Domain) de la technique (Infrastructure/API).

## Arborescence proposée
```text
com.esiee.project
├── domain              # COEUR DU MÉTIER (Aucune dépendance externe)
│   ├── model           # Entités (User, Project, Task...)
│   ├── enums           # Enums (TaskStatus, Role...)
│   └── exception       # Exceptions métier (ProjectNotFoundException...)
│
├── application         # CAS D'UTILISATION
│   └── service         # Logique applicative (ProjectService, TaskService...)
│
├── api                 # COUCHE PRÉSENTATION (Entrée)
│   ├── controller      # Endpoints REST (@RestController)
│   └── dto             # Objets de transfert de données (Request/Response)
│
└── infrastructure      # COUCHE TECHNIQUE (Sortie)
    ├── persistence     # Base de données (Repositories JPA, Entities JPA)
    └── config          # Configuration Spring (Security, Swagger...)