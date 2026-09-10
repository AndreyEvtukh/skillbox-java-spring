# Frontend

Frontend project part **SkillBox — Hotels API**.

Application developed with **Angular 22** and communicates with backend Spring Boot API.

## Tech Stack

* Angular 22.1.7
* TypeScript 6
* Angular Material 22
* AG Grid 36
* Tailwind CSS 4
* RxJS 7
* Vitest 4
* Prettier 3

## Development server

Install dependencies:

```bash
yarn install
```

Start the development server:

```bash
yarn start
```

The application will be available at:

```text
http://localhost:4202/
```

The application automatically reloads when source files are modified.

## Backend API

The frontend communicates with the backend through:

```text
http://localhost:8082/api/v1
```

Make sure the backend application is running before using the frontend.

## Default users

The application provides two pre-configured users for local development and testing:

| Role  | Email               | Password   |
| ----- | ------------------- | ---------- |
| USER  | `user@example.com`  | `Password` |
| ADMIN | `admin@example.com` | `Password` |

The administrator account has access to administrative functionality and user management.

## Build

Build the project:

```bash
yarn build
```

The production build artifacts are generated in the `dist/` directory.

For development build with automatic rebuild:

```bash
yarn watch
```

## Project features

The frontend provides interfaces for:

* User authentication and authorization
* User management
* Hotel management
* Hotel filtering and pagination
* Room management and availability
* Booking management
* Statistics
* Statistics export to CSV/PDF
* Role-based access control
* Administrative operations

## Code formatting

The project uses **Prettier** for code formatting.

Run Prettier manually:

```bash
npx prettier --write .
```

## Available scripts

| Command      | Description                           |
| ------------ | ------------------------------------- |
| `yarn start` | Start development server on port 4202 |
| `yarn build` | Build the application                 |
| `yarn watch` | Build in development mode with watch  |
| `yarn ng`    | Run Angular CLI commands              |

## Additional resources

* [Angular](https://angular.dev/)
* [Angular Material](https://material.angular.dev/)
* [AG Grid](https://www.ag-grid.com/)
* [Tailwind CSS](https://tailwindcss.com/)
* [Vitest](https://vitest.dev/)
