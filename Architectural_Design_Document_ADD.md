# Architectural Design Document (ADD)
## E-Commerce Cloud Platform — Laboratory Implementation

- Version: 1.0
- Date: 2026-11-25
- Architecture baseline: As-Is implementation
- Runtime: Java 21, Spring Boot 3.4.3
- Deployment: Docker Compose

---

# 1. Introduction and Goals

## 1.1 Purpose

This document describes the architecture of the current E-Commerce
Cloud Platform laboratory implementation. It records the implemented
order creation functionality, its software structure, runtime
interactions and deployment topology.

The document distinguishes the implemented system from possible
future marketplace capabilities.

## 1.2 System Scope

The implemented application provides an order creation REST endpoint.

The current request flow includes:
- Receiving order data through HTTP/JSON.
- Mapping the request to an application command.
- Creating and validating domain objects.
- Checking item availability through an inventory port.
- Saving the order through a repository port.
- Publishing an order-created event.
- Dispatching events to in-process consumers.

The current API operation is `POST /orders`.

## 1.3 Architectural Goals

- Maintain separation between domain, application and infrastructure.
- Keep business rules independent of web and storage implementation details.
- Expose order creation through a REST interface.
- Use interfaces (ports) for infrastructure dependencies.
- Make the current deployment reproducible using Docker Compose.
- Keep diagrams, API contract and deployment configuration consistent.

## 1.4 Out of Scope

The following capabilities are not implemented in the current deployment:
- Product catalog and search API.
- Shopping cart API.
- Customer account management.
- Authentication and authorization integration.
- External payment processing.
- Shipment creation and tracking.
- Persistent relational database.
- Distributed cache.
- External message broker.
- Separate notification microservice.
- Kubernetes orchestration.

These may be considered future architectural directions, but are not
represented as running services in this document.

---

# 2. Constraints and Non-Functional Requirements

## 2.1 Technical Constraints

| Constraint | Current implementation |
|---|---|
| Programming language | Java 21 |
| Framework | Spring Boot 3.4.3 |
| Build system | Maven |
| HTTP server | Embedded Tomcat |
| API style | REST / JSON |
| API endpoint | POST /orders |
| Containerization | Docker |
| Orchestration for local deployment | Docker Compose |
| Container port | 8080 |
| Host port | 8081 |
| Persistence | In-memory repository |
| Event transport | In-process event bus |
| Tests | JUnit 5, Mockito, ArchUnit |

## 2.2 Quality Requirements — ISO/IEC 25010

The following requirements are architecture objectives. They are not
claims that all quality characteristics have been fully measured.

| Quality characteristic | Requirement / current consideration |
|---|---|
| Functional suitability | The order creation operation validates domain data, checks inventory availability and returns a created order. |
| Performance efficiency | The current in-memory adapters avoid external network calls in the order creation path. No formal load test result is claimed. |
| Compatibility | The API accepts and returns JSON over HTTP. |
| Usability | The API exposes a defined request and response schema in OpenAPI. |
| Reliability | Domain validation and inventory checks prevent invalid orders from being saved. In-memory state is not durable. |
| Security | The current endpoint does not demonstrate an authentication provider or production security configuration. |
| Maintainability | Clean Architecture separates application ports, domain types and infrastructure adapters. |
| Portability | Docker packaging provides a repeatable Java runtime environment. |
| Safety | Input constraints are enforced by domain constructors and use-case logic; HTTP error mapping requires further verification. |

## 2.3 Important Limitations

- In-memory data is lost when the application process restarts.
- There is no durable message delivery.
- There is no demonstrated multi-instance deployment.
- The current endpoint is not protected by an integrated identity provider.
- The host port 8081 is a local environment mapping, not a production ingress design.

---

# 3. System Context — C4 Level 1

The current external actor is a customer submitting order data.

The customer communicates with the E-Commerce Cloud Platform through
the REST API using HTTP/JSON.

The current implementation boundary includes the order creation
application. External identity, payment, delivery and monitoring
systems are not connected in the running deployment.

