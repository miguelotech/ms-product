# Usa una imagen base de Eclipse Temurin con OpenJDK 21
FROM eclipse-temurin:21-jdk AS builder

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto
COPY . .

# Construir el jar con Gradle
RUN ./gradlew build -x test

# Etapa de ejecución (con otra imagen más liviana)
FROM eclipse-temurin:21-jre

# Copia el jar del contenedor de construcción
COPY --from=builder /app/build/libs/ms-product-1.0.jar /ms-product.jar

# Define el puerto
EXPOSE 8081

# Ejecuta el jar
ENTRYPOINT ["java", "-jar", "/ms-product.jar"]
