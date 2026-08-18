# Trendora 🛒

**Trendora** is a full-featured e-commerce backend application built using **Java, Spring Boot, Spring Security, JPA/Hibernate, and MySQL**.

The application provides secure REST APIs for user authentication, product management, product search, cart management, order processing, and personalized product recommendations.

The project focuses on implementing **real-world backend architecture, secure authentication, database relationships, transactional operations, and scalable REST APIs**.

---

## 🚀 Features

### 🔐 Authentication & Authorization

* User registration and login
* JWT-based authentication
* Secure password storage using BCrypt
* Role-based authorization
* Customer and Admin roles
* Protected REST APIs
* JWT validation through Spring Security filter

### 📦 Product Management

* Create products
* Get all products
* Get product by ID
* Update products
* Delete products
* Product search
* Pagination
* Sorting
* Admin-only product management

---

### 🤖 Product Recommendation System

Trendora includes a **product recommendation system** that recommends products based on product-related data.

The recommendation feature helps users discover products that may be relevant to their interests.

Example flow:

```text
User
 │
 ▼
User/Product Interaction
 │
 ▼
Recommendation Logic
 │
 ▼
Recommended Products
```

This makes the application more than a basic CRUD-based e-commerce application and introduces an element of **personalized product discovery**.

---

### 🛒 Cart Management

Users can manage their shopping cart through REST APIs.

Features include:

* Add product to cart
* Remove product from cart
* Update quantity
* View cart
  
Example:

```text
User
 │
 ▼
Select Product
 │
 ▼
Add to Cart
 │
 ▼
Update Quantity
 │
 ▼
Place Order
```

---

### 📋 Order Management

Trendora provides complete order processing functionality.

Features include:

* Place order from cart
* View user orders
* Order status management
* Cancel orders
* Admin order acceptance
* Order history preservation
* Transactional order processing

### Order Lifecycle

```text
                 ┌──────────────┐
                 │   PENDING    │
                 └──────┬───────┘
                        │
              ┌─────────┴─────────┐
              ▼                   ▼
        ┌───────────┐       ┌───────────┐
        │ ACCEPTED  │       │ CANCELLED │
        └─────┬─────┘       └───────────┘
              │
              ▼
        ┌─────────────┐
        │  PROCESSING │
        └──────┬──────┘
               │
               ▼
        ┌─────────────┐
        │  COMPLETED  │
        └─────────────┘
```

Cancelled orders are retained in the database with their status changed to `CANCELLED` instead of being physically deleted. This preserves the order history.

---

# 🏗️ Architecture

Trendora follows a layered Spring Boot architecture:

```text
                 Client
                   │
                   ▼
             REST Controller
                   │
                   ▼
              Service Layer
                   │
                   ▼
             Repository Layer
                   │
                   ▼
               MySQL DB
```

### Controller Layer

Responsible for:

* Receiving HTTP requests
* Validating request data
* Returning HTTP responses

### Service Layer

Contains the application's business logic, including:

* Cart operations
* Order processing
* Product operations
* Recommendation logic
* User-related operations

### Repository Layer

Uses **Spring Data JPA** to communicate with the database.

### Entity Layer

Represents the application's database entities and their relationships.

### DTO Layer

DTOs are used for transferring request and response data without directly exposing database entities.

---

# 🧩 Main Entities

```text
User
 │
 ├──────────────► Cart
 │                  │
 │                  └──► CartItem ──► Product
 │
 └──────────────► Order
                    │
                    └──► OrderItem ──► Product
```

### User

Stores customer/admin information and authentication-related data.

### Product

Represents products available in the store.

### Cart

Represents a user's shopping cart.

### CartItem

Represents a product and its quantity inside a cart.

### Order

Represents an order placed by a user.

### OrderItem

Represents individual products included in an order.

---

# 🛠️ Tech Stack

| Technology        | Purpose                        |
| ----------------- | ------------------------------ |
| Java              | Programming Language           |
| Spring Boot       | Backend Framework              |
| Spring Web        | REST APIs                      |
| Spring Security   | Authentication & Authorization |
| JWT               | Stateless Authentication       |
| Spring Data JPA   | Database Access                |
| Hibernate         | ORM                            |
| MySQL             | Relational Database            |
| Maven             | Dependency Management          |
| Swagger / OpenAPI | API Documentation              |

---

# 🔐 JWT Authentication Flow

Trendora uses JWT for stateless authentication.

```text
User
 │
 ▼
Login
 │
 ▼
Authentication
 │
 ▼
JWT Token Generated
 │
 ▼
Client Stores Token
 │
 ▼
Request + JWT
 │
 ▼
JwtAuthenticationFilter
 │
 ▼
Token Validation
 │
 ▼
Spring Security
 │
 ▼
Protected API
```

The JWT is sent with subsequent requests:

```text
Authorization: Bearer <JWT_TOKEN>
```

The JWT authentication filter validates the token and establishes the authenticated user before the request reaches the protected controller.

---

# 👥 Role-Based Authorization

Trendora supports different permissions for different users.

### Customer

Customers can:

* Browse products
* View product details
* Manage their cart
* Place orders
* View their orders
* Cancel eligible orders
* Get product recommendations

### Admin

Admins can additionally:

* Create products
* Update products
* Delete products
* Manage orders
* Accept customer orders
* Update order status

Example:

```text
                JWT Authentication
                       │
                       ▼
              ┌─────────────────┐
              │ Authenticated   │
              │     User        │
              └────────┬────────┘
                       │
              ┌────────┴────────┐
              ▼                 ▼
           CUSTOMER           ADMIN
              │                 │
              ▼                 ▼
        Customer APIs       Admin APIs
```

