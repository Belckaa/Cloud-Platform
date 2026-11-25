# 3. Quality Attribute Scenarios
## E-Commerce Cloud Platform

## 3.1. Introduction

This document defines five Quality Attribute Scenarios (QAS) for the marketplace.

Each scenario follows the six mandatory elements:

1. Source
2. Stimulus
3. Environment
4. Artifact
5. Response
6. Response Measure

The scenarios translate abstract quality requirements into measurable system behavior.

---

## QAS-1. Performance Efficiency During Peak Traffic

**Related Requirement:** NFR-01, NFR-02

**Quality Attribute:** Performance Efficiency

- **Source:** Registered marketplace customers.
- **Stimulus:** A large number of customers simultaneously request product searches and submit order creation requests.
- **Environment:** Peak Load during a promotional campaign, with up to 3,000 incoming order requests per second and a separate catalog workload of 1,000 RPS.
- **Artifact:** API Gateway, Catalog Module, Order Module, Redis Cache and RabbitMQ.
- **Response:** The platform validates incoming requests, serves cached catalog information where appropriate, accepts valid order requests, persists order state and delegates asynchronous operations to the message broker. The client receives an appropriate response with an order identifier.
- **Response Measure:**
  - Catalog search p95 latency ≤ 150 ms at 1,000 RPS.
  - Order request HTTP 5xx error rate < 0.1%.
  - Accepted orders are not silently lost.
  - Resource utilization remains within configured infrastructure limits.

## QAS-2. Reliability During Infrastructure Failure

**Related Requirement:** NFR-03, NFR-04

**Quality Attribute:** Reliability

- **Source:** Infrastructure failure or unexpected termination of an application instance.
- **Stimulus:** One application instance becomes unavailable while customers are browsing products or placing orders.
- **Environment:** Normal operation with active customer traffic.
- **Artifact:** Kubernetes cluster, application replicas, API Gateway, PostgreSQL and message broker.
- **Response:** Kubernetes detects the failed instance and starts a replacement. Traffic is redirected to healthy instances. Persistent transaction data remains protected through database durability and backup mechanisms.
- **Response Measure:**
  - Monthly system availability ≥ 99.9%.
  - Recovery Time Objective ≤ 15 minutes.
  - Recovery Point Objective ≤ 5 minutes.
  - No silently lost accepted orders.

## QAS-3. Security Against Unauthorized Access

**Related Requirement:** NFR-05, NFR-06

**Quality Attribute:** Security

- **Source:** An unauthenticated or unauthorized external actor.
- **Stimulus:** The actor attempts to access protected customer information or modify an existing order without sufficient permissions.
- **Environment:** Normal production operation.
- **Artifact:** Identity Module, Keycloak, API Gateway, authorization middleware and audit logging subsystem.
- **Response:** The system validates the authentication token, verifies permissions and denies unauthorized operations. Security-relevant events are recorded in audit logs. Sensitive information is protected using encrypted communication and storage.
- **Response Measure:**
  - 100% of protected operations require authentication.
  - Unauthorized modifications are rejected.
  - Sensitive data is encrypted at rest using AES-256.
  - Communication uses TLS 1.3 where supported.
  - Security-relevant privileged operations are audited.

## QAS-4. Maintainability During Business Feature Changes

**Related Requirement:** NFR-07, NFR-12

**Quality Attribute:** Maintainability

- **Source:** Backend development team.
- **Stimulus:** Developers need to introduce a new discount calculation rule into the Order Module.
- **Environment:** Normal development and CI/CD pipeline execution.
- **Artifact:** Order Module, Domain Layer, Use Cases, module interfaces and automated testing infrastructure.
- **Response:** Developers modify the relevant business logic without accessing unrelated module internals. Automated unit, integration and architecture tests verify that the new functionality works correctly and that module boundaries remain intact.
- **Response Measure:**
  - Domain and Use Case unit-test coverage ≥ 80%.
  - All mandatory automated tests pass.
  - No prohibited cross-module dependencies are introduced.
  - Existing order-processing behavior remains correct.

## QAS-5. Scalability During Increasing Demand

**Related Requirement:** NFR-08

**Quality Attribute:** Flexibility / Scalability

- **Source:** Increasing customer traffic during a major promotional campaign.
- **Stimulus:** CPU utilization of application containers exceeds 70% continuously for two minutes.
- **Environment:** Peak Load with growing numbers of concurrent customer requests.
- **Artifact:** Kubernetes Horizontal Pod Autoscaler, application containers, API Gateway and monitoring infrastructure.
- **Response:** Monitoring identifies increased resource consumption. The Horizontal Pod Autoscaler increases the number of application replicas according to configured scaling policies. Traffic is distributed between available replicas.
- **Response Measure:**
  - Scaling is initiated after CPU exceeds 70% for two consecutive minutes.
  - New replicas become healthy and receive traffic.
  - Catalog search p95 latency remains within its defined target under the specified test workload.
  - No application data is lost during scaling.

---

## 3.2. Quality Scenario Traceability

| Scenario | Quality Attribute | Related NFR |
|---|---|---|
| QAS-1 | Performance Efficiency | NFR-01, NFR-02 |
| QAS-2 | Reliability | NFR-03, NFR-04 |
| QAS-3 | Security | NFR-05, NFR-06 |
| QAS-4 | Maintainability | NFR-07, NFR-12 |
| QAS-5 | Flexibility / Scalability | NFR-08 |

## 3.3. Conclusion

The five scenarios describe measurable responses to important business and technical events.

They provide a foundation for architecture evaluation, performance testing, security verification and future architectural decisions.
