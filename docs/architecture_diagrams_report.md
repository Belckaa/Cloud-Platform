# Лабораторна робота №2
## C4 Model та UML: статична структура і динамічна поведінка системи

**Дисципліна:** Проєктування архітектури, специфікація та стандарти інженерії<br>
**Тема проєкту:** E-Commerce Cloud Platform (High-Load Marketplace)<br>
**Виконав(ла):** О.В. Бєльченко<br>
**Група:** 3ПР1<br>
**Репозиторій:** https://github.com/Belckaa/Cloud-Platform<br>
**Git tag:** `lr2`

---

# 1. Мета роботи

Мета лабораторної роботи — описати архітектуру E-Commerce Cloud Platform за допомогою C4 Model та UML, побудувати діаграми статичної структури й динамічної поведінки, визначити протоколи взаємодії між контейнерами та перевірити можливість внесення змін до архітектурної документації.

# 2. Короткий опис системи

E-Commerce Cloud Platform — платформа електронної комерції для роботи з каталогом товарів, кошиком, замовленнями, оплатою та доставкою. Архітектура побудована як модульний моноліт із Clean Architecture. Такий підхід дає змогу розділити відповідальність між доменними модулями та адаптерами інфраструктури без штучного винесення кожного модуля в окремий мережевий сервіс.

Основний технологічний стек:
- Backend: Java 21, Spring Boot.
- Frontend: React, TypeScript.
- Основна база даних: PostgreSQL.
- Кешування: Redis.
- Асинхронний обмін повідомленнями: RabbitMQ.
- Ідентифікація та автентифікація: Keycloak.
- Контейнеризація й оркестрація: Docker, Kubernetes.
- Моніторинг: Prometheus, Grafana.

# 3. C4 Model

## 3.1. Level 1 — System Context

Діаграма System Context показує систему як єдине ціле та визначає її зовнішніх користувачів і системи, з якими вона взаємодіє.

Основні зовнішні актори та системи:
- Customer — покупець, який переглядає каталог і оформлює замовлення.
- Administrator — адміністратор платформи.
- Keycloak — система керування ідентифікацією та автентифікацією.
- Payment Gateway — зовнішній платіжний провайдер.
- Delivery Provider — зовнішній сервіс доставки.

Основні взаємодії з платформою виконуються через HTTPS. Для API використовується HTTPS/JSON, а для автентифікації — протоколи OIDC/OAuth2 поверх HTTPS.

![C4 Level 1 — System Context](diagrams/rendered/01_system_context.png)

## 3.2. Level 2 — Container Diagram

Container Diagram деталізує програмні контейнери системи та протоколи їх взаємодії.

Основні контейнери:
- Web Application — клієнтський інтерфейс React/TypeScript.
- Backend Application — серверна частина Java 21/Spring Boot.
- PostgreSQL — постійне реляційне сховище.
- Redis — кешування результатів і швидкий доступ до тимчасових даних.
- RabbitMQ — брокер асинхронних повідомлень.
- Keycloak — керування обліковими записами та токенами.
- Notification Service — окремий контейнер для асинхронної обробки подій сповіщень.
- Prometheus/Grafana — збір і візуалізація метрик.
- Payment Gateway і Delivery Provider — зовнішні системи.

Протоколи взаємодії:
- Web Application → Backend Application: HTTPS/JSON.
- Backend Application → Keycloak: HTTPS/JSON, OIDC.
- Backend Application → PostgreSQL: TCP/IP, порт 5432.
- Backend Application → Redis: TCP/IP, порт 6379.
- Backend Application → RabbitMQ: AMQP 0-9-1 поверх TCP/IP, порт 5672.
- RabbitMQ → Notification Service: AMQP 0-9-1 поверх TCP/IP, порт 5672.
- Backend Application → Payment Gateway і Delivery Provider: HTTPS/JSON.
- Prometheus → Backend Application: HTTP/TCP/IP для отримання метрик.

