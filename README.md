# ⚙️ IPSLStock — Backend (Spring Boot 3.x)

<p align="center">
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
  <img src="https://img.shields.io/badge/Java_17-007396?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white" />
  <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white" />
</p>

## 🚀 Vision du Projet
**IPSLStock Backend** est le moteur central de la solution de gestion de stock intelligente développée pour l'**IPSL**. Cette API REST robuste assure la persistance des données, la sécurisation des échanges et intègre un moteur d'**Analyse Prédictive** permettant d'anticiper les ruptures de stock en se basant sur l'historique des mouvements.



---

## ✨ Points Forts du Backend
* **🔒 Sécurité avec JWT** : Implémentation de **Spring Security** avec une authentification par jetons **JWT (JSON Web Token)**. Cela garantit une communication sécurisée et stateless entre le client Angular et l'API.
* **🧠 Moteur d'IA & Prédiction** : Algorithmes dédiés (package `ia`) au calcul des flux de stock pour générer des alertes de réapprovisionnement automatiques.
* **📊 Architecture N-Tiers** : Structure organisée en couches pour une scalabilité maximale.
* **⏰ Automatisation** : Utilisation de `StockScheduler` pour la vérification automatique et régulière des niveaux de stock.

---

## 🛠️ Stack Technologique
| Composant       | Technologie               |
|-----------------|---------------------------|
| Framework       | Spring Boot 3.x           |
| Langage         | Java 17                   |
| Sécurité        | Spring Security & JWT     |
| Persistance     | Spring Data JPA / Hibernate|
| Base de Données | MySQL 8.0                 |

---

## 🏗️ Architecture du Projet (Java)
Le projet suit une structure modulaire respectant les conventions Spring Boot :

```text
sn.ugb.ipsl.produit_backend/
├── config/        # Sécurité (JWT), Swagger (OpenApi), CORS
├── controller/    # Points d'entrée de l'API REST
├── exception/     # Gestion centralisée des erreurs
├── ia/            # Logique d'analyse prédictive
├── model/         # Entités JPA
├── repository/    # Accès à la base de données (Spring Data JPA)
├── scheduler/     # Tâches planifiées (StockScheduler)
└── service/       # Interfaces et implémentations métier (impl)
```
⚙️ Installation & Lancement
Prérequis
JDK 17 ou supérieur

Maven 3.x

Une instance MySQL active

Déploiement local
Clonage du projet :

Bash
git clone [https://github.com/Xhadee/produit-backend.git](https://github.com/Xhadee/produit-backend.git)
cd produit-backend
Configuration : Créez la base gestion_stock_db dans MySQL et ajustez les accès dans src/main/resources/application.properties.

Lancement :

Bash
mvn spring-boot:run
🔗 Écosystème IPSLStock
Repository Frontend : 🎨 Accéder au Frontend Angular

👤 Auteur
<p align="left">
<strong>Développeur :</strong> Khady NDIAYE



<strong>Formation :</strong> IPSL (Institut Polytechnique de Saint-Louis)



<strong>Promotion :</strong> 2025-2026
</p>

<p align="center">
<img src="https://www.google.com/search?q=https://img.shields.io/badge/Projet-Ing%C3%A9nieur-orange%3Fstyle%3Dflat-square" />
<img src="https://www.google.com/search?q=https://img.shields.io/badge/Backend-Operational-success%3Fstyle%3Dflat-square" />
</p>