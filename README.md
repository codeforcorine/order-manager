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

## 📊 Couverture des fonctionnalités

| Module                  | Fonction                                   | Implémentation |
|-------------------------|--------------------------------------------|----------------|
| **Clients**             | liste                                      | 70%            |
|                         | création                                   | 100%           |
|                         | mise à jour                                | 80%            |
|                         | historique commandes                       | 80%            |
|                         | statistiques d'achat                       | 80%            |
| **Produits**            | liste                                      | 80%            |
|                         | ajout produit                              | 80%            |
|                         | mise à jour produit                        | 80%            |
|                         | mise à jour quantité en stock              | 80%            |
|                         | liste produits avec stock bas              | 80%            |
|                         | analyse ventes                             | 80%            |
| **Commandes**           | créer une nouvelle commande                | 80%            |
|                         | détail commande                            | 80%            |
|                         | mise à jour statut commande                | 80%            |
|                         | annuler commande                           | 80%            |
|                         | liste commande avec filtre                 | 80%            |
|                         | traiter un remboursement                   | 0%             |
| **Inventaire & Analyse**| aperçu inventaire                          | 80%            |
|                         | analyse ventes                             | 80%            |
|                         | analyse des tendances de revenu            | 80%            |
|                         | vérification de santé                      | 80%            |
| **Documentation**       | Swagger                                    | 50%            |
|                         | README                                     | 60%            |


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
- API: http://localhost:8086/api

- Swagger UI: http://localhost:8086/swagger-ui/index.html

- RabbitMQ UI: http://localhost:15672/ (user: commande_admin, pass: commande_pas)

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

