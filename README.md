# Hotel Booking Service

A backend service for hotel booking with content management through an administrative CMS.

The application provides hotel search, room booking, user management, content administration, and statistical data collection using an event-driven architecture.

## Main Features

* search hotels by specified criteria;
* filter hotels by rating;
* view detailed hotel and room information;
* book rooms for a specified period;
* prevent booking conflicts for the same room;
* user registration and authentication;
* role-based access control for users and administrators;
* content management through the administrative part of the application;
* collection of statistical events;
* asynchronous statistics processing using Apache Kafka;
* storage of statistical events in MongoDB;
* export statistics to CSV;
* export statistics to PDF.

## Technology Stack

### Backend
* Java 21
* Spring Boot 4.1.1
* Gradle Kotlin DSL
* Spring Web MVC
* Spring Data JPA
* Spring Data MongoDB
* PostgreSQL 18
* MongoDB 8
* Flyway
* MapStruct
* Spring Security
* SpringDoc OpenAPI
* Apache Kafka
* Apache ZooKeeper
* OpenPDF
* Docker
* Docker Compose

### Frontend
* Angular 22
* TypeScript 6
* Angular Material
* AG Grid
* RxJS
* Tailwind CSS
* normalize.css

### Infrastructure

* Docker
* Docker Compose

## Requirements

The following software is required to run the project:

* Docker Desktop
* Docker Compose

When using Docker Compose, local installation of PostgreSQL, MongoDB, Kafka, ZooKeeper, and Gradle is not required.

## Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/AndreyEvtukh/skillbox-java-spring.git
cd skillbox-java-spring
```

### 2. Start the application

Start all application services using Docker Compose:

```bash
docker compose up --build
```

Docker Compose starts the following containers:

* `skillbox-java-spring` — Spring Boot application;
* `skillbox-postgres` — PostgreSQL database;
* `skillbox-mongodb` — MongoDB database;
* `skillbox-zookeeper` — Apache ZooKeeper;
* `skillbox-kafka` — Apache Kafka broker.

The Spring Boot application is available at:

```text
http://localhost:8082
```

PostgreSQL is available at:

```text
localhost:5432
```

MongoDB is available at:

```text
localhost:27017
```

Kafka is available at:

```text
localhost:9092
```

ZooKeeper is available at:

```text
localhost:2181
```

### 3. Stop the application

To stop all containers:

```bash
docker compose down
```

To stop containers and remove PostgreSQL and MongoDB data:

```bash
docker compose down -v
```

## Application Architecture

The application uses PostgreSQL as the primary relational database and MongoDB as a separate storage for statistical events.

Kafka is used to transfer statistical events asynchronously from the main application services to the statistics layer.

```text
                         Docker Compose
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
 Spring Boot             PostgreSQL             MongoDB
   :8082                    :5432                  :27017
        │
        │
        ├──────────────► Kafka
        │                :9092
        │                  │
        │                  ▼
        │          Statistics Consumer
        │                  │
        │                  ▼
        │               MongoDB
        │
        └──────────────► PostgreSQL
```

### Statistics Flow

User registration and room booking generate statistical events.

```text
User Registration
       │
       ▼
 USER_REGISTERED event
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
  ROOM_BOOKED event
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

The stored statistical data can then be exported to CSV or PDF:

```text
MongoDB
   │
   ▼
Statistics Service
   │
   ├──► CSV
   │
   └──► PDF
```

## Databases

### PostgreSQL

PostgreSQL is the primary relational database used to store application data.

It contains the main business entities, including:

* users;
* hotels;
* rooms;
* bookings.

Default Docker connection parameters:

```text
Database: skillbox_db
Username: postgres
Password: postgres
Host: db
Port: 5432
```

When the application runs inside Docker, it connects to PostgreSQL using:

```text
jdbc:postgresql://db:5432/skillbox_db
```

When Spring Boot runs locally outside Docker:

```text
jdbc:postgresql://localhost:5432/skillbox_db
```

### MongoDB

MongoDB is used as a separate storage for statistical events received through Kafka.

The `statistics` collection stores events such as:

```text
USER_REGISTERED
ROOM_BOOKED
```

Default Docker connection:

```text
mongodb://mongodb:27017/statistics
```

The MongoDB UUID representation is configured using the standard UUID representation required by the MongoDB Java driver.

## Statistics

The application implements a separate statistics layer based on Apache Kafka and MongoDB.

Two statistical event types are currently supported:

### User Registration

A `USER_REGISTERED` event contains:

```text
eventId
eventType
occurredAt
userId
```

### Room Booking

A `ROOM_BOOKED` event contains:

```text
eventId
eventType
occurredAt
userId
checkIn
checkOut
```

Events are published to separate Kafka topics:

```text
user-registered
room-booked
```

The statistics consumer receives events from Kafka and stores them in MongoDB.

## Statistics Export

Administrators can export all stored statistical events.

### CSV Export

```text
GET /api/v1/statistics/csv
```

The endpoint generates and downloads:

```text
statistics.csv
```

### PDF Export

```text
GET /api/v1/statistics/pdf
```

