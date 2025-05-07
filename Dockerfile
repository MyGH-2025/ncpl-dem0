
# Step 1: Use official OpenJDK as base image
FROM openjdk:17-jdk-slim
# Step 2: Set working directory inside the container
WORKDIR /app
# Step 3: Copy the JAR file from the host to the container
COPY target/ci-cd-demo.jar ci-cd-demo.jar
# Step 4: Expose the application port
EXPOSE  9988
# Step 5: Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "ci-cd-demo.jar"]