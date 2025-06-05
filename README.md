# 📘 DAPI

## Overview

DAPI is a REST API capable of persisting and managing device resources.

### Device Domain
- Id
- Name
- Brand
- State (available, in-use, inactive)
- Creation time

### Supported Functionalities
- Create a new device
- Fully and/or partially update an existing device
- Fetch a single device
- Fetch all devices
- Fetch devices by brand
- Fetch devices by state
- Delete a single device

### Domain Validations
- Creation time cannot be updated
- Name and brand properties cannot be updated if the device is in use
- In use devices cannot be deleted

---

## 🧩 Tech Stack

- Java 21
- Spring Boot 3.5.0
- Maven 3.9+
- PostgreSQL
- Swagger/OpenAPI for Documentation

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 21
- Maven 3.9+
- Docker

### 📦 Build the Project

```bash
mvn clean install
```

### 🐳 Run with Docker Compose

```bash
docker-compose up --build
```

### 🔗 API Base URL

```
http://localhost:8080/devices
```

### 🌐 Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## 📂 API Endpoints

### 🔹 Create a Device

```
POST /devices
```

**Request Body**

```json
{
  "name": "MacBook Pro",
  "brand": "Apple",
  "state": "AVAILABLE"
}
```

**Response:** `201 Created`

---

### 🔹 Update a Device

```
POST /devices/{id}
```

**Response:** `200 OK`

```json
{
  "id": 1,
  "name": "MacBook Air",
  "brand": "Apple",
  "state": "INACTIVE",
  "creationTime": "2025-01-01T12:34:56.789000"
}
```

OR

```json
{
  "message": "Device Not Found or it is in use. No changes made."
}
```

---

### 🔹 Get a Device by ID

```
GET /devices/{id}
```

**Response:** `200 OK` with device or `{}`

---

### 🔹 Get All Devices

```
GET /devices
```

**Response:** `200 OK` with array of devices

---

### 🔹 Get Devices by Brand

```
GET /devices/brands/{brand}
```

**Response:** `200 OK`

---

### 🔹 Get Devices by State

```
GET /devices/states/{state}
```

**Path Variable:** `AVAILABLE`, `IN_USE`, `INACTIVE`

---

### 🔹 Delete a Device

```
DELETE /devices/{id}
```

**Response:** `200 OK`

```json
{ "message": "Device deleted." }
```

or

```json
{ "message": "Device Not Found or it is in use. No changes made." }
```

---

## 🧪 Examples of Use

You can run the unit tests via:

```bash
mvn test
```

or import the Swagger/OpenAPI file into Postman

Some CURLs examples:

Create a Device
```bash
curl --location 'http://localhost:8080/devices' \
--header 'Content-Type: application/json' \
--data '{
    "name":"Pixel 9 Pro",
    "brand": "Google",
    "state": "IN_USE"
}'
```

Get a Device by ID
```bash
curl --location 'http://localhost:8080/devices/1'
```

Get All Devices
```bash
curl --location 'http://localhost:8080/devices'
```

Update a Device
```bash
curl --location 'http://localhost:8080/devices/1' \
--header 'Content-Type: application/json' \
--data '{
    "name":"Pixel 9 Pro",
    "brand": "Google",
    "state": "AVAILABLE"
}'
```

Get Devices by Brand
```bash
curl --location 'http://localhost:8080/devices/brands/Google'
```

Get Devices by State
```bash
curl --location 'http://localhost:8080/devices/states/AVAILABLE'
```

Delete a Device
```bash
curl --location --request DELETE 'http://localhost:8080/devices/1'
```

---