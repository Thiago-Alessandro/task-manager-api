# USIN A BASE IMAGE FROM OpenJDK (If running outside of a WEG network/proxy remove the sufix)
FROM registry-docker.weg.net/openjdk:17-jdk-alpine

# Defines the work dir inside the container
WORKDIR /app

# Copy the jar file from the application to the container
COPY target/evolve-api-0.0.1-SNAPSHOT.jar /app/evolve-api.jar

# Expose the port the application will use
EXPOSE 8087

# Command to excecute the application
ENTRYPOINT ["java", "-jar", "/app/evolve-api.jar"]