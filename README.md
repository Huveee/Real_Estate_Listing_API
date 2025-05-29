# 🏡 Real Estate Listing API

A RESTful API built with Spring Boot to manage real estate portals. It covers features such as property listings, agent profiles, viewings, offers, and customer management.

---

## 🔐 Authentication

All endpoints require an API key, sent via the `X-API-Key` header.

---

## 📦 Features

- 🏠 Manage property listings
- 👤 Agent and customer profiles
- 🕐 Schedule and manage viewings
- 💬 Submit and track offers
- 🔍 Search and filter listings
- 📋 Soft delete with audit logging

---

## 🔧 Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Web & Spring Data JPA**
- **H2 Database (in-memory)**
- **Lombok**
- **Maven**

---

## 📁 API Endpoints

### 🏠 Properties
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/properties` | Create new property |
| `GET` | `/properties` | Get all properties |
| `GET` | `/properties/{id}` | Get property by ID |
| `PUT` | `/properties/{id}` | Update property |
| `DELETE` | `/properties/{id}` | Soft delete property |
| `PATCH` | `/properties/{id}/status` | Update property status |
| `GET` | `/properties/search?location=&minPrice=&maxPrice=` | Filtered search |
| `GET` | `/properties/agent/{agentId}` | Get properties by agent |

---

### 👤 Agents
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/agents` | Get all agents |
| `GET` | `/agents/{id}` | Get agent by ID |
| `POST` | `/agents` | Create new agent |
| `DELETE` | `/agents/{id}` | Soft delete agent |
| `GET` | `/agents/top` | Get top 3 agents by listing count |
| `GET` | `/agents/{agentId}/properties` | Get listings for a specific agent |

---

### 👥 Customers
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/customers` | Get all customers |
| `GET` | `/customers/{id}` | Get customer by ID |
| `POST` | `/customers` | Create new customer |
| `DELETE` | `/customers/{id}` | Soft delete customer |

---

### 📆 Viewings
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/viewings` | Schedule new viewing |
| `GET` | `/viewings` | Get all viewings |
| `GET` | `/viewings/{id}` | Get viewing by ID |
| `GET` | `/viewings/upcoming` | Get upcoming viewings |
| `DELETE` | `/viewings/{id}` | Soft delete viewing |

---

### 💬 Offers
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/offers` | Submit an offer |
| `GET` | `/offers` | Get all offers |
| `GET` | `/offers/{id}` | Get offer by ID |
| `GET` | `/offers/property/{propertyId}` | Get offers for a property |
| `PUT` | `/offers/{id}` | Update an existing offer |
| `DELETE` | `/offers/{id}` | Soft delete an offer |

---

## ✅ Total Endpoints: **25+**

---

## ▶️ How to Run

```bash
mvn spring-boot:run
```

H2 Console: [http://localhost:9090/h2-console](http://localhost:9090/h2-console)  
JDBC URL: `jdbc:h2:mem:realestatedb`

---

## 📄 Example Request Header

```http
GET /properties HTTP/1.1
Host: localhost:9090
X-API-Key: huve123
```

---

## 🧪 Notes

- All delete operations are implemented as soft deletes.
- Every create/update/delete action is logged via the audit logging service.
- HTTP status codes are standardized: `200 OK`, `201 Created`, `204 No Content`, `404 Not Found`.

---

