# Estágio 1: Compilação do JAR
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar -x test

# Estágio 2: Alvo para rodar a aplicação final (target)
FROM eclipse-temurin:21-jdk-alpine AS target
WORKDIR /app
COPY --from=build /app/build/libs/*-SNAPSHOT.jar /app/agendador-tarefas.jar
EXPOSE 8081
CMD ["java", "-jar", "/app/agendador-tarefas.jar"]

# Estágio 3: Alvo para rodar os testes (tester)
FROM gradle:8.5-jdk21 AS tester
WORKDIR /app
COPY . .
CMD ["./gradlew", "test", "--no-daemon"]