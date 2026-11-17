# ==========================================
# STAGE 1 - BUILD
# ==========================================
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Garante permissão de execução para o Gradle Wrapper
RUN chmod +x ./gradlew

# Compila e gera o JAR executável do Spring Boot
RUN ./gradlew clean bootJar -x test --no-daemon


# ==========================================
# STAGE 2 - RUNTIME
# ==========================================
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Copia o JAR gerado durante a compilação
COPY --from=build /app/build/libs/*.jar /app/agendador-tarefas.jar

# Porta utilizada pelo Spring Boot
EXPOSE 8081

# Inicia a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "/app/agendador-tarefas.jar"]