Оскільки основна Backend Application є модульним монолітом, внутрішня взаємодія її модулів не потребує окремого мережевого протоколу. Асинхронний обмін із Notification Service відбувається через RabbitMQ.

![C4 Level 2 — Container Diagram](diagrams/rendered/02_container_diagram.png)

# 4. UML Static Structure

## 4.1. UML Component Diagram

Діаграма компонентів деталізує Backend у контексті обробки замовлень.

Основні компоненти:
- Order REST Controller;
- Place Order Use Case;
- Order Query Use Case;
- Order Domain Service;
- Order Repository Adapter;
- Payment Adapter;
- Inventory Adapter;
- Event Publisher Adapter.

Provided interfaces описують функції, які компонент надає іншим компонентам. Required interfaces показують залежності, необхідні компоненту для роботи. У моделі розглядаються `IOrderAPI`, `IOrderQuery`, `IOrderRepository`, `IPaymentGateway`, `IInventoryService` та `IEventPublisher`.

Controller приймає зовнішні запити. Use Case координує бізнес-операцію, Domain Service реалізує доменні правила, а адаптери забезпечують взаємодію з інфраструктурою.

![UML Component Diagram](diagrams/rendered/03_backend_components.png)

## 4.2. UML Domain Class Diagram

Діаграма доменних класів моделює основні сутності електронної комерції:

| Клас | Призначення |
|---|---|
| Customer | Покупець |
| Product | Товар |
| Cart | Кошик |
| CartItem | Позиція кошика |
| Order | Замовлення |
| OrderItem | Позиція замовлення |
| Payment | Платіж |
| Shipment | Відправлення |

У діаграмі визначено атрибути, методи, типи даних, специфікатори видимості `+` (public), `-` (private), `#` (protected) та кратність зв'язків.

Композиція використана для зв'язків Cart—CartItem і Order—OrderItem, оскільки позиції є складовими відповідних об'єктів. Product існує незалежно від кошика або замовлення, тому зв'язок із ним подано як асоціацію. Order також пов'язаний із Payment та Shipment.

![UML Domain Class Diagram](diagrams/rendered/04_domain_class_diagram.png)

# 5. UML Dynamic Behavior

## 5.1. Sequence Diagram — Order Placement

Діаграма послідовності відображає оформлення замовлення. Учасники сценарію: Customer, Web Application, Backend API, Order Service, Inventory, Payment Gateway та Database.

Основна послідовність:
1. Користувач ініціює оформлення замовлення.
2. Backend перевіряє запит і токен автентифікації.
3. Система перевіряє наявність товарів.
4. Створюється замовлення.
5. Виконується авторизація платежу.
6. Результат операції зберігається.
7. Користувач отримує результат.

Використані оператори:
- `alt` — альтернативні результати перевірки токена, наявності товарів і платежу;
- `loop` — повторна обробка позицій замовлення;
- `opt` — необов'язкова операція перегляду деталей.

![Order Placement Sequence Diagram](diagrams/rendered/05_order_sequence.png)

## 5.2. Sequence Diagram — Authentication and Product Search

Діаграма демонструє пошук товарів і перевірку автентифікації під час доступу до захищеного ресурсу.

Пошук використовує Redis для перевірки кешу. Якщо результат є в кеші, система повертає його без звернення до бази даних. Якщо кеш не містить потрібних даних, Catalog виконує запит до PostgreSQL і зберігає результат у Redis.

Для захищеного сценарію перевіряється токен:
- за валідного токена Backend повертає `200 OK`;
- за недійсного або простроченого токена Backend повертає `401 Unauthorized`, після чого Web Application перенаправляє користувача на сторінку входу.

Також використано `opt` для необов'язкової нормалізації пошукового запиту та `loop` для повторного пошуку зі зміненими фільтрами.

![Authentication and Product Search Sequence Diagram](diagrams/rendered/06_auth_search_sequence.png)

# 6. UML State Machine Diagram

