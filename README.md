# AtomicBank 🏦

A high-performance, concurrency-safe banking REST API built with Spring Boot. Designed to handle simultaneous financial transactions without data inconsistencies.

## 🚀 Key Features
* **Atomic Transactions:** Uses `@Transactional` to ensure ACID compliance.
* **Concurrency Control:** Implements Pessimistic Locking on database rows to prevent "Double Spend" attacks.
* **Security:** Role-Based Access Control (RBAC) via Spring Security.
* **Auditability:** Immutable transaction logs (no hard deletes).
* **Containerization:** Fully Dockerized for portable deployment.

## 🛠️ Tech Stack
* **Core:** Java 21, Spring Boot 3.2
* **Database:** PostgreSQL
* **Security:** Spring Security (CSRF disabled for API), Basic Auth
* **DevOps:** Docker, Docker Compose

## ⚡ How to Run
1. **Clone the repo**
   ```bash
   git clone (https://github.com/Rijul796/Atomic_Bank.git)
