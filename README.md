# Trendora 🛒

> A production-style **e-commerce backend** built with **Java, Spring Boot, Spring Security (JWT), JPA/Hibernate and MySQL**, with **Razorpay payment integration**, an admin order lifecycle, and product recommendations.

[![Live Demo](https://img.shields.io/badge/Live-Demo-brightgreen)](https://trendora-i4wh.onrender.com/)
[![Swagger](https://img.shields.io/badge/API-Swagger%20UI-85EA2D)](https://trendora-i4wh.onrender.com/swagger-ui/index.html)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F)
![MySQL](https://img.shields.io/badge/MySQL-8-blue)
![Razorpay](https://img.shields.io/badge/Payments-Razorpay-0C2451)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?logo=docker&logoColor=white)

---

## 🌐 Live Demo

| | Link |
|---|---|
| **Base URL** | https://trendora-i4wh.onrender.com/ |

> ⏳ **Note:** Hosted on Render's free tier, so the first request after inactivity can take 30–60 seconds (cold start).

---

## 📌 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Entities & Relationships](#-entities--relationships)
- [Authentication & Authorization](#-authentication--authorization)
- [Order Lifecycle](#-order-lifecycle)
- [Razorpay Payment Flow](#-razorpay-payment-flow)
- [API Reference](#-api-reference)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Environment Variables](#-environment-variables)
- [Run with Docker](#-run-with-docker)
- [Deployment](#-deployment)
- [Future Enhancements](#-future-enhancements)
- [Author](#-author)

---

## 🚀 Features

### 🔐 Authentication & Authorization
- User registration and login
- Stateless **JWT** authentication via a custom Spring Security filter
- Passwords hashed with **BCrypt**
- Role-based access: **CUSTOMER** and **ADMIN**
- Protected REST endpoints

### 📦 Product Management
- Create, read, update (PATCH) and delete products (admin-only for write operations)
- Keyword **search**
- **Pagination** and **sorting**

### 🛒 Cart Management
- Add product to cart
- Remove product from cart
- View cart

### 📋 Orders
- Place an order from the cart
- View order history
- Cancel an order (soft cancel — status becomes `CANCELLED`, record is preserved)
- Transactional order processing

### 🛠️ Admin Order Management
- Accept, process, ship and deliver orders through dedicated endpoints

### 💳 Payments (Razorpay)
- Create a Razorpay payment order for a placed order
- Verify the payment **signature** server-side before confirming payment

### 🤖 Product Recommendations
- Get recommended products based on a selected product

### 🐳 Containerization
- Application is **Dockerized**, so it runs the same way on any machine with a single command

### 📖 API Documentation
- Full **Swagger / OpenAPI** documentation with JWT support

### ⚠️ Validation & Error Handling
- Bean validation on request DTOs
- Global exception handling with consistent error responses
- Handling of invalid users, products, carts and orders

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Language |
| Spring Boot | Backend framework |
| Spring Web | REST APIs |
| Spring Security + JWT | Authentication & authorization |
| Spring Data JPA / Hibernate | ORM & data access |
| MySQL | Relational database |
| Razorpay Java SDK | Payment gateway |
| Swagger / OpenAPI | API docs & testing |
| Maven | Build & dependency management |
| Docker | Containerization |
| Render | Cloud deployment |

---

## 🏗️ Architecture

Layered architecture with clear separation of concerns:

```text
Client
  │
  ▼
Controller Layer   → handles HTTP requests, validation, responses
  │
  ▼
Service Layer      → business logic (cart, orders, payments, recommendations)
  │
  ▼
Repository Layer   → Spring Data JPA
  │
  ▼
MySQL Database
```

DTOs are used for request/response data so database entities are never exposed directly.

---

## 🧩 Entities & Relationships

```text
User ──1:1── Cart ──1:N── CartItem ──N:1── Product
  │
  └──1:N── Order ──1:N── OrderItem ──N:1── Product
```

| Entity | Description |
|---|---|
| `User` | Customer/admin account and auth data |
| `Product` | Product available in the store |
| `Cart` / `CartItem` | A user's cart and its products with quantity |
| `Order` / `OrderItem` | A placed order and its line items |

---

## 🔐 Authentication & Authorization

```text
Login → Credentials verified → JWT generated → Client stores token
   → Request with "Authorization: Bearer <token>"
   → JwtAuthenticationFilter validates token
   → SecurityContext set → Protected API reached
```

| Role | Permissions |
|---|---|
| **CUSTOMER** | Browse/search products, manage cart, place & cancel orders, pay, view recommendations |
| **ADMIN** | Everything above + manage products + accept / process / ship / deliver orders |

---

## 📋 Order Lifecycle

```text
                    ┌─────────┐
                    │ PENDING │
                    └────┬────┘
              ┌──────────┴──────────┐
              ▼                     ▼
        ┌──────────┐          ┌───────────┐
        │ ACCEPTED │          │ CANCELLED │
        └────┬─────┘          └───────────┘
             ▼
        ┌────────────┐
        │ PROCESSING │
        └────┬───────┘
             ▼
        ┌─────────┐
        │ SHIPPED │
        └────┬────┘
             ▼
        ┌───────────┐
        │ DELIVERED │
        └───────────┘
```

Cancelled orders stay in the database with status `CANCELLED` to preserve order history.

---

## 💳 Razorpay Payment Flow

```text
1. Customer places order            POST /api/orders/place/{userId}
2. Backend creates Razorpay order   POST /api/payments/create/{orderId}
3. Client opens Razorpay Checkout using the returned order details
4. Customer pays
5. Client sends payment details     POST /api/payments/verify
6. Backend verifies the Razorpay signature (HMAC-SHA256) → payment confirmed
```

The Razorpay key secret never leaves the server, and payment is only trusted after signature verification.

---

## 🔌 API Reference

### 👤 User APIs
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/user/register` | Register a user |
| POST | `/api/user/login` | Login and receive JWT |
| GET | `/api/user/view/profile` | View logged-in user's profile |

### 📦 Product APIs
| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/product/{id}` | Get product by ID | Authenticated |
| GET | `/api/product/get/products` | Get all products (pagination + sorting) | Authenticated |
| GET | `/api/product/search` | Search products | Authenticated |
| POST | `/api/product/add` | Add a product | Admin |
| PATCH | `/api/product/update` | Update a product | Admin |
| DELETE | `/api/product/delete/{id}` | Delete a product | Admin |

Example:
```text
GET /api/product/get/products?page=0&size=10&sortBy=price&direction=asc
```

### 🛒 Cart Management
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/cart/add` | Add product to cart |
| POST | `/api/cart/remove` | Remove product from cart |
| GET | `/api/cart/view/{userId}` | View user's cart |

### 📋 Order Management
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/orders/place/{userId}` | Place a new order |
| GET | `/api/orders/view/{userId}` | View user's orders |
| PUT | `/api/orders/cancel/{orderId}` | Cancel an order |

### 🛠️ Admin Order Management
| Method | Endpoint | Description |
|---|---|---|
| PUT | `/api/admin/orders/accept/{orderId}` | Accept order |
| PUT | `/api/admin/orders/{orderId}/process` | Mark order as processing |
| PUT | `/api/admin/orders/{orderId}/ship` | Ship order |
| PUT | `/api/admin/orders/{orderId}/deliver` | Mark order as delivered |

### 💳 Payment Management
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/payments/create/{orderId}` | Create Razorpay payment order |
| POST | `/api/payments/verify` | Verify Razorpay payment signature |

### 🤖 Product Recommendations
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/recommendations/product/{productId}` | Get recommendations for a product |

---

## 📂 Project Structure

```text
src/
└── main/
    ├── java/com/project/trendora/
    │   ├── controller/
    │   ├── service/
    │   ├── repository/
    │   ├── entity/
    │   ├── dto/
    │   ├── exception/
    │   ├── security/
    │   └── config/
    └── resources/
        └── application.properties
```

---

## ▶️ Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL 8+
- A Razorpay account (test mode keys work fine)

### 1. Clone the repository
```bash
git clone https://github.com/nikilkumar-swe/trendora.git
cd <YOUR_REPO_NAME>
```

### 2. Create the database
```sql
CREATE DATABASE trendora;
```

### 3. Configure `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/trendora
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

### 4. Set environment variables
See [Environment Variables](#-environment-variables).

### 5. Build and run
```bash
mvn clean install
mvn spring-boot:run
```

App runs at `http://localhost:8080

---

## 🔑 Environment Variables

Never commit secrets. Configure these via environment variables (names should match your `application.properties`):

| Variable | Description |
|---|---|
| `DB_USERNAME` | MySQL username |
| `DB_PASSWORD` | MySQL password |
| `JWT_SECRET` | Secret used to sign JWT tokens |
| `RAZORPAY_KEY_ID` | Razorpay key ID |
| `RAZORPAY_KEY_SECRET` | Razorpay key secret |

---

## 🐳 Run with Docker

Trendora is containerized with **Docker**, so you can run it without installing Java or Maven locally.

### Build the image
```bash
docker build -t trendora .
```

### Run the container
```bash
docker run -p 8080:8080 \
  -e DB_USERNAME=<your_db_username> \
  -e DB_PASSWORD=<your_db_password> \
  -e JWT_SECRET=<your_jwt_secret> \
  -e RAZORPAY_KEY_ID=<your_key_id> \
  -e RAZORPAY_KEY_SECRET=<your_key_secret> \
  trendora
```

> The database URL must point to a MySQL instance reachable from the container (for example `host.docker.internal` for a MySQL running on your machine, or a separate MySQL container).

App will be available at `http://localhost:8080`.

---

## ☁️ Deployment

The application is deployed on **Render**.

- Live: https://trendora-i4wh.onrender.com/
- Secrets and DB credentials are configured through Render environment variables.

---

## 🧪 Typical Flow

```text
Register → Login → Get JWT → Browse / Search products → Get recommendations
→ Add to cart → View cart → Place order (PENDING)
→ Create Razorpay payment → Pay → Verify payment
→ Admin: Accept → Process → Ship → Deliver
```

---

## 🎯 Key Concepts Demonstrated

- REST API design with Spring Boot
- JWT authentication & role-based authorization with Spring Security
- BCrypt password hashing
- JPA/Hibernate entity relationships (1:1, 1:N, N:1)
- DTO pattern, service layer, repository pattern
- Transactional order processing
- Third-party payment gateway integration (Razorpay) with signature verification
- Pagination, sorting, search
- Bean validation and global exception handling
- Swagger/OpenAPI documentation
- Containerization with Docker
- Cloud deployment on Render

---

## 🔮 Future Enhancements

- Wishlist
- Product reviews and ratings
- Advanced filtering
- Inventory/stock management
- Email notifications
- Redis caching
- Unit & integration tests
- ML-based recommendations

---

## 👨‍💻 Author

**Nikhil Kumar Rana** — Java Backend Developer

**Skills:** Java • Spring Boot • Spring Security • JWT • JPA • Hibernate • MySQL • REST APIs

[![GitHub](https://img.shields.io/badge/GitHub-nikilkumar--swe-181717?logo=github)](https://github.com/nikilkumar-swe)
[![Email](https://img.shields.io/badge/Email-nikil.kumar.swe%40gmail.com-D14836?logo=gmail&logoColor=white)](mailto:nikil.kumar.swe@gmail.com)

---

⭐ If you found this project useful, consider giving it a star!
