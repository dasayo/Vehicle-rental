# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR de tu aplicación al contenedor
COPY target/vehicle-rental-app.jar app.jar

# Exponer el puerto en el que tu aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