Diagram: [C4 System Context](diagrams/01_system_context.puml)

The context diagram describes the implemented laboratory scope,
not the complete future marketplace vision.

---

# 4. Architecture Strategy and Decisions

## 4.1 Architectural Style

The application follows Clean Architecture principles within a
modular monolith.

The source code is organized into the following logical areas:

- Domain: order aggregate, order items, address, status and repository abstraction.
- Application: create-order use case, command and ports.
- Infrastructure: in-memory repository, inventory adapter, event bus and consumers.
- WebAPI: Spring configuration and REST controller.
- Events: event data structures used by the application.

The application is deployed as one executable Spring Boot process.
The logical layers are not separate deployable services.

## 4.2 Dependency Strategy

The application use case depends on abstractions:
- `OrderRepository`
- `InventoryAvailabilityPort`
- `IEventPublisher`

Infrastructure adapters implement these abstractions.

The domain model does not depend on Spring MVC, Docker or a database
implementation.

## 4.3 Event Handling

The application publishes `OrderCreatedEvent` through `IEventPublisher`.
The configured in-memory event bus has subscribers:
- `StockReservationConsumer`
- `OrderNotificationConsumer`

`PaymentAuditConsumer` subscribes to `PaymentProcessedEvent`.

These consumers are configured within the same Spring application.
Their presence does not imply a separate notification service or
external message broker.

## 4.4 Architecture Decision Records

The repository contains earlier architecture decisions:
- `docs/adr/0001-architectural-style.md`
- `docs/adr/0002-database-strategy.md`
- `docs/adr/0003-security-framework.md`

The deployment decision for this implementation is recorded in:
- `docs/adr/0004-deployment-topology.md`

The new decision selects a single Dockerized Spring Boot application
with in-memory adapters for the current laboratory baseline.

---

# 5. Structural Architecture

## 5.1 Container Diagram — C4 Level 2

The current deployment has one application container:
`cloud-platform-api`.

It runs Java 21 and Spring Boot 3.4.3 with embedded Tomcat.
The application exposes `POST /orders`.

There are no separately deployed database, cache, broker or
notification containers in the current Compose configuration.

Diagram: [C4 Container Diagram](diagrams/02_container_diagram.puml)

## 5.2 Component Diagram

The backend component structure includes:
- `CreateOrderController`
- `CreateOrderUseCase`
- Domain model types
- Repository and inventory ports
- In-memory repository and inventory adapters
- In-memory event bus
- Event consumers
- In-memory processed-event store

Diagram: [Backend Component Diagram](diagrams/03_backend_components.puml)

## 5.3 Domain Model

The implemented order domain includes:
- `Order`
- `OrderItem`
- `Address`
- `OrderStatus`
- `OrderRepository`

The domain diagram is limited to types present in the current source
code and does not claim that all marketplace entities are implemented.

Diagram: [Domain Class Diagram](diagrams/04_domain_class_diagram.puml)

## 5.4 API Contract

The OpenAPI contract is maintained in `api/openapi.yaml`.

The currently implemented operation is:
- `POST /orders`

The request includes `customerId`, `items` and `shippingAddress`.
A successful response contains `id`, `customerId`, `status`,
`totalAmount` and `createdAt`.

The API is available in the local Docker environment at:
`http://localhost:8081/orders`.

---

# 6. Dynamic Architecture

## 6.1 Order Creation Scenario

1. The customer sends a JSON request to `POST /orders`.
2. `CreateOrderController` maps the request to `CreateOrderCommand`.
3. `CreateOrderUseCase` creates domain objects.
4. The use case checks availability for each order item.
5. If all items are available, the order is saved through the repository port.
6. The use case publishes `OrderCreatedEvent`.
7. The in-memory event bus dispatches the event to its subscribers.
8. The controller returns HTTP 201 with the order response.

If inventory is insufficient, the use case throws
`InsufficientInventoryException` instead of saving the order.

Diagram: [Order Creation Sequence](diagrams/05_order_sequence.puml)

## 6.2 Runtime Event Consumers

