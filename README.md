# Stock Exchange Service

This project is a Stock Exchange Service implemented in Java using Spring Boot and Gradle. It provides a set of RESTful APIs to manage stocks and stock exchanges.

The data is stored in the `STOCK`,`STOCK_EXCHANGE`,`USERS` tables in an H2 database. During the bootstrap process, sample transaction data will be inserted.
For performance optimization, indexes (`idx_user_name` on USERS table `idx_stock_name` on `STOCK` table and `idx_stock_exchange_name` on `STOCK_EXCHANGE` table) have been created.

## Stock Controller API Endpoints

The `StockController` class manages operations related to stocks in the system. It integrates with the `StockService` to perform business logic operations and manages HTTP requests and responses for the following endpoints:

- `DELETE /api/v1/stock/{id}`: Deletes a stock by its ID.
- `PATCH /api/v1/stock/{id}?price={price}`: Updates the price of a stock.
- `POST /api/v1/stock`: Creates a new stock.

## Stock Exchange Controller API Endpoints

The `StockExchangeController` class manages operations related to stock exchanges in the system. It integrates with the `StockExchangeService` to perform business logic operations and handles HTTP requests and responses for the following endpoints:

- `DELETE /api/v1/stock-exchange/{name}?stockName={stockName}`: Deletes a stock from a specific stock exchange.
- `POST /api/v1/stock-exchange/{name}?stockName={stockName}`: Adds a stock to a specific stock exchange.
- `GET /api/v1/stock-exchange/{name}`: Retrieves all stocks associated with a specific stock exchange.


## AuthController API Endpoints

The `AuthController` is a REST controller that handles authentication related requests in the application. It provides endpoints for user sign in, sign up and deletion.

- `POST /api/auth/signIn`:
This endpoint is used to authenticate a user.

- `POST /api/auth/signUp`:
This endpoint is used to register a new user.

- `DELETE /api/auth/delete/{username}`:
This endpoint is used to delete a user.


## Installation

To build and run the service, use the following command:

```bash
docker compose up --build
```

## API Documentation

You can access the Swagger API documentation and usage at the following URL:

```
http://localhost:8080/swagger-ui/index.html
```

## H2 Database

You can log in to the H2 database console at the following URL:

```
http://localhost:8080/h2-console/login.do
```

The username and password can be found in the `application.properties` file.
You should sign in to get JWT token for authorization. After got JWT token you can use it in swagger-ui for authorization or you can use it in postman.
You can find the initial data insertion in the data.sql file located in the resources folder.
Initially created 3 exchanges with names NYSE, BIST, NASDAQ.
You can add more exchanges on h2 console or by changing data.sql.

```sql
INSERT INTO STOCK_EXCHANGE (name, description, live_in_market)
VALUES ('NYSE', 'New York Stock Exchange', false);
INSERT INTO STOCK_EXCHANGE ( name, description, live_in_market)
VALUES ('BIST', 'Borsa istanbul', false);
INSERT INTO STOCK_EXCHANGE ( name, description, live_in_market)
VALUES ('NASDAQ', 'Nasdaq', false);