Діаграма станів описує життєвий цикл замовлення. Основні стани:
1. `Created` — замовлення створене.
2. `PendingPayment` — очікується оплата.
3. `Confirmed` — оплату підтверджено.
4. `Processing` — замовлення обробляється.
5. `Shipped` — замовлення відправлене.
6. `Delivered` — замовлення доставлене.
7. `Cancelled` — замовлення скасоване.
8. `Refunded` — кошти повернені.

Переходи між станами визначаються результатом оплати, наявністю товарів, виконанням операцій обробки та доставкою. Діаграма дає змогу перевірити допустимі переходи та уникати некоректних змін стану замовлення.

![Order State Machine Diagram](diagrams/rendered/07_order_state_machine.png)

# 7. Модифікаційне тестування

## 7.1. Додавання Notification Service

До C4 Container Diagram додано окремий контейнер Notification Service на базі Java 21 та Spring Boot. Сервіс отримує події сповіщень через RabbitMQ. Для передавання повідомлень використовується AMQP 0-9-1 поверх TCP/IP, порт 5672.

Це дає змогу обробляти сповіщення асинхронно та зменшує залежність основного процесу оформлення замовлення від операцій надсилання повідомлень.

## 7.2. Альтернативна гілка 401 Unauthorized

У Sequence Diagram автентифікації передбачено альтернативну гілку для недійсного або простроченого токена. Якщо токен валідний, Backend API повертає `200 OK`. Якщо токен недійсний або прострочений, Backend API повертає `401 Unauthorized`, а Web Application перенаправляє користувача на сторінку входу. Альтернативні сценарії оформлено оператором `alt`.

## 7.3. Результати перевірки

| Перевірка | Результат |
|---|---|
| Додавання Notification Service до C4 Container Diagram | Виконано |
| Зв'язок RabbitMQ → Notification Service | Виконано |
| Вказання AMQP 0-9-1, TCP/IP, порт 5672 | Виконано |
| Альтернативна гілка `401 Unauthorized` | Перевірено у вихідному коді |
| Повторний рендеринг діаграм | Виконано скриптом |

# 8. Відповіді на контрольні питання

## 8.1. У чому полягає різниця між C4 Model та UML?

C4 Model — підхід до опису архітектури програмної системи на кількох рівнях абстракції: System Context, Container, Component та Code. UML — стандартизована мова моделювання, яка містить різні типи діаграм для опису структури та поведінки системи. C4 допомагає послідовно пояснити архітектуру від загального контексту до внутрішньої структури, а UML надає формальні засоби моделювання класів, компонентів, взаємодій і станів.

## 8.2. Чим відрізняється синхронна REST/gRPC взаємодія від асинхронної AMQP/Kafka?

REST і gRPC зазвичай використовуються для синхронної взаємодії: клієнт надсилає запит і очікує відповідь. AMQP та Kafka підтримують асинхронну взаємодію через обмін повідомленнями або подіями. Відправник не повинен очікувати завершення обробки повідомлення отримувачем. У цій архітектурі HTTPS/JSON використовується для API-запитів, а RabbitMQ та AMQP — для асинхронних подій.

## 8.3. Що означають Provided та Required Interfaces у UML Component Diagram?

Provided Interface — інтерфейс, який компонент реалізує та надає іншим компонентам. Required Interface — інтерфейс, який компонент використовує та потребує для своєї роботи. У стандартній нотації ball позначає наданий інтерфейс, а socket — необхідний інтерфейс.

## 8.4. Чим відрізняється агрегація від композиції?

Агрегація — слабкий зв'язок «ціле—частина», за якого частина може існувати незалежно від цілого. Композиція — сильний зв'язок, за якого життєвий цикл частини залежить від життєвого циклу цілого. Наприклад, OrderItem належить конкретному Order і моделюється композицією. Product існує незалежно від замовлення, тому зв'язок із ним є асоціацією.

## 8.5. У чому різниця між операторами alt, opt та loop?

