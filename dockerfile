# Etapa 1: Construção do Front-End
FROM node:18 AS frontend

# Define o diretório de trabalho
WORKDIR /app/blogfront

# Copia o package.json e o package-lock.json
COPY blogfront/package.json blogfront/package-lock.json ./

# Instala as dependências do front-end
RUN npm install

# Copia o código do front-end para dentro da imagem
COPY blogfront/ ./

# Copie o diretório do frontend para o container
COPY blogfront/ /app/blogfront/

# Gera os arquivos de build do front-end
RUN npm run build

# Etapa 2: Construção do Back-End (API)
FROM openjdk:17-jdk AS backend

# Define o diretório de trabalho
WORKDIR /app

# Copia o pom.xml (para Maven) ou build.gradle (para Gradle)
COPY apiblog/pom.xml apiblog/mvnw ./
COPY apiblog/.mvn .mvn

# Instala as dependências do back-end (Java)
RUN ./mvnw clean install

# Copia o código do back-end
COPY apiblog/ ./

# Etapa 3: Expondo as portas e rodando as aplicações
EXPOSE 8080
EXPOSE 3000

# Inicia o back-end e o front-end simultaneamente
CMD ["sh", "-c", "java -jar /app/apiblog/target/seu-arquivo.jar & cd /app/blogfront && npm start"]
