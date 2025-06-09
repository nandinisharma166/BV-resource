# # Use official OpenJDK image
# FROM openjdk:17-jdk-slim
#
# # Set working directory
# WORKDIR /app
#
# # Copy jar file to container
# COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
#
# # Expose the port your app runs on
# EXPOSE 8080
#
# # Command to run the jar
# CMD ["java", "-jar", "app.jar"]

# Stage 1: Build the jar
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the jar
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

