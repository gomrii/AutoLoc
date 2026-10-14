# AutoLoc - Plateforme de gestion de location de véhicules multi-agences

## 👤 Informations de l'étudiant
- **Nom & Prénom** : Gomri Youssef
- **Module** : Architecture des Systèmes d'Information (ASI)
- **Année Universitaire** : 2026-2027
- **Établissement** : ESPRIT - UP ASI

---

## 🚗 Description du Projet
« AutoLoc » est une plateforme complète de gestion de location de véhicules multi-agences développée avec le framework **Spring Boot 3**, **Spring Data JPA**, **Hibernate** et **MySQL**.

Ce dépôt Git retrace l'ensemble des **5 Ateliers du cours d'Architecture des SI**, réalisés de manière incrémentale et cumulative :

| Atelier | Chapitre | Description détaillée | Commit / Push GitHub |
| :--- | :--- | :--- | :--- |
| **Atelier 1** | Chapitre 1 | Configuration de l'environnement (Adoptium JDK 17, IntelliJ IDEA Ultimate, SGBD MySQL utoloc_db, Postman, Git). Initialisation du dépôt AutoLoc avec README.md et .gitignore. | Push 1 |
| **Atelier 2** | Chapitre 2 | Initialisation du projet Spring Boot / Maven (utoloc-api), configuration d'pplication.properties, création des énumérations (StatutVehicule, CategorieVehicule, ModePaiement, RoleEmploye, StatutReservation) et des 9 entités JPA sans associations (Vehicule, Agence, Client, Employe, Contrat, Reservation, Paiement, Maintenance, Equipement). | Push 2 |
| **Atelier 3** | Chapitre 3 (Ch.2 suite) | Modélisation et implémentation des associations relationnelles bidirectionnelles (@OneToMany, @ManyToOne, @OneToOne, @ManyToMany, table de jointure ehicule_equipement), application des règles de Cascade (PERSIST, MERGE, REMOVE, orphanRemoval) et de Fetch (EAGER, LAZY), et tests d'intégration avec AgenceTests & AgenceRepositoryMock. | Push 3 (« Chap3 ») |
| **Atelier 4** | Chapitre 4 | Mise en place de la couche d'accès aux données Spring Data JPA avec les 9 interfaces Repository (IAgenceRepository, IClientRepository, IVehiculeRepository, etc.), tests avancés avec gestion d'unicité par horodatage (System.currentTimeMillis), tri descendant (Sort.by) et pagination par lots de 2 (PageRequest). | Push 4 |
| **Atelier 5** | Chapitre 5 | Architecture logicielle IoC / DI : création de la classe utilitaire Utils.isNotEmpty, gestion centralisée des erreurs avec EntityNotFoundException, conception de la couche de services générique (IGenericService<T, ID> et GenericServiceImpl<T, ID>), implémentations spécifiques (BasicAgenceServiceImpl et AgenceServiceImpl avec @Primary pour la résolution des conflits d'injection), et extension aux 8 autres entités métier (dont le service de paiement en lecture seule). | Push 5 |

---

## 🏛️ Architecture et Structure des Packages
`
src/main/java/tn/esprit/autoloc/
├── AutolocApiApplication.java
├── Utils.java                          # Méthodes utilitaires (isNotEmpty)
├── domain/                             # Entités métier & énumérations JPA
│   ├── Agence.java
│   ├── Vehicule.java
│   ├── Client.java
│   ├── Employe.java
│   ├── Contrat.java
│   ├── Reservation.java
│   ├── Paiement.java
│   ├── Maintenance.java
│   ├── Equipement.java
│   ├── CategorieVehicule.java
│   ├── ModePaiement.java
│   ├── RoleEmploye.java
│   ├── StatutReservation.java
│   └── StatutVehicule.java
├── exception/                          # Exceptions métier personnalisées
│   └── EntityNotFoundException.java
├── repository/                         # Interfaces Spring Data JPA
│   ├── IAgenceRepository.java
│   ├── IClientRepository.java
│   ├── IContratRepository.java
│   ├── IEmployeRepository.java
│   ├── IEquipementRepository.java
│   ├── IMaintenanceRepository.java
│   ├── IPaiementRepository.java
│   ├── IReservationRepository.java
│   └── IVehiculeRepository.java
└── service/                            # Couche métier et inversion de contrôle
    ├── IGenericService.java
    ├── GenericServiceImpl.java
    ├── IAgenceService.java
    ├── BasicAgenceServiceImpl.java
    ├── AgenceServiceImpl.java
    ├── IClientService.java / ClientServiceImpl.java
    ├── IVehiculeService.java / VehiculeServiceImpl.java
    ├── IEmployeService.java / EmployeServiceImpl.java
    ├── IContratService.java / ContratServiceImpl.java
    ├── IReservationService.java / ReservationServiceImpl.java
    ├── IPaiementService.java / PaiementServiceImpl.java (Lecture seule)
    ├── IMaintenanceService.java / MaintenanceServiceImpl.java
    └── IEquipementService.java / EquipementServiceImpl.java
`

---

## 🚀 Exécution et Tests
- **Compiler le projet** :
  `powershell
  .\mvnw.cmd clean compile
  `
- **Lancer l'application** :
  `powershell
  .\mvnw.cmd spring-boot:run
  `
- **Exécuter les tests unitaires et d'intégration** :
  `powershell
  .\mvnw.cmd test
  `