The endpoint generates and downloads:

```text
statistics.pdf
```

Statistics export endpoints are available to administrators only.

## Kafka

Apache Kafka is used as an asynchronous event broker for the statistics layer.

The application publishes two types of events:

```text
user-registered
room-booked
```

Kafka runs together with ZooKeeper in Docker Compose.

Internal application communication uses:

```text
kafka:29092
```

Kafka is exposed on the host at:

```text
localhost:9092
```

## Database Migrations

Flyway is used to manage the PostgreSQL database schema.

Migration files are located in:

```text
src/main/resources/db/migration/
```

Flyway automatically checks and applies available migrations when the application starts.

Hibernate is configured with:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
```

This means Hibernate does not modify the database schema. It only validates the existing schema against the JPA entities.

## API Documentation

The REST API is documented using SpringDoc OpenAPI.

Swagger UI:

```text
http://localhost:8082/docs
```

OpenAPI specification:

```text
http://localhost:8082/v1/api-docs
```

Administrative endpoints require authentication and the appropriate administrator role.

## Project Structure

```text
skillbox-java-spring/
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
│   │   │       │   
│   │   │       ├── entity/
│   │   │       ├── exceptions/
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
│   └── test/
│
├── Dockerfile
├── docker-compose.yml
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
└── gradlew.bat
```

## Docker

The project includes a `Dockerfile` for building the Spring Boot application and a `docker-compose.yml` for running all required infrastructure services.

```text
                         Docker Compose
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
          ▼                   ▼                   ▼
   Spring Boot App        PostgreSQL           MongoDB
       :8082                 :5432              :27017
          │
          │
          ▼
        Kafka
       :9092
          │
          │
          ▼
      ZooKeeper
       :2181
```

The Spring Boot application communicates with:

```text
PostgreSQL → jdbc:postgresql://db:5432/skillbox_db
MongoDB    → mongodb://mongodb:27017/statistics
Kafka      → kafka:29092
```

## Local Development Without Docker

To run the Spring Boot application directly using Gradle, PostgreSQL must be available on `localhost:5432`.

If statistics functionality is enabled, MongoDB and Kafka must also be available locally with the corresponding configuration.

Run the application:

```bash
./gradlew bootRun
```

For Windows:

```powershell
.\gradlew.bat bootRun
```

Build the project:

```bash
./gradlew build
```

For Windows:

```powershell
.\gradlew.bat build
```

After building, the application can be started using:

```bash
java -jar build/libs/skillbox-java-spring-1.0.1.jar
```

## Version Control

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

## Project Tasks

### Task 1. Environment Setup

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

### Task 9. Hotel and Room Filtering

Implemented paginated search and filtering of hotels and rooms according to the specified criteria.

The filtering layer uses Spring Data JPA specifications and supports pagination through Spring Data `Pageable`.

### Task 10. Booking

Implemented room booking functionality, including:

* room and user validation;
* booking period validation;
* prevention of overlapping bookings;
* storage of booking information in PostgreSQL.

### Task 11. Statistics Collection Layer

Implemented a separate statistics collection layer using Apache Kafka and MongoDB.

The implementation includes:

* Kafka and ZooKeeper integration;
* MongoDB integration;
* statistical event models;
* user registration events;
* room booking events;
* Kafka topics for statistical events;
* Kafka consumer for processing events;
* MongoDB repository and service;
* CSV export;
* PDF export;
* administrator-only statistics endpoints.

The implemented event flow is:

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

## Frontend

The frontend client is located in the `frontend` directory of the project.

The frontend application is built with **Angular 22** and communicates with the Spring Boot backend through the REST API.

### Frontend Prerequisites

The following software is required to build and run the frontend application:

| Software    |       Version |
| ----------- | ------------: |
| Node.js     | 22.x or later |
| npm         | 10.x or later |
| Angular CLI |        22.1.7 |

### Frontend Stack

| Technology       | Version |
| ---------------- | ------: |
| Angular          |  22.1.5 |
| TypeScript       |   6.0.3 |
| Angular Material |  22.1.5 |
| AG Grid Angular  |  36.1.0 |
| RxJS             |   7.8.2 |
| Tailwind CSS     |   4.3.3 |
| normalize.css    |   8.0.1 |
| Vitest           |   4.0.8 |

The frontend uses **npm** as the package manager.

### Development Server

The Angular development server runs on port `4202`.

Start the frontend from the `frontend` directory:

```bash
cd frontend
npm install
npm start
```

The frontend application will be available at:

```text
http://localhost:4202
```

### Production Build

To create a production build:

```bash
cd frontend
npm run build
```

The production build is generated in the Angular `dist` directory.

### Frontend Structure

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

The frontend and backend are developed as separate applications:

```text
┌──────────────────────────┐
│      Angular 22          │
│      Frontend            │
│      :4202               │
└────────────┬─────────────┘
             │
             │ REST API
             ▼
┌──────────────────────────┐
│      Spring Boot         │
│      Backend             │
│      :8082               │
└──────────────────────────┘
```


## License

This project was developed as part of a Skillbox diploma project.
