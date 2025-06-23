# 📦 API Système de Gestion des Commandes

API RESTful pour gérer les clients, produits, commandes et inventaire dans un système.  
Développée avec **Spring Boot 3**, **PostgreSQL**, **RabbitMQ**, **Docker**, et **Maven**.

---

## 🚀 Fonctionnalités principales

- 🔍 Gestion des clients (CRUD, historique, stats)
- 🛒 Gestion des produits (CRUD, stock, ventes)
- 📦 Traitement des commandes (validation, annulation, remboursement)
- 📊 Analyses (ventes, tendances, inventaire)
- 📤 Événements RabbitMQ (optionnel)
- 🧪 Tests unitaires & d’intégration
- 📄 Documentation Swagger/OpenAPI

---

## ⚙️ Stack technique

| Technologie      | Version     |
|------------------|-------------|
| Java             | 17          |
| Spring Boot      | 3.5.3       |
| PostgreSQL       | 13 (Docker) |
| RabbitMQ         | 3.x         |
| Maven            | 3.9+        |
| Docker / Compose | 3.8+        |
| MapStruct        | 1.5.5       |
| Liquibase        | ✔️           |

---

## 📁 Structure du projet
```
src/java/com/technical/evaluation/orders/
├── features
├──├──client
├──├──commande
├──├──inventaire
├──├──produit
├── shares/
├──├──config
├──├──dto
├──├──exception
└──├──utils
└── exception/
```

---

## 🐳 Démarrage avec Docker

### 1. Lancer l'application

```bash
docker-compose up --build
```

### 2. Accès à l'API
- API: http://localhost:8080/api

- Swagger UI: http://localhost:8080/swagger-ui.html

- RabbitMQ UI: http://localhost:15672 (user: commande_admin, pass: commande_pass)

## 📬 Endpoints principaux

| Ressource  | Endpoint Exemple                      |
| ---------- | ------------------------------------- |
| Clients    | `GET /api/clients`                    |
| Commandes  | `POST /api/commandes`                 |
| Produits   | `PUT /api/produits/{id}/stock`        |
| Inventaire | `GET /api/inventaire/resume`          |
| Analyses   | `GET /api/analyses/tendances-revenus` |

Voir collection Postman dans /postman.

## 🧪 Tests
Exécuter tous les tests :
bash
```
./mvnw test
```

Couverture minimum :

- 70% sur la couche service

- Tests d’intégration REST avec @SpringBootTest

