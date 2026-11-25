# E-Commerce Cloud Platform
## High-Load Marketplace

**Laboratory Work №1:** Architectural Requirements Specification, NFR Analysis, Quality Attribute Scenarios and ADR Registry.

**Discipline:** Software Architecture Design and Specification  
**Specialty:** 121 — Software Engineering  
**Project Variant:** 1 — E-Commerce Cloud Platform

---

## 1. Project Overview

E-Commerce Cloud Platform is a cloud-native online marketplace designed to support high-volume product browsing, shopping cart operations, order processing, payment integration, inventory management and customer notifications.

The platform aims to provide reliable, secure and scalable online shopping services under normal and peak-load conditions.

## 2. Business Goals

- Provide a convenient online shopping experience.
- Support high-volume concurrent customer traffic.
- Ensure secure payment processing.
- Maintain accurate product inventory.
- Guarantee reliable order processing.
- Support horizontal infrastructure scaling.
- Minimize service downtime.
- Enable future expansion of the platform.

## 3. Core Business Modules

| Module | Responsibility |
|---|---|
| Catalog | Product information, categories and search |
| Cart | Shopping cart management |
| Order | Order creation and lifecycle management |
| Payment | Payment provider integration |
| Inventory | Stock availability and reservation |
| Notification | Email and order status notifications |
| Identity | Authentication and authorization |

## 4. Technology Direction

The initial architecture is based on a Modular Monolith with Clean Architecture principles.

Proposed technology stack:

- Backend: Java 21 / Spring Boot
- Frontend: React / TypeScript
- Database: PostgreSQL
- Cache: Redis
- Message Broker: RabbitMQ
- Authentication: Keycloak / OAuth 2.0 / OpenID Connect
- Containerization: Docker
- Orchestration: Kubernetes
- Monitoring: Prometheus / Grafana
- API: REST / OpenAPI

These technologies represent the proposed architectural baseline and may evolve through subsequent ADRs.

## 5. Repository Structure

```text
ECommerce-Cloud-Platform/
├── README.md
└── docs/
    ├── 1_system_description.md
    ├── 2_nfr_specification.md
    ├── 3_quality_scenarios.md
    ├── 4_viewpoints_map.md
    └── adrs/
        ├── 0001-architectural-style-selection.md
        ├── 0002-database-and-storage-strategy.md
        └── 0003-authentication-and-security-framework.md
```

## 6. Laboratory Deliverables

- System description and stakeholder matrix.
- NFR specification based on ISO/IEC 25010:2023.
- Five Quality Attribute Scenarios.
- Three Architecture Decision Records.
- Architecture Viewpoints Map based on ISO/IEC/IEEE 42010:2022.

## 7. Architectural Principles

1. Separation of concerns.
2. High cohesion and low coupling.
3. Security by design.
4. Horizontal scalability.
5. Observability.
6. Automated testing.
7. Fault tolerance.
8. Maintainability.
