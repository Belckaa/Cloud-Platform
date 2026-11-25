# Звіт про виконання лабораторної роботи №4

## Тема: Модульне ядро, Clean Architecture та Dependency Injection

**Дисципліна:** Проєктування архітектури, специфікація та стандарти інженерії  
**Студент:** О.В. Бєльченко  
**Група:** 3ПР1  
**Викладач:** Матвійчук Олексій Вадимович  
**Навчальний заклад:** Херсонський національний технічний університет (ХНТУ)  
**Тег у репозиторії:** lr4  
**Посилання на репозиторій:** https://github.com/Belckaa/Cloud-Platform

## 1. Шари та правило залежностей

Для наскрізного проєкту E-Commerce Cloud Platform реалізовано чотири шари відповідно до принципів Clean Architecture.

### Domain

Розташування: `src/Domain`

Містить:
- `Order` — доменну сутність замовлення з унікальним ідентифікатором;
- `OrderItem` — позицію замовлення;
- `Address` — value object адреси доставки;
- `OrderStatus` — перелік станів замовлення;
- `OrderRepository` — інтерфейс репозиторію.

Доменна сутність відповідає за перевірку базових правил: наявність клієнта, хоча б однієї позиції, коректність адреси, кількості та ціни товару. Загальна сума обчислюється на основі позицій замовлення.

Domain не використовує Spring, HTTP, ORM, SQL або класи Infrastructure.

### Application

Розташування: `src/Application`

Містить:
- `CreateOrderCommand` — DTO команди створення замовлення;
- `CreateOrderUseCase` — сценарій створення замовлення;
- `InventoryAvailabilityPort` — інтерфейс перевірки доступності товару;
- `InsufficientInventoryException` — виняток недостатньої кількості товару.

Application залежить від Domain та абстракцій портів. Сценарій отримує всі залежності через конструктор. Створення адаптерів усередині сценарію відсутнє.

### Infrastructure

Розташування: `src/Infrastructure`

Містить:
- `InMemoryOrderRepository` — демонстраційну реалізацію репозиторію;
- `InMemoryInventoryAvailabilityAdapter` — адаптер перевірки доступності товарів.

Ці класи реалізують інтерфейси внутрішніх шарів. Для лабораторної роботи використано in-memory реалізації без підключення бази даних.

### WebAPI

Розташування: `src/WebAPI`

Містить:
- `CreateOrderController` — REST-контролер для створення замовлення;
- `ApplicationConfiguration` — композиційний корінь та конфігурацію Spring DI.

Контролер перетворює HTTP-запит на DTO, викликає сценарій і формує HTTP-відповідь зі статусом 201 Created. Бізнес-правила в контролері не реалізуються.

### Структура проєкту

