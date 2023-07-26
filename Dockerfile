# Use an official OpenJDK runtime as the base image
FROM library/amazoncorretto:17-alpine3.17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY ./target/web3270-data-sysdump-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which your Spring Boot application will listen
EXPOSE 8080

# Define the environment variables for the MongoDB connection
ENV MONGODB_HOST=web3270db
ENV MONGODB_PORT=27017
ENV MONGODB_DBNAME=web3270db
ENV MONGODB_USERNAME=evandro
ENV MONGODB_PASSWORD=tricolor


# Wait for the MongoDB container to start before running the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
