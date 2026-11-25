# ADR-0002. Database and Storage Strategy

## Status

Accepted

## Date

2026-11-25

## Context

The marketplace processes structured transactional information including customers, products, shopping carts, orders, payments and inventory records.

The platform requires:
- Transactional consistency.
- Referential integrity.
- Reliable persistence.
- Efficient query execution.
- Support for concurrent operations.
- Backup and recovery.
- Horizontal application scaling.

The system also requires low-latency access to frequently requested catalog information and temporary data.

A single storage technology may not efficiently address all these requirements.

## Decision

Adopt a **Hybrid Persistence Strategy** with PostgreSQL as the primary transactional database and Redis as the caching and temporary-data layer.

RabbitMQ will be used as the asynchronous messaging infrastructure rather than as a primary database.

### PostgreSQL

Responsible for:
- Customer records.
- Product records.
- Orders.
- Order items.
- Payment references and transaction states.
- Inventory records.
- Transactional business data.

### Redis

Responsible for:
- Frequently accessed catalog cache.
- Temporary shopping cart data where appropriate.
- Short-lived session-related data.
- Rate limiting counters.
- Temporary application data.

Redis must not replace PostgreSQL as the authoritative source of transactional order and payment records.

### RabbitMQ

Responsible for:
- Asynchronous order processing events.
- Payment status events.
- Inventory update events.
- Notification delivery tasks.

Reliable message handling must include acknowledgement, retry and dead-letter handling.

## Alternatives Considered

### Alternative A: PostgreSQL Only

**Advantages:**
- Simple infrastructure.
- Strong transactional consistency.
- Mature relational database functionality.

**Disadvantages:**
- More read load on the primary database.
- Temporary data operations compete with transactional workloads.
- Additional cache optimization would still be needed.

**Reason for rejection:**

A dedicated cache layer is beneficial for frequently accessed marketplace data.

### Alternative B: MongoDB as the Primary Database

**Advantages:**
- Flexible document model.
- Convenient storage of variable product attributes.
- Horizontal scaling capabilities.

**Disadvantages:**
- Relational marketplace data requires careful consistency management.
- Complex transactional relationships are less natural.
- Migration of existing business relationships may become complicated.

**Reason for rejection:**

The marketplace contains highly relational transactional entities such as orders, payments and inventory records.

### Alternative C: Hybrid PostgreSQL + Redis

**Advantages:**
- Reliable transactional persistence.
- Low-latency caching.
- Reduced repetitive database reads.
- Separation of transactional and temporary workloads.
- Flexible future optimization.

**Disadvantages:**
- Cache invalidation complexity.
- Additional infrastructure and monitoring.
- Risk of stale cached information.
- Requires explicit data ownership rules.

**Reason for selection:**

This strategy provides transactional reliability while improving performance for frequently accessed data.

## Consequences

### Positive Consequences

- PostgreSQL provides durable transactional storage.
- Redis improves frequently accessed read operations.
- Database workload can be reduced through caching.
- The system supports a clear authoritative data source.
- Asynchronous events reduce unnecessary synchronous coupling.

### Negative Consequences / Trade-offs

- Additional infrastructure components are required.
- Cache invalidation must be carefully designed.
- Eventual consistency may occur in asynchronous workflows.
- Database and cache monitoring becomes more complex.

## Consistency Strategy

- PostgreSQL is the authoritative source for orders and payments.
- Financial state changes must use transactional database operations.
- Inventory reservation must use concurrency-safe database logic.
- Cache entries must have explicit expiration and invalidation policies.
- Critical asynchronous events should use reliable publication mechanisms such as the transactional outbox pattern.

## Compliance and Validation

- Transaction consistency tests.
- Concurrent inventory reservation tests.
- Cache invalidation tests.
- Backup restoration tests.
- Database performance benchmarks.

## Related Requirements

- NFR-01: Time Behaviour.
- NFR-02: Capacity.
- NFR-03: Availability.
- NFR-04: Recoverability.
- NFR-11: Correctness.
