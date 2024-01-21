# Microservices README

This README file provides documentation for the microservices architecture of our application. It outlines the services, their functionality, communication, and how to run them.

## Services

### 1. API-Gateway Service

- **Main Class**: `ApiGatewayApplication.java`
- **Description**: The API Gateway handles external client requests and routes them to the appropriate downstream services based on the request path.
- **Port**: 8084
- **Routes**: Routes to USER-SERVICE, CAREER-SERVICE, and ASSIGNMENT-SERVICE.
- **Eureka Client**: Registers with Eureka service discovery at `http://localhost:9090/eureka`.

### 2. Assignment-Service

- **Main Class**: `AssignmentServiceApplication.java`
- **Description**: Manages assignments and interacts with a MongoDB database.
- **Port**: 8082
- **Database**: MongoDB at `mongodb://localhost:28018/assignmentservice`.

### 3. Career-Service

- **Main Class**: `CareerServiceApplication.java`
- **Description**: Handles career-related information and interacts with a MongoDB database.
- **Port**: 8081
- **Database**: MongoDB at `mongodb://localhost:28018/careerservice`.

### 4. ConfigurationServer

- **Description**: Provides centralized configuration for microservices.
- **Configuration**: Imports configuration from `http://localhost:8888` (Spring Cloud Config Server).

### 5. Service-Registry

- **Main Class**: `ServiceRegistryApplication.java`
- **Description**: Eureka Service Registry for service registration and discovery.
- **Port**: 9090
- **Eureka Server**: Acts as a standalone registry without fetching or registering with other Eureka servers.

## How to Run

1. Ensure MongoDB is installed and running on the specified ports (`27017` and `28018`) for Assignment-Service and Career-Service.
2. Start Service-Registry (Eureka) by running `ServiceRegistryApplication.java`.
3. Start ConfigurationServer by running the Spring Boot application.
4. Start Assignment-Service, Career-Service, and API-Gateway in any order:


The provided code consists of two microservices, "AssignmentService" and "UserService," and they communicate with each other through RESTful APIs. Here's a brief overview of the relationship and communication between these services:

1. AssignmentService:
   - It is a Spring Boot application that provides RESTful APIs for managing assignments.
   - It uses a configuration class `AssignmentConfig` to define properties related to its operation, such as service URLs.
   - The `AssignmentController` handles incoming HTTP requests and interacts with the `AssignmentService` and `AssignmentRepository`.
   - The `AssignmentEntity` class defines the data structure for assignments.
   - It doesn't directly communicate with UserService but can be configured to do so using RESTful endpoints.

2. UserService:
   - It is another Spring Boot application responsible for managing user-related operations.
   - The `UserController` handles HTTP requests for user-related tasks, including creating users and fetching user profiles.
   - It uses a `UserRepository` to interact with a MongoDB database to store user information.
   - The `UserServiceImpal` class contains business logic and communicates with the "AssignmentService" microservice to fetch assignment information for a user profile using a RESTful endpoint.
   - It utilizes Resilience4j Circuit Breaker for resilience in communication with the "AssignmentService."

In summary, these two microservices are separate applications that communicate indirectly via HTTP requests. The UserService interacts with AssignmentService to retrieve assignment information to include in user profiles. They also have their own configurations, controllers, and repositories to manage their respective functionalities.