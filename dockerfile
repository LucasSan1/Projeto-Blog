# Etapa 1: Construção da aplicação com Maven
FROM maven:3.8.4-openjdk-17-slim AS builder

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo pom.xml e o diretório .mvn (onde ficam os scripts do Maven Wrapper)
COPY apiblog/pom.xml /app/
COPY .mvn /app/.mvn

# Copiar o código da API para o container
COPY apiblog/src /app/src

# Rodar o comando Maven para construir o projeto (instalando dependências e criando o JAR)
RUN mvn clean install -DskipTests

# Etapa 2: Criar a imagem final com o JAR da aplicação
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR gerado na etapa anterior para o novo container
COPY --from=builder /app/target/*.jar /app/app.jar

# Expor a porta 8080, que é onde o Spring Boot vai rodar
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "/app/app.jar"]
