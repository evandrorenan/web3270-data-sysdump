# Web3270 Data Sysdump

A Spring Boot application designed to process, store, and analyze system dumps from IBM 3270 terminals/emulators.

## Prerequisites

- Java 17 or higher
- Docker (optional, for containerized deployment)
- MongoDB
- Azure Storage Account

## Configuration

The following environment variables need to be set before running the application:

### MongoDB Configuration
```
SPRING_DATA_MONGODB_URI=mongodb://username:password@host:port/database
SPRING_DATA_MONGODB_DATABASE=your_database_name
```

### Azure Blob Storage Configuration
```
AZURE_STORAGE_CONNECTION_STRING=your_azure_storage_connection_string
AZURE_STORAGE_CONTAINER_NAME=your_container_name
```

### Application Configuration
```
SERVER_PORT=8080 # Optional, defaults to 8080
SPRING_PROFILES_ACTIVE=prod # Optional, defaults to 'default'
```

## Running Locally

1. Set the required environment variables
2. Build the application:
   ```bash
   ./mvnw clean package
   ```
3. Run the application:
   ```bash
   java -jar target/web3270-data-sysdump-0.0.1-SNAPSHOT.jar
   ```

## Running with Docker

1. Build the Docker image:
   ```bash
   docker build -t web3270-data-sysdump .
   ```

2. Run the container:
   ```bash
   docker run -d \
     --name web3270-data-sysdump \
     -p 8080:8080 \
     -e SPRING_DATA_MONGODB_URI=mongodb://username:password@host:port/database \
     -e SPRING_DATA_MONGODB_DATABASE=your_database_name \
     -e AZURE_STORAGE_CONNECTION_STRING=your_azure_storage_connection_string \
     -e AZURE_STORAGE_CONTAINER_NAME=your_container_name \
     web3270-data-sysdump
   ```

Note: When running with Docker, make sure to:
- Replace the MongoDB URI with your actual MongoDB connection string
- Use the correct Azure Storage connection string and container name
- Adjust the host port (8080) if needed

## Health Check

The application includes Spring Boot Actuator for health monitoring. Once running, you can check the application health at:
```
http://localhost:8080/actuator/health
```

## Building from Source

1. Clone the repository:
   ```bash
   git clone https://github.com/evandrorenan/web3270-data-sysdump.git
   ```

2. Navigate to the project directory:
   ```bash
   cd web3270-data-sysdump
   ```

3. Build the project:
   ```bash
   ./mvnw clean package
   ```

## Development

For development, you can use Spring Boot DevTools which is included in the project. It provides features like automatic restart when files change.

## Support

For support and questions, please [create an issue](https://github.com/evandrorenan/web3270-data-sysdump/issues) in the repository.