- `alt` — задає альтернативні гілки виконання залежно від умови.
- `opt` — описує необов'язковий фрагмент, який виконується за певної умови.
- `loop` — задає повторення фрагмента взаємодії.

У роботі `alt` використовується для результатів автентифікації та оплати, `opt` — для необов'язкових операцій, а `loop` — для обробки позицій замовлення та повторного пошуку.

# 9. Висновки

У результаті лабораторної роботи побудовано архітектурне представлення E-Commerce Cloud Platform із використанням C4 Model та UML. Створено діаграми System Context, Container, Component, Domain Class, дві Sequence Diagram і State Machine. Описано контейнери системи, технології та протоколи взаємодії, компоненти Backend, доменні сутності й поведінку замовлення.

Виконано модифікаційні перевірки: додано Notification Service до C4 Container Diagram та перевірено альтернативну гілку `401 Unauthorized` у Sequence Diagram. Діаграми створені як код PlantUML, що дає змогу зберігати їх у системі контролю версій і повторно генерувати зображення після змін.

# 10. Структура матеріалів

```text
docs/
├── architecture_diagrams_report.md
└── diagrams/
    ├── 01_system_context.puml
    ├── 02_container_diagram.puml
    ├── 03_backend_components.puml
    ├── 04_domain_class_diagram.puml
    ├── 05_order_sequence.puml
    ├── 06_auth_search_sequence.puml
    ├── 07_order_state_machine.puml
    └── rendered/
        ├── 01_system_context.png
        ├── 02_container_diagram.png
        ├── 03_backend_components.png
        ├── 04_domain_class_diagram.png
        ├── 05_order_sequence.png
        ├── 06_auth_search_sequence.png
        └── 07_order_state_machine.png
```

# ДОДАТОК А. Вихідний код PlantUML

Нижче наведено повний вихідний код семи діаграм. Джерелом є відповідні файли каталогу `docs/diagrams/`.

## 01_system_context.puml

```plantuml
@startuml 01_system_context
!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml

title C4 Level 1 - System Context
LAYOUT_WITH_LEGEND()

Person(customer, "Customer", "Browses products, manages cart and places orders")
Person(admin, "Administrator", "Manages catalog, orders and marketplace operations")

System(platform, "E-Commerce Cloud Platform", "High-load marketplace for product discovery, shopping, order processing and delivery coordination")

System_Ext(keycloak, "Keycloak Identity Provider", "Authentication and identity management")
System_Ext(payment, "External Payment Gateway", "Processes card and online payments")
System_Ext(delivery, "External Delivery Provider", "Shipment creation and delivery tracking")

Rel(customer, platform, "Browses products, manages cart, places orders", "HTTPS")
Rel(admin, platform, "Manages catalog and orders", "HTTPS")
Rel(platform, keycloak, "Authenticates users and validates tokens", "OIDC / OAuth 2.0 over HTTPS")
Rel(platform, payment, "Creates and verifies payments", "HTTPS/JSON")
Rel(platform, delivery, "Creates shipments and receives tracking updates", "HTTPS/JSON")

SHOW_LEGEND()
@enduml
```

## 02_container_diagram.puml

