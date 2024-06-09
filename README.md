# Clinic Management API

This repository contains the simple clinic management API, a Spring Boot application that connects to a MySQL database.

## Prerequisites

- Docker

## Getting Started

### Clone the Repository

Clone the repository to your local machine:

```sh
git clone https://github.com/alfinwijaya/clinic-management-api.git
```

### Build the Docker Image
Build the Docker image :

```sh
docker build -t "clinic-api" .
```

### Run the Application
Use Docker Compose to start the application and the MySQL database:

```sh
docker-compose up
```

The application should now be running and accessible on port 8080 of your localhost. The MySQL database will be accessible on port 3306.

## Configuration
Changing Port Settings
If the default ports (8080 for the application and 3306 for MySQL) are already in use on your machine, you can change them.

### Docker Compose Configuration
Open docker-compose.yml and modify the port mappings:

```sh
version: "3.7"
services:
  api_service:
    build: .
    restart: always
    ports:
      - "YOUR_LOCAL_PORT:8080"

  mysqldb:
    image: "mysql:8.0"
    restart: always
    ports:
      - "YOUR_LOCAL_PORT:3306"
```

### Application Properties
Open src/main/resources/application.properties and update the server port if needed:

```sh
server.port=YOUR_LOCAL_PORT

spring.datasource.url=jdbc:mysql://localhost:YOUR_LOCAL_PORT/clinic
```

## Stopping the Application
To stop the application and remove the containers, use:

```sh
docker-compose down
```

