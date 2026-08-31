# Сервис бронирования отелей

Бэкенд-составляющая сервиса бронирования отелей с возможностью управления контентом через административную панель CMS.

Основные возможности приложения:

* поиск отелей по заданным критериям;
* просмотр информации об отелях;
* поиск и фильтрация отелей по рейтингу;
* бронирование отеля на определённый период;
* выставление пользователями оценок от 1 до 5;
* управление контентом через административную часть приложения;
* формирование статистики по работе сервиса;
* выгрузка статистики в формате CSV.

## Стек

* Java 21
* Spring Boot 4.1.1
* Gradle Kotlin DSL
* Spring Web MVC
* Spring Data JPA
* PostgreSQL 18
* Flyway
* MapStruct
* Spring Security
* SpringDoc OpenAPI
* Docker
* Docker Compose

## Требования

Для запуска проекта необходимы:

* Docker Desktop
* Docker Compose

При использовании Docker локальная установка PostgreSQL и Gradle не требуется.

## Быстрый старт

### 1. Клонирование репозитория

```bash
git clone https://github.com/AndreyEvtukh/skillbox-java-spring.git
cd skillbox-java-spring
```

### 2. Запуск приложения

Запустите приложение вместе с PostgreSQL:

```bash
docker compose up --build
```

Docker Compose создаст два контейнера:

* `skillbox-java-spring` — Spring Boot приложение;
* `skillbox-postgres` — PostgreSQL 18.

PostgreSQL будет доступен на:

```text
localhost:5432
```

Spring Boot приложение будет доступно на:

```text
http://localhost:8082
```

### 3. Остановка приложения

Для остановки контейнеров:

```bash
docker compose down
```

Для остановки контейнеров с удалением данных PostgreSQL:

```bash
docker compose down -v
```

## База данных

Для PostgreSQL используются следующие параметры:

```text
Database: skillbox_db
Username: postgres
Password: postgres
Host: db
Port: 5432
```

При запуске приложения через Docker Compose Spring Boot подключается к PostgreSQL по адресу:

```text
jdbc:postgresql://db:5432/skillbox_db
```

При локальном запуске Spring Boot вне Docker используется:

```text
jdbc:postgresql://localhost:5432/skillbox_db
```

## Миграции базы данных

Для управления структурой базы данных используется Flyway.

Миграции находятся в:

```text
src/main/resources/db/migration/
```

При запуске приложения Flyway автоматически проверяет и применяет доступные миграции.

Hibernate работает в режиме:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
```

Это означает, что Hibernate не изменяет структуру базы данных, а только проверяет её соответствие сущностям приложения.

## API документация

Для документирования REST API используется SpringDoc OpenAPI.

Swagger UI:

```text
http://localhost:8082/docs
```

OpenAPI specification:

```text
http://localhost:8082/v1/api-docs
```

## Структура проекта

```text
skillbox-java-spring/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/diploma/skillboxjavaspring/
│   │   └── resources/
│   │       ├── db/
│   │       │   └── migration/
│   │       └── application.yaml
│   └── test/
├── gradle/
├── Dockerfile
├── docker-compose.yml
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
└── gradlew.bat
```

## Docker

Проект содержит `Dockerfile` для сборки Spring Boot приложения и `docker-compose.yml` для запуска приложения вместе с PostgreSQL.

Архитектура запуска:

```text
                 Docker Compose
                       │
          ┌────────────┴────────────┐
          │                         │
          ▼                         ▼
  Spring Boot application      PostgreSQL 18
       :8082                       :5432
          │                         ▲
          └────── JDBC ─────────────┘
              db:5432/skillbox_db
```

## Локальный запуск без Docker

Для запуска Spring Boot непосредственно из Gradle необходимо, чтобы PostgreSQL был доступен на `localhost:5432`.

Запуск приложения:

```bash
./gradlew bootRun
```

Для Windows:

```powershell
.\gradlew.bat bootRun
```

Сборка JAR:

```bash
./gradlew build
```

Для Windows:

```powershell
.\gradlew.bat build
```

После сборки приложение запускается командой:

```bash
java -jar build/libs/skillbox-java-spring-1.0.1.jar
```

## Версионирование

Основная стабильная ветка проекта:

```text
main
```

Ветка текущей разработки:

```text
development
```

Для отдельных заданий и функциональности используются feature-ветки:

```text
feature/task-1-environment
feature/...
```

Завершённые версии проекта публикуются в ветке `main`.

## Задания проекта

### Задание 1. Подготовка окружения

На данном этапе подготовлены:

* проект Spring Boot;
* Spring Web MVC;
* Spring Data JPA;
* PostgreSQL;
* Flyway;
* MapStruct;
* Spring Security;
* SpringDoc OpenAPI;
* Dockerfile;
* Docker Compose;
* конфигурация подключения к PostgreSQL;
* миграция базы данных.

Для проверки запуска проекта достаточно выполнить:

```bash
docker compose up --build
```

После успешного запуска приложение доступно по адресу:

```text
http://localhost:8082
```
