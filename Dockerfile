# Étape 1 : Construction avec Maven (et Java 17)
FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /app

# Précharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source complet
COPY src ./src

# Compiler le projet et créer le jar
RUN mvn clean package -DskipTests

# Étape 2 : Image d'exécution avec JRE léger
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copier le JAR généré
COPY --from=builder /app/target/order-management-api-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port par défaut
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
