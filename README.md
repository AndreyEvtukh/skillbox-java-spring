# Hotel Booking Service

A full-stack hotel booking application with hotel and room management, user authentication, administrative content management, booking functionality, and an event-driven statistics collection system.

The project consists of a **Spring Boot backend** and an **Angular frontend**. PostgreSQL is used as the primary relational database, while MongoDB is used to store statistical events received asynchronously through Apache Kafka.

---

## Table of Contents

* [Overview](#overview)
* [Main Features](#main-features)
* [Technology Stack](#technology-stack)
* [Requirements](#requirements)
* [Quick Start](#quick-start)
* [Application Architecture](#application-architecture)
* [Security](#security)
* [API Documentation](#api-documentation)
* [Databases](#databases)
* [Statistics](#statistics)
* [Statistics Export](#statistics-export)
* [Database Migrations](#database-migrations)
* [Frontend](#frontend)
* [Docker](#docker)
* [Local Development Without Docker](#local-development-without-docker)
* [Project Structure](#project-structure)
* [Version Control](#version-control)
* [Project Tasks](#project-tasks)
* [License](#license)

---

## Overview

The Hotel Booking Service is a web application designed to manage hotels, rooms, users, and room bookings.

The backend provides a REST API for working with the main business entities and implements authentication and role-based authorization.

The frontend provides a separate Angular 22 client for interacting with the backend API.

The application also contains a dedicated statistics layer based on **Apache Kafka** and **MongoDB**. User registration and room booking events are published to Kafka and processed asynchronously by a statistics consumer.

Statistical data can be exported by administrators in both **CSV** and **PDF** formats.

---

## Main Features

### Hotel and Room Management

* search hotels by specified criteria;
* filter hotels by rating;
* view detailed hotel information;
* view available rooms;
* view detailed room information;
* filter rooms using specified criteria;
* paginate hotel and room search results.

### Booking

* book a room for a specified period;
* validate booking dates;
* validate the existence of the requested room;
* validate the existence of the requesting user;
* prevent overlapping bookings for the same room;
* store booking information in PostgreSQL.

### User Management

* user registration;
* user authentication;
* user identification by email;
* role-based access control;
* administrative user management.

### Security

* HTTP Basic Authentication;
* `USER` role;
* `ADMIN` role;
* protected REST endpoints;
* administrator-only endpoints;
* role-based authorization using Spring Security.

### Statistics

* user registration event collection;
* room booking event collection;
* asynchronous event processing through Apache Kafka;
* separate Kafka topics for different event types;
* MongoDB storage for statistical events;
* CSV statistics export;
* PDF statistics export.

### API

* REST API;
* OpenAPI specification;
* Swagger UI;
* API testing directly from Swagger UI;
* Basic Authentication configuration through Swagger's `Authorize` button.

---

# Technology Stack

## Backend

| Technology          |    Version |
| ------------------- | ---------: |
| Java                |         21 |
| Spring Boot         |      4.1.1 |
| Gradle              | Kotlin DSL |
| Spring Web MVC      |          — |
| Spring Data JPA     |          — |
| Spring Data MongoDB |          — |
| Spring Security     |          — |
| SpringDoc OpenAPI   |          — |
| MapStruct           |          — |
| Flyway              |          — |
| PostgreSQL          |         18 |
| MongoDB             |          8 |
| Apache Kafka        |      7.5.3 |
| Apache ZooKeeper    |      7.5.3 |
| OpenPDF             |      2.0.3 |

## Frontend

| Technology       | Version |
| ---------------- | ------: |
| Angular          |  22.1.5 |
| Angular CLI      |  22.1.7 |
| TypeScript       |   6.0.3 |
| Angular Material |  22.1.5 |
| AG Grid Angular  |  36.1.0 |
| RxJS             |   7.8.2 |
| Tailwind CSS     |   4.3.3 |
| normalize.css    |   8.1.0 |
| Vitest           |   4.0.8 |

## Infrastructure

* Docker
* Docker Compose
* Apache Kafka
* Apache ZooKeeper

---

# Requirements

## Backend and Infrastructure

The following software is required to run the complete application:

* **Docker Desktop**
* **Docker Compose**

When running the project using Docker Compose, local installation of the following services is not required:

* PostgreSQL;
* MongoDB;
* Apache Kafka;
* Apache ZooKeeper;
* Gradle.

## Frontend

The frontend can be run separately from the backend.

Required software:

| Software    |       Version |
| ----------- | ------------: |
| Node.js     | 22.x or later |
| npm         | 10.x or later |
| Angular CLI |        22.1.7 |

The frontend uses **npm** as its package manager.

---

# Quick Start

## 1. Clone the Repository

```bash
git clone https://github.com/AndreyEvtukh/skillbox-java-spring.git
cd skillbox-java-spring
```

## 2. Start the Backend and Infrastructure

Start all backend and infrastructure services using Docker Compose:

```bash
docker compose up --build
```

Docker Compose starts the following containers:

| Container              | Description             |    Port |
| ---------------------- | ----------------------- | ------: |
| `skillbox-java-spring` | Spring Boot application |  `8082` |
| `skillbox-postgres`    | PostgreSQL database     |  `5432` |
| `skillbox-mongodb`     | MongoDB database        | `27017` |
| `skillbox-kafka`       | Apache Kafka broker     |  `9092` |
| `skillbox-zookeeper`   | Apache ZooKeeper        |  `2181` |

The backend is available at:

```text
http://localhost:8082
```

## 3. Start the Frontend

Open a separate terminal:

```bash
cd frontend
npm install
npm start
```

The frontend application is available at:

```text
http://localhost:4202
```

## 4. Open Swagger

Swagger UI is available at:

```text
http://localhost:8082/docs
```

The API can be tested directly from Swagger UI.

---

# Application Architecture

The application uses a layered architecture with a separate frontend, backend, relational database, statistics storage, and asynchronous messaging infrastructure.

```text
                    ┌──────────────────────┐
                    │      Angular 22       │
                    │       Frontend        │
                    │        :4202          │
                    └──────────┬───────────┘
                               │
                               │ REST API
                               ▼
                    ┌──────────────────────┐
                    │     Spring Boot       │
                    │       Backend         │
                    │        :8082          │
                    └──────┬─────────┬─────┘
                           │         │
                 Business │         │ Statistics events
                   data    │         │
                           ▼         ▼
                    ┌──────────┐  ┌──────────┐
                    │PostgreSQL│  │  Kafka   │
                    │  :5432   │  │  :9092   │
                    └──────────┘  └────┬─────┘
                                       │
                                       ▼
                              ┌─────────────────┐
                              │    Statistics   │
                              │     Consumer    │
                              └────────┬────────┘
                                       │
                                       ▼
                                ┌─────────────┐
                                │   MongoDB   │
                                │    :27017   │
                                └─────────────┘
```

## Main Data Flow

The main business data is stored in PostgreSQL:

```text
Angular Frontend
       │
       │ REST API
       ▼
Spring Boot
       │
       ▼
PostgreSQL
```

Statistical events are processed asynchronously:

```text
Spring Boot
       │
       ▼
    Kafka
       │
       ▼
Statistics Consumer
       │
       ▼
    MongoDB
```

This separation allows the main business operations to remain independent from statistical processing.

---

# Security

The application uses **Spring Security** with **HTTP Basic Authentication** and role-based access control.

Two roles are supported:

### USER

The `USER` role is intended for registered users and provides access to protected user-level functionality.

### ADMIN

The `ADMIN` role provides access to administrative functionality, including:

* user management;
* content management;
* statistics export;
* other administrator-only endpoints.

Administrative endpoints are protected with role-based authorization.

For example:

```java
.hasRole("ADMIN")
```

The application internally uses authorities in the following format:

```text
ROLE_USER
ROLE_ADMIN
```

## Initial Users

The application is initially populated with two registered users:

| Email               | Password   | Role    |
| ------------------- | ---------- | ------- |
| `admin@example.com` | `Password` | `ADMIN` |
| `user@example.com`  | `Password` | `USER`  |

These accounts can be used to test authentication and role-based access control.

The `ADMIN` account provides access to administrator-only endpoints, including user management and statistics export.

The `USER` account can be used to test functionality available to registered users.

Both accounts can be used to authenticate directly in **Swagger UI** using the **Authorize** button and HTTP Basic Authentication.


## Swagger Authentication

Protected endpoints can be tested directly from Swagger UI.

Click the **Authorize** button in Swagger UI and enter the user's Basic Authentication credentials.

```text
Swagger UI
     │
     ▼
┌─────────────────────┐
│      Authorize      │
│      Basic Auth     │
└──────────┬──────────┘
           │
           ▼
     ┌─────────────┐
     │ USER / ADMIN│
     └─────────────┘
```

---

# API Documentation

The complete REST API is documented using **Swagger / OpenAPI**.

All available endpoints, request parameters, request bodies, response models, HTTP status codes, and authentication requirements are described in the OpenAPI specification.

The API is **fully documented and ready for testing directly from Swagger UI**.

## Swagger UI

```text
http://localhost:8082/docs
```

Swagger UI allows developers to:

* view all available REST endpoints;
* inspect request parameters;
* inspect request and response DTOs;
* configure Basic Authentication;
* execute API requests;
* inspect HTTP responses;
* test protected endpoints using `USER` or `ADMIN` credentials.

## OpenAPI Specification

```text
http://localhost:8082/v1/api-docs
```

The OpenAPI specification can also be used by external API development and testing tools.

---

# Databases

## PostgreSQL

PostgreSQL is the **primary relational database** of the application.

It stores the main business entities:

* users;
* hotels;
* rooms;
* bookings.

### Docker Connection

```text
Database: skillbox_db
Username: postgres
Password: postgres
Host: db
Port: 5432
```

The application connects to PostgreSQL inside Docker using:

```text
jdbc:postgresql://db:5432/skillbox_db
```

When running Spring Boot locally outside Docker:

```text
jdbc:postgresql://localhost:5432/skillbox_db
```

## MongoDB

MongoDB is used as a dedicated storage for statistical events.

The statistics collection is:

```text
statistics
```

The following event types are currently stored:

```text
USER_REGISTERED
ROOM_BOOKED
```

Docker connection:

```text
mongodb://mongodb:27017/statistics
```

MongoDB UUID handling is configured using the standard UUID representation required by the MongoDB Java driver.

---

# Statistics

The application implements a dedicated statistics layer based on **Apache Kafka** and **MongoDB**.

Two statistical event types are currently supported.

## User Registration Event

The `USER_REGISTERED` event is generated when a new user is registered.

The event contains:

```text
eventId
eventType
occurredAt
userId
```

Example:

```json
{
  "eventId": "uuid",
  "eventType": "USER_REGISTERED",
  "occurredAt": "2026-09-08T10:00:00Z",
  "userId": "uuid"
}
```

## Room Booking Event

The `ROOM_BOOKED` event is generated when a room is successfully booked.

The event contains:

```text
eventId
eventType
occurredAt
userId
checkIn
checkOut
```

Example:

```json
{
  "eventId": "uuid",
  "eventType": "ROOM_BOOKED",
  "occurredAt": "2026-09-08T10:05:00Z",
  "userId": "uuid",
  "checkIn": "2026-09-10",
  "checkOut": "2026-09-15"
}
```

---

# Kafka

Apache Kafka is used as an **asynchronous event broker** for the statistics layer.

Two Kafka topics are used:

```text
user-registered
room-booked
```

The application publishes events to Kafka after the corresponding business operation is completed.

The statistics consumer listens to these topics and stores received events in MongoDB.

## Kafka Flow

```text
User Registration
       │
       ▼
USER_REGISTERED
       │
       ▼
user-registered
       │
       ▼
Kafka
       │
       ▼
Statistics Consumer
       │
       ▼
MongoDB
```

```text
Room Booking
       │
       ▼
ROOM_BOOKED
       │
       ▼
room-booked
       │
       ▼
Kafka
       │
       ▼
Statistics Consumer
       │
       ▼
MongoDB
```

## Kafka Connection

Kafka is exposed on the host at:

```text
localhost:9092
```

The Spring Boot application uses the Docker internal address:

```text
kafka:29092
```

ZooKeeper is available at:

```text
localhost:2181
```

---

# Statistics Export

Statistical data stored in MongoDB can be exported by administrators.

The export layer supports two formats:

* CSV;
* PDF.

## CSV Export

```http
GET /api/v1/statistics/csv
```

The endpoint generates and downloads:

```text
statistics.csv
```

The CSV file contains:

```text
eventId
eventType
occurredAt
userId
checkIn
checkOut
```

## PDF Export

```http
GET /api/v1/statistics/pdf
```

The endpoint generates and downloads:

```text
statistics.pdf
```

The PDF contains a tabular representation of the stored statistical events.

Both statistics export endpoints are available to administrators only.

---

# Database Migrations

**Flyway** is used to manage the PostgreSQL database schema.

Migration files are located in:

```text
src/main/resources/db/migration/
```

Flyway automatically checks and applies available migrations when the application starts.

Hibernate does not modify the database schema.

The following configuration is used:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
```

This means Hibernate only validates the existing database schema against the JPA entities.

Database structure changes are managed exclusively through Flyway migrations.

---

# Frontend

The frontend client is located in the:

```text
frontend/
```

directory.

The frontend is implemented using **Angular 22** and communicates with the Spring Boot backend through the REST API.

## Frontend Prerequisites

| Software    |       Version |
| ----------- | ------------: |
| Node.js     | 22.x or later |
| npm         | 10.x or later |
| Angular CLI |        22.1.7 |

The frontend uses **npm** as the package manager.

## Frontend Stack

| Technology       | Version |
| ---------------- | ------: |
| Angular          |  22.1.5 |
| TypeScript       |   6.0.3 |
| Angular Material |  22.1.5 |
| AG Grid Angular  |  36.1.0 |
| RxJS             |   7.8.2 |
| Tailwind CSS     |   4.3.3 |
| normalize.css    |   8.1.0 |
| Vitest           |   4.0.8 |

## Development Server

Start the frontend from the `frontend` directory:

```bash
cd frontend
npm install
npm start
```

The development server runs on:

```text
http://localhost:4202
```

## Production Build

Create a production build:

```bash
cd frontend
npm run build
```

The production build is generated in the Angular `dist` directory.

## Frontend Structure

```text
frontend/
├── src/
│   ├── app/
│   ├── assets/
│   ├── main.ts
│   └── styles.css
├── public/
├── angular.json
├── package.json
├── tsconfig.json
└── tsconfig.app.json
```

## Frontend / Backend Communication

The frontend and backend are developed as separate applications:

```text
┌──────────────────────────┐
│       Angular 22         │
│        Frontend          │
│         :4202            │
└────────────┬─────────────┘
             │
             │ REST API
             ▼
┌──────────────────────────┐
│       Spring Boot        │
│         Backend          │
│          :8082           │
└──────────────────────────┘
```

---

# Docker

The project includes:

* `Dockerfile` for building the Spring Boot application;
* `docker-compose.yml` for running the backend and infrastructure services.

Docker Compose provides the complete backend environment:

```text
                         Docker Compose
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
          ▼                   ▼                   ▼
   Spring Boot App        PostgreSQL           MongoDB
       :8082                 :5432              :27017
          │
          ▼
        Kafka
       :9092
          │
          ▼
      ZooKeeper
       :2181
```

## Docker Services

```text
skillbox-java-spring
skillbox-postgres
skillbox-mongodb
skillbox-kafka
skillbox-zookeeper
```

Internal Docker communication:

```text
PostgreSQL → jdbc:postgresql://db:5432/skillbox_db
MongoDB    → mongodb://mongodb:27017/statistics
Kafka      → kafka:29092
```

---

# Local Development Without Docker

The backend can also be started directly using Gradle.

For local development, PostgreSQL must be available on:

```text
localhost:5432
```

If the statistics functionality is enabled, MongoDB and Kafka must also be available locally.

## Start the Application

Linux / macOS:

```bash
./gradlew bootRun
```

Windows:

```powershell
.\gradlew.bat bootRun
```

## Build the Project

Linux / macOS:

```bash
./gradlew build
```

Windows:

```powershell
.\gradlew.bat build
```

## Run the JAR

After building:

```bash
java -jar build/libs/skillbox-java-spring-1.0.1.jar
```

---

# Project Structure

```text
skillbox-java-spring/
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   ├── assets/
│   │   ├── main.ts
│   │   └── styles.css
│   ├── public/
│   ├── angular.json
│   ├── package.json
│   ├── tsconfig.json
│   └── tsconfig.app.json
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/diploma/skillboxjavaspring/
│   │   │       ├── config/
│   │   │       ├── controllers/
│   │   │       ├── dto/
│   │   │       │   ├── booking/
│   │   │       │   ├── hotels/
│   │   │       │   ├── login/
│   │   │       │   ├── logout/
│   │   │       │   ├── room/
│   │   │       │   └── user/
│   │   │       ├── entity/
│   │   │       ├── exceptions/
│   │   │       ├── repositories/
│   │   │       ├── security/
│   │   │       ├── services/
│   │   │       └── statistics/
│   │   │           ├── config/
│   │   │           ├── controller/
│   │   │           ├── dto/
│   │   │           ├── entity/
│   │   │           ├── repository/
│   │   │           └── service/
│   │   │
│   │   └── resources/
│   │       ├── db/
│   │       │   └── migration/
│   │       └── application.yaml
│   │
│   └── test/
│
├── Dockerfile
├── docker-compose.yml
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
└── gradlew.bat
```

---

# Version Control

The main stable branch is:

```text
main
```

The current development branch is:

```text
development
```

Feature branches are used for individual tasks and functionality:

```text
feature/task-1-environment
feature/...
```

Completed versions of the project are merged into the `main` branch.

---

# Project Tasks

## Task 1 — Environment Setup

The initial project environment was prepared, including:

* Spring Boot;
* Spring Web MVC;
* Spring Data JPA;
* PostgreSQL;
* Flyway;
* MapStruct;
* Spring Security;
* SpringDoc OpenAPI;
* Docker;
* Docker Compose;
* PostgreSQL connection configuration;
* initial database migration.

## Task 9 — Hotel and Room Filtering

Implemented paginated search and filtering of hotels and rooms according to the specified criteria.

The filtering layer uses Spring Data JPA Specifications and supports pagination through Spring Data `Pageable`.

Implemented functionality includes:

* filtering by hotel ID;
* filtering by hotel name;
* filtering by rating;
* room filtering;
* pagination;
* total record count in paginated responses.

## Task 10 — Booking

Implemented room booking functionality, including:

* room validation;
* user validation;
* booking period validation;
* prevention of overlapping bookings;
* storage of booking information in PostgreSQL;
* generation of a room booking statistical event.

## Task 11 — Statistics Collection Layer

Implemented a separate statistics collection layer using Apache Kafka and MongoDB.

The implementation includes:

* Kafka integration;
* ZooKeeper integration;
* MongoDB integration;
* statistical event models;
* user registration events;
* room booking events;
* separate Kafka topics;
* Kafka consumer;
* MongoDB repository;
* statistics service;
* CSV export;
* PDF export;
* administrator-only statistics endpoints.

The complete event flow is:

```text
UserService / BookingService
          │
          ▼
        Kafka
          │
          ▼
StatisticsKafkaConsumer
          │
          ▼
StatisticsService
          │
          ▼
       MongoDB
          │
          ▼
StatisticsExportService
          │
       ┌──┴──┐
       ▼     ▼
      CSV   PDF
```

---

# License

This project was developed as part of a **Skillbox diploma project**.
