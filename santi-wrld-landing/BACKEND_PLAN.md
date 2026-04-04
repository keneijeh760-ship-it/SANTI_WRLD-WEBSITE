# SANTI WRLD -- Spring Boot Backend Plan

Full backend architecture for the SANTI WRLD e-commerce platform.
Java 17+ / Spring Boot 3.x / PostgreSQL / Paystack.

---

## Project Structure

```
com.santiwrld.api
├── config/             # Security, CORS, Paystack config
├── controller/         # REST controllers
├── dto/                # Request/response DTOs
│   ├── request/
│   └── response/
├── entity/             # JPA entities
├── enums/              # OrderStatus, PaymentStatus, etc.
├── exception/          # Custom exceptions + global handler
├── repository/         # Spring Data JPA repositories
├── service/            # Business logic
└── util/               # Helpers (Paystack client, email, etc.)
```

---

## Entities

### 1. User

| Field         | Type         | Notes                          |
|---------------|--------------|--------------------------------|
| id            | UUID (PK)    | Auto-generated                 |
| fullName      | String       | Not null                       |
| email         | String       | Unique, not null               |
| phone         | String       | Nullable                       |
| passwordHash  | String       | BCrypt hashed                  |
| role          | Role (enum)  | CUSTOMER, ADMIN                |
| createdAt     | Timestamp    | Auto-set                       |
| updatedAt     | Timestamp    | Auto-updated                   |

### 2. Product

| Field         | Type         | Notes                          |
|---------------|--------------|--------------------------------|
| id            | UUID (PK)    | Auto-generated                 |
| slug          | String       | Unique, URL-friendly           |
| name          | String       | Not null                       |
| subtitle      | String       | Short description              |
| priceKobo     | Long         | Price in kobo (100 kobo = ₦1)  |
| imageUrl      | String       | Path or CDN URL                |
| collection    | String       | e.g. "srntz", "lnd"           |
| active        | Boolean      | Soft visibility toggle         |
| stockQuantity | Integer      | Available stock                |
| createdAt     | Timestamp    |                                |
| updatedAt     | Timestamp    |                                |

### 3. Order

| Field           | Type           | Notes                           |
|-----------------|----------------|---------------------------------|
| id              | UUID (PK)      |                                 |
| orderReference  | String         | Unique, e.g. "SW-1712345678"   |
| user            | User (FK)      | Nullable for guest checkout     |
| customerName    | String         | From checkout form              |
| customerEmail   | String         | From checkout form              |
| customerPhone   | String         |                                 |
| deliveryAddress | String         |                                 |
| city            | String         |                                 |
| state           | String         |                                 |
| totalKobo       | Long           | Sum of all line items           |
| status          | OrderStatus    | PENDING, PAID, SHIPPED, DELIVERED, CANCELLED |
| paymentRef      | String         | Paystack transaction reference  |
| paymentStatus   | PaymentStatus  | PENDING, SUCCESS, FAILED        |
| createdAt       | Timestamp      |                                 |
| updatedAt       | Timestamp      |                                 |

### 4. OrderItem

| Field         | Type         | Notes                          |
|---------------|--------------|--------------------------------|
| id            | UUID (PK)    |                                |
| order         | Order (FK)   | ManyToOne                      |
| product       | Product (FK) | ManyToOne                      |
| productName   | String       | Snapshot at time of order      |
| priceKobo     | Long         | Snapshot at time of order      |
| quantity      | Integer      | Min 1                          |

### 5. WaitlistEntry

| Field         | Type         | Notes                          |
|---------------|--------------|--------------------------------|
| id            | UUID (PK)    |                                |
| email         | String       | Unique                         |
| createdAt     | Timestamp    |                                |

---

## Enums

```java
public enum Role { CUSTOMER, ADMIN }

public enum OrderStatus { PENDING, PAID, SHIPPED, DELIVERED, CANCELLED }

public enum PaymentStatus { PENDING, SUCCESS, FAILED }
```

---

## Repositories

All extend `JpaRepository<Entity, UUID>`.

