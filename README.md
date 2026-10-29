# Tag-Me Bank · backend

API REST de un banco ficticio: clientes, cuentas, tarjetas, movimientos y pagos. Es la mitad bancaria del proyecto Tag-Me; la tienda ([store-backend](https://github.com/Tag-Me-DAW2/store-backend)) le cobra con tarjeta a través de ella. Frontend: [bank-frontend](https://github.com/Tag-Me-DAW2/bank-frontend).

**Stack:** Java 21 · Spring Boot 3.5 · Spring Data JPA · MariaDB · Flyway · JWT (jjwt) · password4j (argon2) · JUnit y Testcontainers.

## Arquitectura

Capas con el dominio en el centro; la persistencia y el framework dependen del dominio, no al revés.

```
controller   endpoints REST, modelos web y mappers
usecase      casos de uso (pago con tarjeta, transferencia)
domain       modelos, servicios, excepciones y repositorios (interfaces)
persistence  entidades JPA, DAOs y repositorios que implementan los del dominio
web          filtros: autenticación JWT, CORS y rutas sin token
spring       configuración y manejo global de errores
```

## Endpoints

| Recurso | Rutas |
|---|---|
| `/auth` | `POST /login`, `POST /logout` |
| `/clients` | `GET /{id}` |
| `/bank-accounts` | `GET /{id}`, `GET /client/{clientId}`, `GET /iban/{iban}` |
| `/credit-cards` | `GET /{id}`, `POST /authorize` |
| `/movements` | `GET /{id}`, `GET /account/{accountId}`, `GET /monthly` |
| `/payments` | `POST /credit-card`, `POST /transfer` |

Todo requiere `Authorization: Bearer <token>` salvo `/auth/login` y `/payments/credit-card`.

## Arrancar en local

Necesita Java 21 y una MariaDB con la base `db_tagme_bank`. El perfil `dev` escucha en el puerto 8081 y Flyway crea las tablas y datos de ejemplo al iniciar.

```bash
./mvnw spring-boot:run
```

## Tests

```bash
./mvnw test
```

Los tests de persistencia usan Testcontainers, así que necesitan Docker.
