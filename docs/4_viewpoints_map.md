# 4. Architecture Viewpoints Map
## ISO/IEC/IEEE 42010:2022

## 4.1. Introduction

This document defines the architectural viewpoints and corresponding views for E-Commerce Cloud Platform.

An Architecture Viewpoint establishes conventions, concerns, stakeholders and modeling methods used to construct an architectural view.

An Architecture View is a concrete representation of the system from a particular viewpoint.

## 4.2. Architecture Viewpoints Map

| Viewpoint | View / Artifact | Notation / Modeling Method | Stakeholders | Main Concerns |
|---|---|---|---|---|
| Context Viewpoint | System Context Diagram | C4 Level 1 | Business Owner, End User, System Integrator | System boundaries and external actors |
| Container Viewpoint | Container Diagram | C4 Level 2 | Software Architect, Lead Developer, DevOps | Application containers and infrastructure |
| Structural Viewpoint | Component Diagram | UML 2.5 | Developers, Module Owners | Module boundaries and dependencies |
| Behavioral Viewpoint | Sequence Diagrams | UML 2.5 | Backend Developers, QA Engineers | Order processing and payment interactions |
| Data Viewpoint | Entity Relationship Diagram | ERD / Crow's Foot | Database Engineer, Developers | Data structure and consistency |
| Deployment Viewpoint | Deployment Diagram | UML 2.5 / C4 | DevOps, Infrastructure Engineers | Deployment topology and availability |
| Security Viewpoint | Security Architecture Diagram | UML / Data Flow Diagram | Security Officer, Compliance Team | Authentication, authorization and data protection |
| Operational Viewpoint | Monitoring Architecture | Component Diagram | DevOps, SysAdmin | Observability, alerting and recovery |

## 4.3. Context Viewpoint

**Purpose:** Describe the marketplace boundary and its external interactions.

**Primary elements:**
- Customer.
- Administrator.
- E-Commerce Cloud Platform.
- External Payment Provider.
- Warehouse System.
- Notification Provider.

**Main concerns:**
- External system dependencies.
- Business boundaries.
- Integration responsibilities.

## 4.4. Container Viewpoint

**Purpose:** Describe the high-level runtime and deployment structure.

**Primary elements:**
- React Web Application.
- Backend Application.
- PostgreSQL Database.
- Redis Cache.
- RabbitMQ Message Broker.
- Keycloak Identity Provider.
- External Payment API.

**Main concerns:**
- Runtime communication.
- Technology selection.
- Deployment responsibilities.
- Performance and scalability.

## 4.5. Structural Viewpoint

**Purpose:** Describe internal application modularity.

**Primary modules:**
- Identity.
- Catalog.
- Cart.
- Order.
- Payment.
- Inventory.
- Notification.

**Main concerns:**
- Encapsulation.
- Module ownership.
- Dependency direction.
- Low coupling and high cohesion.

## 4.6. Behavioral Viewpoint

**Purpose:** Describe dynamic system behavior.

**Key scenarios:**
1. Customer authentication.
2. Product search.
3. Shopping cart modification.
4. Order creation.
5. Payment processing.
6. Inventory reservation.
7. Order confirmation notification.

**Main concerns:**
- Request processing.
- Event sequencing.
- Error handling.
- Transaction consistency.

## 4.7. Data Viewpoint

**Purpose:** Describe persistence structures and relationships.

**Primary entities:**
- Customer.
- Product.
- Category.
- ShoppingCart.
- CartItem.
- Order.
- OrderItem.
- Payment.
- InventoryItem.

**Main concerns:**
- Referential integrity.
- Transactional consistency.
- Data ownership.
- Sensitive data protection.

## 4.8. Deployment Viewpoint

**Purpose:** Describe the physical and logical deployment environment.

**Primary elements:**
- Cloud infrastructure.
- Kubernetes cluster.
- Application Pods.
- Database infrastructure.
- Cache infrastructure.
- Message broker.
- Monitoring services.

**Main concerns:**
- Availability.
- Horizontal scaling.
- Resource utilization.
- Recovery and deployment automation.

## 4.9. Security Viewpoint

**Purpose:** Describe the security architecture and protection mechanisms.

**Primary elements:**
- Keycloak.
- OAuth 2.0.
- OpenID Connect.
- JWT access tokens.
- TLS 1.3.
- AES-256 encryption.
- Role-Based Access Control.
- Audit logging.

**Main concerns:**
- Confidentiality.
- Integrity.
- Authenticity.
- Accountability.
- Access control.

## 4.10. Operational Viewpoint

**Purpose:** Describe how the system is monitored and operated.

**Primary elements:**
- Prometheus.
- Grafana.
- Centralized application logs.
- Health checks.
- Alerting.
- CI/CD pipelines.

**Main concerns:**
- Incident detection.
- Performance monitoring.
- Operational visibility.
- Automated recovery.

## 4.11. Stakeholder-to-Viewpoint Mapping

| Stakeholder | Relevant Viewpoints |
|---|---|
| Business Owner | Context, Container, Operational |
| End User | Context, Behavioral |
| Software Architect | Context, Container, Structural, Behavioral |
| Backend Developer | Structural, Behavioral, Data |
| Database Engineer | Data, Deployment |
| DevOps Engineer | Container, Deployment, Operational |
| Security Officer | Security, Container, Data |
| QA Engineer | Behavioral, Structural, Operational |
| System Integrator | Context, Container, Behavioral |

## 4.12. Conclusion

The defined viewpoints provide structured representations of the marketplace for different stakeholder concerns.

They establish the documentation foundation for the Architectural Design Document (ADD).