```text
src/
├── Domain/
│   └── com/cloudplatform/domain/
│       ├── model/
│       │   ├── Address.java
│       │   ├── Order.java
│       │   ├── OrderItem.java
│       │   └── OrderStatus.java
│       └── repository/
│           └── OrderRepository.java
├── Application/
│   └── com/cloudplatform/application/
│       ├── port/
│       │   └── InventoryAvailabilityPort.java
│       └── usecase/
│           ├── CreateOrderCommand.java
│           ├── CreateOrderUseCase.java
│           └── InsufficientInventoryException.java
├── Infrastructure/
│   └── com/cloudplatform/infrastructure/
│       ├── inventory/
│       │   └── InMemoryInventoryAvailabilityAdapter.java
│       └── persistence/
│           └── InMemoryOrderRepository.java
└── WebAPI/
    └── com/cloudplatform/webapi/
        ├── config/
        │   └── ApplicationConfiguration.java
        └── controller/
            └── CreateOrderController.java

tests/
├── com/cloudplatform/application/
│   └── CreateOrderUseCaseTest.java
└── com/cloudplatform/architecture/
    └── LayerDependencyTest.java


Перевірка правила залежностей
Для автоматичної перевірки використано ArchUnit. Тест LayerDependencyTest забороняє пакетам Domain залежати від пакетів Application, Infrastructure та WebAPI.

Результат перевірки: тест пройшов. Залежностей Domain від зовнішніх шарів не виявлено.
2. Сценарій використання
Реалізований сценарій: CreateOrderUseCase.

Вхідні дані передаються через CreateOrderCommand, який містить:
- ідентифікатор клієнта;
- список позицій замовлення;
- адресу доставки.
Залежності сценарію передаються через конструктор:
public CreateOrderUseCase(
        OrderRepository orderRepository,
        InventoryAvailabilityPort inventoryPort
) {
    this.orderRepository = orderRepository;
    this.inventoryPort = inventoryPort;
}


Сценарій виконує такі дії:
1. Перевіряє наявність команди.
2. Перетворює позиції команди на доменні об'єкти.
3. Створює доменну адресу та сутність замовлення.
4. Перевіряє доступність кожного товару через InventoryAvailabilityPort.
5. Зберігає замовлення через OrderRepository.
6. Повертає створену сутність.
Доменні правила відхиляють замовлення без клієнта, без позицій, без адреси доставки, з некоректною кількістю або ціною. Якщо товар недоступний, сценарій генерує InsufficientInventoryException і не викликає збереження.

У сценарії немає прямого створення адаптерів, SQL-запитів, URL або рядків підключення.
3. Композиційний корінь і Dependency Injection
Композиційний корінь розташований у ApplicationConfiguration.java.

Для керування залежностями використано Spring Framework Dependency Injection. Залежності передаються через конструктори та методи конфігурації.
АбстракціяРеалізаціяЖиттєвий циклОбґрунтування
OrderRepositoryInMemoryOrderRepositorySingletonОдин потокобезпечний екземпляр спільного in-memory сховища в межах процесу
InventoryAvailabilityPortInMemoryInventoryAvailabilityAdapterSingletonАдаптер не містить стану конкретного запиту
CreateOrderUseCaseCreateOrderUseCaseTransient (Prototype)Новий екземпляр сценарію під час кожного вирішення залежності
CreateOrderControllerCreateOrderControllerScoped (Request)Екземпляр контролера обмежений життєвим циклом HTTP-запиту

У Spring життєвий цикл Transient реалізовано через prototype, Scoped — через @RequestScope, Singleton — стандартною областю singleton.

Бізнес-шар не імпортує DI-контейнер і не використовує Service Locator.
4. Модульні тести
Тести розташовані в tests/com/cloudplatform/application/CreateOrderUseCaseTest.java.

Для ізоляції сценарію використано Mockito. Інтерфейси OrderRepository та InventoryAvailabilityPort замінено mock-об'єктами.

Реалізовано три обов'язкові тести:
ТестПеревіркаРезультат
execute_validCommand_checksInventoryAndSavesOrderКоректне замовлення перевіряє доступність товару та передається на збереженняPASSED
execute_emptyItems_rejectsCommandWithoutSavingПорожній список товарів відхиляється, збереження не викликаєтьсяPASSED
execute_inventoryPortFailure_propagatesToCallerПомилка порту передається виклику, збереження не викликаєтьсяPASSED

Окремо виконується архітектурний тест LayerDependencyTest, який перевіряє напрямок залежностей.
Результат запуску
Команда запуску:
mvn clean test


Результат:
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

Tests run: 4, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS


Повний лог збережено у файлі docs/lr4-test-run.log.

Тести не підключаються до реальної бази даних, сокетів або зовнішніх сервісів.
5. Висновки
У лабораторній роботі реалізовано модульне ядро E-Commerce Cloud Platform відповідно до принципів Clean Architecture.

Створено чотири шари: Domain, Application, Infrastructure та WebAPI. Доменна модель не залежить від інфраструктури та вебфреймворку. Сценарій CreateOrderUseCase отримує залежності через конструктор і взаємодіє з репозиторієм та адаптером через інтерфейси.

У композиційному корені Spring DI зареєстровано Singleton, Transient і Scoped життєві цикли з поясненням вибору кожного. Для ізольованої перевірки сценарію використано JUnit 5 та Mockito. ArchUnit автоматично перевіряє правило залежностей.

Результат тестування: 4 тести виконано успішно, помилок немає.

Поставленої мети досягнуто. Реалізація підтверджує ПРН17 — використання сучасних інструментальних засобів розробки та інтеграції компонентів, а також ФК03 — проєктування архітектури, модулів та інтерфейсів програмного забезпечення.