```plantuml
@startuml 02_container_diagram
!includeurl https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml

title C4 Level 2 - Container Diagram
LAYOUT_WITH_LEGEND()

Person(customer, "Customer", "Marketplace customer")
Person(admin, "Administrator", "Marketplace administrator")

System_Boundary(platform, "E-Commerce Cloud Platform") {
    Container(web, "Web Application", "React, TypeScript", "Customer and administrator user interface")
    Container(api, "Backend Application", "Java 21, Spring Boot", "Modular monolith implementing catalog, cart, order, payment and shipment use cases")
    ContainerDb(db, "Main Database", "PostgreSQL", "Users references, products, carts, orders, payments and shipments")
    ContainerDb(cache, "Distributed Cache", "Redis", "Product cache, sessions, rate-limit counters and temporary data")
    ContainerQueue(broker, "Message Broker", "RabbitMQ", "Asynchronous domain events and background processing")
    Container(notification, "Notification Service", "Java 21, Spring Boot", "Consumes notification events and processes customer notifications")
}

System_Ext(identity, "Keycloak", "Identity Provider")
System_Ext(payment, "Payment Gateway", "External payment processing")
System_Ext(delivery, "Delivery Provider", "External shipment and tracking")
System_Ext(monitoring, "Prometheus / Grafana", "Metrics collection and visualization")

Rel(customer, web, "Uses marketplace UI", "HTTPS")
Rel(admin, web, "Uses administration UI", "HTTPS")
Rel(web, api, "REST API requests", "HTTPS/JSON")
Rel(api, identity, "OIDC authentication, token validation", "HTTPS/JSON")
Rel(api, db, "Reads and writes persistent data", "TCP/IP - PostgreSQL protocol, port 5432")
Rel(api, cache, "Cache operations", "TCP/IP - Redis protocol, port 6379")
Rel(api, broker, "Publishes and consumes domain events", "AMQP 0-9-1, TCP/IP port 5672")
Rel(broker, notification, "Delivers notification events", "AMQP 0-9-1, TCP/IP port 5672")
Rel(api, payment, "Payment authorization and status queries", "HTTPS/JSON")
Rel(api, delivery, "Shipment creation and tracking", "HTTPS/JSON")
Rel(monitoring, api, "Scrapes application metrics", "HTTP, TCP/IP")

SHOW_LEGEND()
@enduml
```

## 03_backend_components.puml

```plantuml
@startuml 03_backend_components
title UML Component Diagram - Backend Order Module
skinparam componentStyle uml
skinparam linetype ortho

package "Backend Application - Order Module" {
    component "Order REST Controller" as Controller
    component "Place Order Use Case" as PlaceOrder
    component "Order Query Use Case" as QueryOrder
    component "Order Domain Service" as DomainService
    component "Order Repository Adapter" as Repository
    component "Payment Adapter" as PaymentAdapter
    component "Inventory Adapter" as InventoryAdapter
    component "Event Publisher Adapter" as EventPublisher
}

() "IOrderAPI\nProvided interface" as IOrderAPI
() "IOrderQuery\nProvided interface" as IOrderQuery
() "IOrderRepository\nRequired interface" as IOrderRepository
() "IPaymentGateway\nRequired interface" as IPaymentGateway
() "IInventoryService\nRequired interface" as IInventoryService
() "IEventPublisher\nRequired interface" as IEventPublisher

Controller -- IOrderAPI : provides
Controller -- IOrderQuery : provides

Controller ..> PlaceOrder : invokes
Controller ..> QueryOrder : invokes
PlaceOrder ..> DomainService : uses
QueryOrder ..> DomainService : uses

PlaceOrder ..> IOrderRepository : requires
PlaceOrder ..> IPaymentGateway : requires
PlaceOrder ..> IInventoryService : requires
PlaceOrder ..> IEventPublisher : requires

Repository -- IOrderRepository : implements
PaymentAdapter -- IPaymentGateway : implements
InventoryAdapter -- IInventoryService : implements
EventPublisher -- IEventPublisher : implements

note right of IOrderAPI
Provided interface (ball):
the component exposes
order operations to clients.
end note

note right of IOrderRepository
Required interface (socket):
the use case needs persistence
through this abstraction.
end note

note bottom
The application follows Clean Architecture:
domain and use cases do not depend on infrastructure details.
Adapters implement required ports.
end note
@enduml
```

## 04_domain_class_diagram.puml

