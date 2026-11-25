# ADR 0004: Deployment Topology for the Current Order API

- Status: Accepted
- Date: 2026-11-25
- Decision scope: Current laboratory implementation

## Context

The application implements a Java 21 / Spring Boot 3.4.3 REST API.
The running application exposes `POST /orders` and executes the order
creation use case inside one application process.

The current infrastructure adapters are in-memory implementations:

- `InMemoryOrderRepository`
- `InMemoryInventoryAvailabilityAdapter`
- `InMemoryEventBus`
- `InMemoryProcessedEventStore`

The event consumers are Spring-managed components in the same process.
There is no deployed PostgreSQL instance, Redis instance, RabbitMQ broker,
Keycloak identity provider or separate notification service in the
current implementation.

The application is packaged as an executable Spring Boot JAR and
deployed using Docker Compose.

The host port is 8081 and the container application port is 8080.
The host port is 8081 because port 8080 is already occupied in the
development environment.

## Decision Drivers

- Keep the deployment consistent with the implemented code.
- Provide a repeatable local deployment.
- Keep the number of running infrastructure components small.
- Make the HTTP entry point explicit.
- Avoid documenting planned infrastructure as already deployed.

## Considered Options

### Option 1: Single Docker container

Run the Spring Boot application as one Docker container. Keep the
repository, inventory adapter, event bus and consumers in the same
process.

Advantages:
- Simple build and deployment.
- Matches the current code and dependency configuration.
- No additional database or broker setup.
- Easy local demonstration.

Disadvantages:
- In-memory data is not durable.
- Application restart loses runtime state.
- No independent scaling of consumers.
- A single application instance is a single point of failure.

### Option 2: Split application and infrastructure services

Deploy the API, PostgreSQL, Redis and RabbitMQ as separate services.

Advantages:
- Persistent storage and external messaging can be introduced.
- Infrastructure can be scaled independently.
- Better basis for production evolution.

Disadvantages:
- Requires adapters, configuration and operational setup not
  implemented in the current code.
- Adds deployment and maintenance complexity.
- Would make the deployment diagram inaccurate for the current lab state.

### Option 3: Kubernetes deployment

Deploy the application and infrastructure through Kubernetes.

Advantages:
- Orchestration and scaling capabilities.
- Health management and deployment strategies.

Disadvantages:
- More operational complexity.
- No current requirement or implementation evidence for Kubernetes.
- Not necessary to demonstrate the current order creation use case.

## Decision

Choose Option 1.

Deploy one executable Spring Boot application in a Docker container
named `cloud-platform-api`.

The container listens on port 8080. Docker Compose publishes the
container port as host port 8081 (`8081:8080`).

The application includes the REST controller, use case, domain model,
in-memory adapters, in-memory event bus and event consumers.

## Consequences

Positive:
- The deployment matches the source code.
- The application can be started and tested locally with Docker Compose.
- The REST API is accessible at `http://localhost:8081/orders`.

Negative:
- Orders are not persisted across process restarts.
- The in-memory event bus does not provide durable delivery.
- The deployment has no independent scaling or high availability.
- Authentication, external payment and delivery integrations are not
  implemented in this deployment.

## Follow-up

A future architecture revision may introduce a persistent repository,
external message broker, authentication provider and orchestration
platform. Such a change requires implementation, configuration,
tests and a new deployment decision before these services can be
shown as deployed components.