The application configuration registers three consumers:
- `StockReservationConsumer` — subscribes to `OrderCreatedEvent`.
- `OrderNotificationConsumer` — subscribes to `OrderCreatedEvent`.
- `PaymentAuditConsumer` — subscribes to `PaymentProcessedEvent`.

The current order creation flow publishes `OrderCreatedEvent`.
The existence of the payment audit consumer does not mean that an
external payment workflow is implemented.

---

# 7. Deployment Architecture

## 7.1 Deployment Topology

The current deployment consists of one Docker container:
`cloud-platform-api`.

The container runs the executable Spring Boot JAR on Java 21.
Embedded Tomcat listens on port 8080 inside the container.

Docker Compose maps:
- Host port: 8081
- Container port: 8080

Mapping: `8081:8080`

The API can be accessed locally at:
`http://localhost:8081/orders`

## 7.2 Deployment Artifacts

| Artifact / element | Value |
|---|---|
| Docker Compose service | cloud-platform-api |
| Container name | cloud-platform-api |
| Build definition | Dockerfile |
| Build image | Maven 3.9.9 with Eclipse Temurin 21 |
| Runtime image | Eclipse Temurin 21 JRE |
| Executable artifact | ecommerce-clean-architecture-1.0.0.jar |
| Application port | 8080 |
| Published host port | 8081 |
| Startup command | java -jar app.jar |
| Restart policy | unless-stopped |

## 7.3 Communication Protocols

| Source | Destination | Protocol |
|---|---|---|
| HTTP client | Docker host | HTTP, TCP port 8081 |
| Docker host | API container | Published port mapping 8081:8080 |
| Embedded Tomcat | Spring REST controller | Internal HTTP request dispatch |
| Application components | In-memory adapters and event bus | In-process method calls |

No PostgreSQL, Redis, RabbitMQ or external identity provider
connections are configured in the current deployment.

## 7.4 Deployment Diagram

Diagram: [UML Deployment Diagram](diagrams/08_deployment_diagram.puml)

## 7.5 Operational Limitations

- A container restart recreates the application process.
- In-memory order data is not persistent.
- The event bus does not provide durable queues.
- No independent scaling of event consumers is configured.
- A single application container does not provide high availability.

---

# 8. Trade-offs, Risks and Future Evolution

## 8.1 Trade-offs

| Decision | Benefit | Cost / limitation |
|---|---|---|
| Modular monolith | Simple deployment and clear logical separation | Modules are deployed and scaled together |
| In-memory repository | Simple local execution and testing | Data is lost on restart |
| In-memory event bus | No external broker required | No durable asynchronous delivery |
| Single Docker container | Reproducible local deployment | Single runtime failure domain |
| HTTP REST interface | Simple integration contract | No authentication integration in current implementation |

## 8.2 Risks

1. Data loss: orders are stored in memory and are not durable.
2. Availability: the deployment has one application instance.
3. Event delivery: in-memory event processing does not guarantee delivery after process failure.
4. Security: authentication and authorization are not demonstrated in the current API.
5. Scalability: the current in-process architecture cannot independently scale consumers.
6. Contract drift: API documentation must be updated whenever controller behavior changes.

## 8.3 Future Evolution

Possible future steps, subject to separate implementation and architecture
decisions:
- Introduce a persistent repository adapter and relational database.
- Introduce an external message broker if durable asynchronous processing is required.
- Add authentication and authorization.
- Add external payment and delivery adapters.
- Add health checks, metrics and operational monitoring.
- Evaluate multi-instance deployment and orchestration.

These items are recommendations for future work, not components
of the current deployment.

## 8.4 Conclusion

The current laboratory implementation is a Dockerized Java 21 /
Spring Boot modular monolith using Clean Architecture principles.
Its implemented business flow is order creation through `POST /orders`.

The deployment consists of one application container, with in-memory
persistence, inventory availability and event handling.

The ADD, OpenAPI contract, C4 diagrams, component diagram and UML
deployment diagram describe this current implementation. Planned
external infrastructure is explicitly distinguished from deployed
components.
