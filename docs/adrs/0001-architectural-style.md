# ADR-0001. Selection of the Architectural Style

## Status

Accepted

## Date

2026-11-25

## Context

E-Commerce Cloud Platform is expected to support a growing customer base and high traffic during promotional campaigns.

The project starts with a relatively small development team and requires a reasonable time-to-market.

The architecture must support:
- Clear separation of business domains.
- Independent development of business modules.
- Automated testing.
- Efficient debugging.
- Controlled infrastructure costs.
- Future migration toward independently deployable services.

A distributed microservices architecture would introduce additional operational complexity, network communication overhead and distributed transaction management requirements at an early stage.

A traditional monolithic architecture without explicit module boundaries would create a risk of tight coupling and uncontrolled dependencies.

## Decision

Adopt a **Modular Monolith architecture with Clean Architecture principles**.

The application will initially be deployed as one primary backend application while maintaining strict logical boundaries between business modules.

The primary modules are:

- Identity
- Catalog
- Cart
- Order
- Payment
- Inventory
- Notification

Each module must expose controlled public interfaces.

Internal domain models and implementation details must not be accessed directly by unrelated modules.

The internal structure will follow:

- Domain Layer.
- Application / Use Case Layer.
- Infrastructure Layer.
- Presentation Layer.

Dependencies must point toward the domain and application abstractions.

## Alternatives Considered

### Alternative A: Traditional Layered Monolith

**Advantages:**
- Simple deployment.
- Low infrastructure requirements.
- Easy initial development.

**Disadvantages:**
- Weak domain isolation if module boundaries are not enforced.
- High risk of cross-module coupling.
- Difficult long-term evolution.

**Reason for rejection:**

A traditional layered structure alone does not sufficiently enforce independent business module boundaries.

### Alternative B: Microservices Architecture

**Advantages:**
- Independent deployment.
- Independent scaling.
- Technology flexibility.
- Clear service ownership.

**Disadvantages:**
- Increased DevOps complexity.
- Network latency.
- Distributed transaction challenges.
- More complex monitoring.
- Higher initial infrastructure costs.

**Reason for rejection:**

The current project stage does not justify the operational overhead of a distributed architecture.

### Alternative C: Modular Monolith with Clean Architecture

**Advantages:**
- Strong logical module boundaries.
- Lower operational complexity than microservices.
- Efficient automated testing.
- Easier debugging.
- Controlled dependencies.
- Future extraction of independent services.

**Disadvantages:**
- Requires strict architectural governance.
- Modules share deployment lifecycle.
- Scaling individual modules independently is limited.

**Reason for selection:**

This approach provides the best balance between development speed, maintainability, operational simplicity and future scalability.

## Consequences

### Positive Consequences

- Reduced initial infrastructure complexity.
- Faster development and delivery.
- Improved domain isolation.
- Easier unit and integration testing.
- Clearer ownership of business capabilities.
- Future migration to microservices remains possible.

### Negative Consequences / Trade-offs

- The application is deployed as one primary unit.
- Independent module scaling is limited.
- Strict code review and architecture tests are required.
- Poorly enforced module boundaries may lead to unwanted coupling.

## Compliance and Validation

- Automated architecture dependency tests.
- Code review enforcement.
- Module interface validation.
- Unit and integration test execution.

## Related Requirements

- NFR-07: Testability.
- NFR-12: Modifiability.
- NFR-08: Scalability.
