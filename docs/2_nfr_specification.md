# 2. Non-Functional Requirements Specification
## ISO/IEC 25010:2023

## 2.1. Introduction

This document defines the non-functional requirements of E-Commerce Cloud Platform.

The requirements are based on the eight product quality characteristics defined in ISO/IEC 25010:2023.

Each requirement includes a measurable indicator or verification criterion.

## 2.2. NFR Specification

| ID | ISO Quality Characteristic | Subcharacteristic | Requirement | Quantitative Indicator |
|---|---|---|---|---|
| NFR-01 | Performance Efficiency | Time Behaviour | Product catalog and search operations must respond quickly under normal and peak traffic. | p95 latency ≤ 150 ms at 1,000 RPS |
| NFR-02 | Performance Efficiency | Capacity | The platform must process high volumes of order creation requests. | 3,000 incoming order requests per second during the defined peak-load test; 5xx error rate < 0.1% |
| NFR-03 | Reliability | Availability | Customer-facing marketplace functionality must remain available. | Monthly availability ≥ 99.9% |
| NFR-04 | Reliability | Recoverability | The system must recover after infrastructure or application failures. | RTO ≤ 15 minutes; RPO ≤ 5 minutes |
| NFR-05 | Security | Confidentiality | Sensitive customer information and payment tokens must be protected. | AES-256 encryption at rest; TLS 1.3 in transit |
| NFR-06 | Security | Integrity | Unauthorized modifications of order and payment records must be prevented. | 100% of privileged write operations authenticated and audited |
| NFR-07 | Maintainability | Testability | Core business logic must be covered by automated unit tests. | Domain and Use Case unit-test coverage ≥ 80% |
| NFR-08 | Flexibility | Scalability | Application capacity must increase automatically when demand grows. | Horizontal scaling initiated when CPU > 70% for 2 consecutive minutes |
| NFR-09 | Compatibility | Interoperability | The platform must exchange information with external payment and warehouse systems. | 100% of external integrations use documented API contracts |
| NFR-10 | Interaction Capability | Learnability | Customers must be able to complete a standard checkout flow without unnecessary complexity. | At least 90% successful completion in usability tests with representative users |
| NFR-11 | Functional Suitability | Correctness | Order totals and inventory reservations must be calculated correctly. | 100% consistency in defined automated financial and inventory validation tests |
| NFR-12 | Maintainability | Modifiability | Independent business modules must be modifiable without unintended changes to unrelated modules. | All module dependency rules pass automated architecture tests |

## 2.3. Detailed Requirement Definitions

### NFR-01: Search Performance

The system must provide efficient catalog browsing and product search.

Measurement:
- p95 response time must not exceed 150 ms.
- Load test must sustain 1,000 RPS.
- The measurement must exclude external client network latency.

Verification: Load testing using k6 or an equivalent tool.

### NFR-02: Order Processing Capacity

The order processing functionality must support peak traffic.

Measurement:
- 3,000 incoming order requests per second during the defined peak-load test.
- HTTP 5xx error rate below 0.1%.
- Accepted orders must not be silently lost.
- Asynchronous processing must be observable.

Verification: Load testing and reconciliation of accepted orders against persisted orders.

### NFR-03: Availability

The marketplace must provide continuous access to essential customer operations.

Measurement:
- Monthly availability of at least 99.9%.
- Planned maintenance must be tracked separately.
- Availability must be measured using successful external synthetic requests.

Verification: Monitoring and availability reports.

### NFR-04: Recoverability

The system must recover after infrastructure failures.

Measurement:
- Recovery Time Objective (RTO) ≤ 15 minutes.
- Recovery Point Objective (RPO) ≤ 5 minutes.

Verification: Backup restoration and disaster recovery exercises.

### NFR-05: Confidentiality

Sensitive information must be protected during storage and transmission.

Measurement:
- AES-256 encryption at rest for sensitive stored data.
- TLS 1.3 for external and internal communication where supported.
- Raw payment card data must not be stored by the marketplace.

Verification: Security configuration inspection and penetration testing.

### NFR-06: Integrity

Order and payment-related records must be protected against unauthorized changes.

Measurement:
- Authentication and authorization for all privileged write operations.
- Audit logging for administrative changes.
- Transaction consistency checks for critical business operations.

Verification: Security tests and audit-log inspection.

### NFR-07: Testability

The application must support automated verification of core business behavior.

Measurement:
- At least 80% unit-test coverage for Domain and Use Case layers.
- Critical order and payment flows must have integration tests.

Verification: Automated CI pipeline reports.

### NFR-08: Scalability

The platform must automatically adapt application capacity to increased demand.

Measurement:
- Horizontal Pod Autoscaler activation at CPU utilization above 70% for two consecutive minutes.
- Scaling must not violate defined service performance objectives.

Verification: Kubernetes load and scaling tests.

### NFR-09: Interoperability

External integrations must use documented contracts.

Measurement:
- OpenAPI specifications for REST APIs.
- Versioned integration contracts.
- Defined timeout and retry policies.

Verification: API contract tests.

### NFR-10: Learnability

The checkout process must be understandable to new customers.

Measurement:
- At least 90% successful completion in representative usability tests.
- Standard checkout flow must not require unnecessary navigation.

Verification: Usability testing.

### NFR-11: Correctness

Financial calculations and inventory reservations must be correct.

Measurement:
- No incorrect totals in automated financial calculation tests.
- No overselling in defined concurrent inventory reservation tests.

Verification: Automated unit, integration and concurrency tests.

### NFR-12: Modifiability

The system must preserve module isolation.

Measurement:
- Automated architecture tests must verify allowed dependencies.
- Cross-module access must occur through public module interfaces.

Verification: Architecture tests in CI.

## 2.4. Requirement Prioritization

| Priority | Requirements | Reason |
|---|---|---|
| Critical | NFR-02, NFR-03, NFR-05, NFR-06 | Business continuity, traffic handling and data protection |
| High | NFR-01, NFR-04, NFR-07, NFR-08 | Performance, recovery and evolution |
| Medium | NFR-09, NFR-10, NFR-11, NFR-12 | Integration, usability and maintainability |

## 2.5. Verification Strategy

Requirements will be verified using:

- Load and stress testing.
- Automated unit and integration testing.
- Security testing.
- Monitoring and availability reports.
- Backup restoration exercises.
- API contract testing.
- Architecture dependency tests.
- Usability testing.

## 2.6. Conclusion

The defined NFRs establish measurable quality targets for the marketplace and provide architectural drivers for subsequent design decisions.