```plantuml
@startuml 04_domain_class_diagram
title UML Domain Class Diagram - E-Commerce Marketplace
skinparam classAttributeIconSize 0
hide empty members

class Customer {
  -id: UUID
  -email: String
  -fullName: String
  -phone: String
  +updateProfile(name: String, phone: String): void
  +getOrders(): List<Order>
}

class Product {
  -id: UUID
  -sku: String
  -name: String
  -description: String
  -price: BigDecimal
  -availableQuantity: Integer
  +changePrice(price: BigDecimal): void
  +isAvailable(quantity: Integer): Boolean
  +reserve(quantity: Integer): Boolean
}

class Cart {
  -id: UUID
  -createdAt: Instant
  -updatedAt: Instant
  +addItem(product: Product, quantity: Integer): void
  +removeItem(productId: UUID): void
  +calculateTotal(): BigDecimal
  +clear(): void
}

class CartItem {
  -id: UUID
  -quantity: Integer
  -unitPrice: BigDecimal
  +changeQuantity(quantity: Integer): void
  +calculateSubtotal(): BigDecimal
}

class Order {
  -id: UUID
  -number: String
  -status: OrderStatus
  -totalAmount: BigDecimal
  -createdAt: Instant
  +place(): void
  +confirm(): void
  +cancel(reason: String): void
  +markAsShipped(): void
  +complete(): void
}

class OrderItem {
  -id: UUID
  -productId: UUID
  -productName: String
  -quantity: Integer
  -unitPrice: BigDecimal
  +calculateSubtotal(): BigDecimal
}

class Payment {
  -id: UUID
  -amount: BigDecimal
  -status: PaymentStatus
  -transactionReference: String
  -createdAt: Instant
  +authorize(): Boolean
  +capture(): Boolean
  +refund(): Boolean
}

class Shipment {
  -id: UUID
  -trackingNumber: String
  -status: ShipmentStatus
  -carrier: String
  -shippedAt: Instant
  +create(): void
  +updateTracking(status: ShipmentStatus): void
  +markDelivered(): void
}

enum OrderStatus {
  CREATED
  PENDING_PAYMENT
  CONFIRMED
  PROCESSING
  SHIPPED
  DELIVERED
  CANCELLED
  REFUNDED
}

enum PaymentStatus {
  PENDING
  AUTHORIZED
  CAPTURED
  FAILED
  REFUNDED
}

enum ShipmentStatus {
  CREATED
  IN_TRANSIT
  DELIVERED
  FAILED
}

Customer "1" -- "0..1" Cart : owns
Customer "1" -- "0..*" Order : places
Cart "1" *-- "0..*" CartItem : composition
CartItem "0..*" --> "1" Product : references
Order "1" *-- "1..*" OrderItem : composition
OrderItem "0..*" --> "1" Product : references
Order "1" *-- "0..1" Payment : composition
Order "1" *-- "0..1" Shipment : composition
Order --> OrderStatus
Payment --> PaymentStatus
Shipment --> ShipmentStatus

note bottom
Composition is used where the child object's lifecycle
is owned by its parent aggregate. Product is referenced
by cart and order items; it is not owned by them.
OrderItem stores product name and unit price snapshots.
end note
@enduml
```

## 05_order_sequence.puml

```plantuml
@startuml 05_order_sequence
title UML Sequence Diagram - Order Placement
autonumber

actor Customer
participant "Web Application" as Web
participant "Backend API" as API
participant "Keycloak" as Auth
participant "Order Service" as Order
participant "Inventory Adapter" as Inventory
participant "Payment Adapter" as Payment
database "PostgreSQL" as DB
queue "RabbitMQ" as MQ

Customer -> Web: Submit checkout
Web -> API: POST /api/orders (HTTPS/JSON)
API -> Auth: Validate access token (OIDC)

alt Token is valid
    Auth --> API: Authentication successful
    API -> Order: placeOrder(customerId, cart)

    loop For each order item
        Order -> Inventory: reserve(productId, quantity)
        Inventory --> Order: Reservation result
    end

    alt All items are available
        Order -> DB: Save order with PENDING_PAYMENT
        DB --> Order: Order persisted

        Order -> Payment: authorizePayment(orderId, amount)
        alt Payment authorized
            Payment --> Order: Authorization successful
            Order -> DB: Update order to CONFIRMED
            DB --> Order: Update successful
            Order -> MQ: Publish OrderConfirmed (AMQP)
            Order --> API: Order confirmation
            API --> Web: 201 Created
            Web --> Customer: Show order confirmation
        else Payment declined
            Payment --> Order: Payment declined
            Order -> DB: Mark payment FAILED
            Order -> Inventory: Release reservations
            Order --> API: Payment failure
            API --> Web: 402 Payment Required
            Web --> Customer: Show payment error
        end
    else At least one item unavailable
        Order -> Inventory: Release successful reservations
        Order --> API: Inventory conflict
        API --> Web: 409 Conflict
        Web --> Customer: Show unavailable item
    end

else Token is invalid
    Auth --> API: Unauthorized
    API --> Web: 401 Unauthorized
    Web --> Customer: Request login
end

opt Customer requests order details after checkout
    Web -> API: GET /api/orders/{id}
    API -> Order: getOrder(orderId)
    Order -> DB: Load order
    DB --> Order: Order data
    Order --> API: Order details
    API --> Web: 200 OK
end
@enduml
```