| Repository              | Custom Methods                                             |
|-------------------------|------------------------------------------------------------|
| UserRepository          | `findByEmail(String email)`                                |
| ProductRepository       | `findBySlug(String slug)`, `findByActiveTrue()`, `findByCollection(String collection)` |
| OrderRepository         | `findByOrderReference(String ref)`, `findByCustomerEmail(String email)`, `findByStatus(OrderStatus status)` |
| OrderItemRepository     | `findByOrderId(UUID orderId)`                              |
| WaitlistRepository      | `findByEmail(String email)`, `existsByEmail(String email)` |

---

## Services

### ProductService
- `getAllActive()` -- list active products
- `getBySlug(String slug)` -- single product
- `getByCollection(String collection)` -- filter by collection
- `create(ProductCreateDTO dto)` -- admin: create product
- `update(UUID id, ProductUpdateDTO dto)` -- admin: update product
- `deactivate(UUID id)` -- admin: soft-delete

### OrderService
- `createOrder(CheckoutRequestDTO dto)` -- create order from checkout, set status PENDING
- `getByReference(String ref)` -- lookup order
- `getByEmail(String email)` -- customer's orders
- `updateStatus(UUID id, OrderStatus status)` -- admin: update order fulfillment
- `updatePaymentStatus(UUID id, PaymentStatus status, String paystackRef)` -- after verification

### PaystackService
- `verifyTransaction(String reference)` -- call Paystack API `GET /transaction/verify/:reference`
- `processWebhook(String payload, String signature)` -- verify webhook signature, update order
- Returns a `PaystackVerificationResponse` DTO

### UserService
- `register(RegisterDTO dto)` -- create user with hashed password
- `findByEmail(String email)` -- lookup
- `authenticate(LoginDTO dto)` -- validate credentials, return JWT

### WaitlistService
- `addEmail(String email)` -- add to waitlist, send confirmation email
- `getAll()` -- admin: list all entries
- `removeByEmail(String email)` -- admin: remove entry

### EmailService
- `sendWaitlistConfirmation(String email)` -- waitlist welcome email
- `sendOrderConfirmation(String email, Order order)` -- order receipt
- `sendShippingNotification(String email, Order order)` -- shipping update
- Use Spring Mail with an SMTP provider (e.g. Resend, SendGrid, Gmail SMTP)

---

## Controllers

### ProductController (`/api/products`)
| Method | Endpoint                  | Auth    | Description               |
|--------|---------------------------|---------|---------------------------|
| GET    | `/api/products`           | Public  | List active products      |
| GET    | `/api/products/{slug}`    | Public  | Get product by slug       |
| GET    | `/api/products?collection=srntz` | Public | Filter by collection |
| POST   | `/api/products`           | ADMIN   | Create product            |
| PUT    | `/api/products/{id}`      | ADMIN   | Update product            |
| DELETE | `/api/products/{id}`      | ADMIN   | Deactivate product        |

### OrderController (`/api/orders`)
| Method | Endpoint                         | Auth      | Description              |
|--------|----------------------------------|-----------|--------------------------|
| POST   | `/api/orders/checkout`           | Public    | Create order from cart   |
| GET    | `/api/orders/{reference}`        | Public    | Get order by reference   |
| GET    | `/api/orders?email=x`            | Public    | Customer's orders        |
| PATCH  | `/api/orders/{id}/status`        | ADMIN     | Update order status      |

### PaymentController (`/api/payments`)
| Method | Endpoint                         | Auth      | Description              |
|--------|----------------------------------|-----------|--------------------------|
| POST   | `/api/payments/verify`           | Public    | Verify Paystack payment  |
| POST   | `/api/payments/webhook`          | Paystack  | Paystack webhook handler |

### AuthController (`/api/auth`)
| Method | Endpoint                  | Auth    | Description               |
|--------|---------------------------|---------|---------------------------|
| POST   | `/api/auth/register`      | Public  | Register new user         |
| POST   | `/api/auth/login`         | Public  | Login, return JWT         |
| GET    | `/api/auth/me`            | USER    | Get current user profile  |

