# Usa una imagen con Maven para compilar la app
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Establece el directorio de trabajo
WORKDIR /app

# Copia el pom.xml y las dependencias primero para aprovechar el caché de Docker
COPY pom.xml .
COPY src ./src

# Compila el proyecto sin correr tests
RUN mvn clean package -DskipTests

# Segunda etapa: imagen más ligera con solo el JAR
FROM openjdk:17

WORKDIR /app

# Copia el JAR generado desde la etapa de construcción
COPY --from=builder /app/target/proyecto-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto que usa la app
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]