## 06_auth_search_sequence.puml

```plantuml
@startuml 06_auth_search_sequence
title UML Sequence Diagram - Authentication and Product Search
autonumber

actor Customer
participant "Web Application" as Web
participant "Backend API" as API
participant "Keycloak" as Auth
participant "Catalog Service" as Catalog
database "Redis Cache" as Cache
database "PostgreSQL" as DB

Customer -> Web: Open marketplace
Web -> API: GET /api/products
API -> Catalog: searchProducts(query, filters)

opt Search query is not empty
    Catalog -> Catalog: Normalize query and filters
end

Catalog -> Cache: GET products:{query}
alt Cache hit
    Cache --> Catalog: Cached product list
else Cache miss
    Catalog -> DB: SELECT products with filters
    DB --> Catalog: Product records
    Catalog -> Cache: SET products:{query}, TTL
end

Catalog --> API: Product search results
API --> Web: 200 OK (HTTPS/JSON)
Web --> Customer: Display products

Customer -> Web: Open protected account page
Web -> API: GET /api/account (Bearer token)
API -> Auth: Validate token

alt Token is valid
    Auth --> API: User identity and roles
    API --> Web: 200 OK (account data)
    Web --> Customer: Display account
else Token expired or invalid
    Auth --> API: Token validation failed
    API --> Web: 401 Unauthorized
    Web --> Customer: Redirect to login
end

loop While customer changes search filters
    Customer -> Web: Change filter or sort order
    Web -> API: GET /api/products with parameters
    API -> Catalog: searchProducts(query, filters)
    Catalog -> DB: Execute filtered search
    DB --> Catalog: Matching products
    Catalog --> API: Updated result page
    API --> Web: 200 OK
    Web --> Customer: Refresh product list
end
@enduml
```

## 07_order_state_machine.puml

```plantuml
@startuml 07_order_state_machine
title UML State Machine Diagram - Order Lifecycle

[*] --> Created : createOrder()

Created --> PendingPayment : submit()
PendingPayment --> Confirmed : paymentAuthorized()
PendingPayment --> Cancelled : paymentDeclined()
PendingPayment --> Cancelled : cancelByCustomer()

Confirmed --> Processing : startFulfillment()
Processing --> Shipped : shipmentCreated()
Shipped --> Delivered : deliveryConfirmed()

Created --> Cancelled : cancel()
Confirmed --> Cancelled : cancelBeforeShipment()
Processing --> Cancelled : cancelIfNotDispatched()

Delivered --> [*]
Cancelled --> [*]

Confirmed --> Refunded : refundApproved()
Delivered --> Refunded : returnAccepted()
Refunded --> [*]

note right of PendingPayment
Payment authorization is required
before order confirmation.
end note

note right of Shipped
After shipment dispatch,
cancellation is restricted.
end note

note bottom
The state machine models the lifecycle of the Order aggregate.
Transitions are triggered by business events and validated
by domain rules.
end note
@enduml
```
