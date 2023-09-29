# Build Stage
# Multi-Stage Build to reduce the Size of the final Image by Excluding Build-Time Dependencies that are not needed at Runtime
FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
# Only copy necessary Files for the Build
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
RUN ./gradlew clean build -x test

# Run Stage
# Using Java Runtime Environment to run JAR to reduce Image Size
FROM eclipse-temurin:17-jre
WORKDIR /app
# Define a Directory for Data Storage
RUN mkdir /app/data
# Create a non-root User for Security Reasons
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
# Give the non-root User Permissions to the Data Directory
RUN chown -R appuser:appgroup /app/data
# Switch to the non-root User
USER appuser
# Copy Files (i.e. JAR File) from Bulild Stage to the Soruce Path
COPY --from=builder /app/build/libs/komdatisbackend-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "komdatisbackend-0.0.1-SNAPSHOT.jar"]