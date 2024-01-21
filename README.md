##  Microservices Demo

###  Introduction

This mini-application demonstrates a microservices architecture using Spring Boot, Netflix Eureka for service discovery, Resilience4j for circuit breaking, and MongoDB for data storage. It consists of several services:

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


### User-Service Request to Career-Service:

The user-service receives a request from a client (e.g., a web application) to fetch the career information for a user with a specific User ID.

```java
@GetMapping("/{userId}")
@CircuitBreaker(name = "userCareerServiceBreaker", fallbackMethod = "userCareerServiceFallback")
public ResponseEntity<User> findUserProfileById(@PathVariable int userId) {
    // ...
}
```

### User-Service Makes an HTTP Request:

Inside the `findUserProfileById` method, the user-service needs to fetch career information from the career-service.
To do this, it constructs an HTTP request to the career-service using the RestTemplate and the URL of the career-service.

```java
String restUrl = "http://CAREER-SERVICE/career/assignments/{userId}";
String response = restTemplate.getForObject(restUrl, String.class, userId);
```

Here, `CAREER-SERVICE` is the logical name of the career-service as registered in Eureka. It's used as the hostname in the URL.

### Service Discovery with Eureka:

When the user-service makes this HTTP request using the service name `CAREER-SERVICE`, Eureka comes into play.
Eureka resolves the service name `CAREER-SERVICE` to the actual network location (IP address and port) of the career-service.

### Career-Service Handles the Request:

On the career-service side, the controller for the career-service has a mapping to handle the request.

```java
@GetMapping("/assignments/{userId}")
public ResponseEntity<CareerEntity> getallAssignmentsByCompanyId(@PathVariable int userId) {
    // ...
}
```

The career-service processes the request, fetches career information for the specified user, and sends back a response.

### User-Service Receives the Response:

The user-service receives the response from the career-service, containing the career information for the user.

### Circuit Breaker (Resilience4j):

If the career-service experiences issues or becomes unavailable, the Circuit Breaker pattern implemented in the user-service (`@CircuitBreaker`) comes into play.
The circuit breaker may open, and the user-service will execute the fallback method (`userCareerServiceFallback`) to provide a graceful response to the client.

This flow demonstrates how the user-service communicates with the career-service through REST API calls and how Eureka assists in service discovery. Additionally, the Circuit Breaker pattern ensures fault tolerance and provides a fallback mechanism for handling service failures.

### Direct Communication:

In a microservices architecture, services can communicate with each other through various methods, including direct communication and using service discovery tools like Netflix Eureka. I'll explain both approaches and show how you can prove that they work.

### Netflix Eureka Service Discovery:

In the Netflix Eureka approach, services register themselves with Eureka, and Eureka maintains a registry of all available services.
When one service needs to communicate with another, it uses the logical service name registered with Eureka, and Eureka resolves this name to the actual network location (IP address and port) of the target service.

To prove that Netflix Eureka is facilitating service discovery, you can do the following:

- Start the Eureka Server: Ensure that the Eureka Server is running and accessible at `http://localhost:9090`.

- Register Services: Verify that all microservices (`user-service`, `career-service`, and `assignment-service`) are registering themselves with Eureka. You can check the Eureka Dashboard (`http://localhost:9090`) to see if all services are listed.

- Test Service Communication: In the `user-service` code, use the logical service name (e.g., `CAREER-SERVICE`) when making HTTP requests to other services. Eureka will resolve this name to the actual service location.

- Verify Eureka Logs: Check the logs of the microservices to see Eureka resolving service names to network locations. You should see log entries related to service registration and discovery.

Here's an example of using Eureka for service discovery in the `user-service`:

```java
String restUrl = "http://CAREER-SERVICE/career/assignments/{userId}"; // Logical service name
String response = restTemplate.getForObject(restUrl, String.class, userId);
```

In this approach, Netflix Eureka plays a crucial role in dynamically discovering the network locations of services, making it easier to scale and manage microservices. It also provides resiliency features like load balancing. The proof of its functionality lies in the successful registration and resolution of service names as shown in logs and dashboards.
 