---

# 🔎 Product Search & Discovery

Users can search the product catalog using keywords.

```text
Search Keyword
      │
      ▼
Product Search API
      │
      ▼
Database Query
      │
      ▼
Matching Products
```

Search functionality makes it easier for users to discover products in a large catalog.

---

# 🤖 Recommendation System

Trendora also contains a product recommendation feature.

The recommendation system analyzes available product/user-related information and generates relevant product recommendations.

```text
User
 │
 ▼
Relevant Data
 │
 ▼
Recommendation Logic
 │
 ▼
Recommended Products
```

This feature demonstrates how recommendation functionality can be integrated into a traditional Spring Boot e-commerce backend.

---

# 📄 Pagination & Sorting

Product APIs support pagination and sorting.

Example:

```text
GET /api/product/get/products?page=0&size=10&sortBy=price&direction=asc
```

Benefits:

* Avoids loading the complete product catalog
* Improves API performance
* Provides better frontend experience
* Supports scalable product listing

---

# ⚠️ Validation & Exception Handling

The application implements:

* Request validation
* DTO validation
* Global exception handling
* Custom error responses
* Handling of invalid product IDs
* Handling of invalid users
* Cart validation
* Order validation

A centralized exception-handling approach keeps API responses consistent.

---

# 💾 Database Design

Trendora uses **MySQL** with JPA/Hibernate.

Major tables/entities include:

```text
users
products
cart
cart_items
orders
order_items
```

Entity relationships are managed using JPA mappings.

For example:

```text
User
 ├── 1 : 1 ── Cart
 │
 └── 1 : N ── Orders

Cart
 └── 1 : N ── CartItems

Order
 └── 1 : N ── OrderItems

CartItem
 └── N : 1 ── Product

OrderItem
 └── N : 1 ── Product
```

---

# 📂 Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com.project.trendora/
    │       ├── controller/
    │       ├── service/
    │       ├── repository/
    │       ├── entity/
    │       ├── dto/
    │       ├── exception/
    │       ├── security/
    │       └── config/
    │
    └── resources/
        └── application.properties
```

---

# 🔌 API Modules

## Authentication

```text
POST /api/user/register
POST /api/user/login
```

## Products

```text
GET    /api/product/{id}
GET    /api/product/get/products
POST   /api/product/add
PUT    /api/product/update/{id}
DELETE /api/product/delete/{id}
GET    /api/product/search
```

## Cart

```text
POST   /api/cart/add
DELETE /api/cart/remove
GET    /api/cart/view/{userId}
```

## Orders

```text
POST /api/orders/place
GET  /api/orders/view/{userId}
PUT  /api/orders/cancel/{orderId}
```

## Recommendations

```text
GET /api/recommendations/product/{productId}
```

---

# ▶️ Running the Project

### 1. Clone the repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

### 2. Create the database

```sql
CREATE DATABASE trendora;
```

### 3. Configure application properties

Update:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/trendora
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

Configure your JWT secret and other sensitive values using environment variables.

### 4. Build the project

```bash
mvn clean install
```

### 5. Run the application

```bash
mvn spring-boot:run
```

The application will run on:

```text
http://localhost:8080
```

---

# 📖 API Documentation

Swagger/OpenAPI is integrated into the project for API documentation and testing.

After starting the application, open the configured Swagger UI to:

* View available endpoints
* Check request/response structures
* Test APIs
* Provide JWT authentication
* Explore API documentation

---

# 🧪 Typical User Flow

```text
Register
   │
   ▼
Login
   │
   ▼
Receive JWT
   │
   ▼
Browse Products
   │
   ├────► Search Products
   │
   └────► Get Recommendations
             │
             ▼
        Add to Cart
             │
             ▼
        View Cart
             │
             ▼
        Place Order
             │
             ▼
          PENDING
             │
             ▼
       Admin Accepts
             │
             ▼
          ACCEPTED
             │
             ▼
        Processing
             │
             ▼
         Completed
```

---

# 🎯 Key Backend Concepts Demonstrated

This project demonstrates practical knowledge of:

* Java
* Spring Boot
* REST API development
* Spring Security
* JWT authentication
* Role-based authorization
* BCrypt password hashing
* Spring Data JPA
* Hibernate ORM
* MySQL
* Entity relationships
* DTO pattern
* Service-layer architecture
* Repository pattern
* Transaction management
* Product search
* Product recommendation systems
* Pagination
* Sorting
* Bean validation
* Global exception handling
* Swagger/OpenAPI
* Secure API design

---

# 🔮 Future Enhancements

Potential future improvements:

* Payment gateway integration
* Wishlist
* Product reviews and ratings
* Advanced product filtering
* Inventory management
* Email notifications
* Order tracking
* Redis caching
* Docker containerization
* Automated unit and integration testing
* More advanced ML-based recommendations

---

# 👨‍💻 Author

**Nikhil Kumar Rana**

Java Backend Developer 

**Technologies:** Java • Spring Boot • Spring Security • JWT • JPA • Hibernate • MySQL • REST APIs

---

## ⭐ Project Objective

The objective of Trendora is to build a production-style e-commerce backend while applying real-world backend engineering concepts such as **secure authentication, role-based authorization, relational database design, transactional order processing, product discovery, and recommendation systems**.

The project combines traditional e-commerce functionality with modern backend practices to create a scalable and maintainable application.