### WaitlistController (`/api/waitlist`)
| Method | Endpoint                  | Auth    | Description               |
|--------|---------------------------|---------|---------------------------|
| POST   | `/api/waitlist`           | Public  | Join waitlist             |
| GET    | `/api/waitlist`           | ADMIN   | List all entries          |
| DELETE  | `/api/waitlist/{email}`  | ADMIN   | Remove entry              |

---

## DTOs

### Request DTOs
```java
// Checkout
record CheckoutRequestDTO(
    String fullName,
    String email,
    String phone,
    String address,
    String city,
    String state,
    List<CartItemDTO> items
) {}

record CartItemDTO(String slug, int quantity) {}

// Auth
record RegisterDTO(String fullName, String email, String password) {}
record LoginDTO(String email, String password) {}

// Product (admin)
record ProductCreateDTO(String slug, String name, String subtitle,
    long priceKobo, String imageUrl, String collection) {}
record ProductUpdateDTO(String name, String subtitle,
    Long priceKobo, String imageUrl, Boolean active, Integer stockQuantity) {}

// Waitlist
record WaitlistRequestDTO(String email) {}
```

### Response DTOs
```java
record ProductResponseDTO(UUID id, String slug, String name, String subtitle,
    long priceKobo, String displayPrice, String imageUrl, String collection) {}

record OrderResponseDTO(UUID id, String orderReference, String customerName,
    String customerEmail, long totalKobo, String displayTotal,
    String status, String paymentStatus, List<OrderItemDTO> items,
    LocalDateTime createdAt) {}

record OrderItemDTO(String productName, long priceKobo, int quantity) {}

record AuthResponseDTO(String token, String email, String fullName, String role) {}

record PaymentVerifyResponseDTO(boolean verified, String reference, long amount) {}
```

---

## Security (Spring Security + JWT)

- Public endpoints: product listing, checkout, payment verify/webhook, waitlist join, auth
- USER endpoints: order lookup by email (scoped to own email)
- ADMIN endpoints: product CRUD, order status updates, waitlist management
- JWT token in `Authorization: Bearer <token>` header
- CORS configured for the Next.js frontend origin

---

## Paystack Webhook

Paystack sends POST to `/api/payments/webhook` on payment events.

1. Verify the `x-paystack-signature` header using HMAC SHA-512 with your secret key
2. Parse the event body; handle `charge.success` event
3. Look up the order by `data.reference`
4. Update `paymentStatus` to SUCCESS and `status` to PAID
5. Trigger order confirmation email

---

## Database (PostgreSQL)

### Key indexes
- `products.slug` -- unique index
- `orders.order_reference` -- unique index
- `orders.customer_email` -- index for lookup
- `waitlist.email` -- unique index
- `users.email` -- unique index

### Migrations
Use Flyway or Liquibase for schema migrations.

---

## Configuration (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/santiwrld
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

paystack:
  secret-key: ${PAYSTACK_SECRET_KEY}
  public-key: ${PAYSTACK_PUBLIC_KEY}

jwt:
  secret: ${JWT_SECRET}
  expiration-ms: 86400000  # 24 hours

mail:
  host: smtp.resend.com
  port: 465
  username: resend
  password: ${RESEND_API_KEY}

cors:
  allowed-origins: http://localhost:3000,https://yourdomain.com
```

---

## Checkout Flow (End-to-End)

```
1. Frontend sends POST /api/orders/checkout with cart items + customer details
2. Backend validates items, checks stock, creates Order (status: PENDING)
3. Backend returns orderReference to frontend
4. Frontend opens Paystack Inline with orderReference as ref
5. User pays in Paystack popup
6. Paystack calls webhook POST /api/payments/webhook
7. Backend verifies signature, updates order to PAID
8. Frontend also calls POST /api/payments/verify as fallback
9. Backend sends order confirmation email
10. Frontend shows success screen
```

---

## Dependencies (pom.xml)

- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `spring-boot-starter-validation`
- `spring-boot-starter-mail`
- `postgresql` (runtime)
- `jjwt-api` + `jjwt-impl` + `jjwt-jackson` (JWT)
- `flyway-core` (migrations)
- `lombok` (optional, for boilerplate reduction)
