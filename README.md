# Customer Transaction Service

## 1. Understanding of the Problem

This project implements a small transaction-processing service using Java and Spring Boot.

The service manages customer transactions. Each transaction contains:

- Transaction ID
- Customer ID
- Amount
- Currency
- Transaction Type
- Transaction Status

The application provides four required operations:

1. Create a transaction
2. Get a transaction by Transaction ID
3. Update the status of an existing transaction
4. Get all transactions for a Customer ID

The application uses Spring Data JPA with the H2 embedded database for storing transactions.

## 2. Assumptions

The following assumptions were made while implementing the service:

- Transaction ID uniquely identifies a transaction.
- Transaction ID and Customer ID cannot be blank.
- Transaction amount must be greater than zero.
- Currency, transaction type, and initial transaction status are required.
- A transaction cannot be created if its Transaction ID already exists.
- Transaction status can be PENDING, COMPLETED, FAILED, or CANCELLED when updating an existing transaction.
- A transaction whose status is COMPLETED, FAILED, or CANCELLED cannot be changed again.
- The application is intended as a simple transaction service and does not include authentication or authorization.

## 3. Validation Rules

The following validation rules are implemented during transaction creation:

- Transaction ID is required and cannot be blank.
- Customer ID is required and cannot be blank.
- Amount is required and must be greater than zero.
- Currency is required and cannot be blank.
- Transaction type is required and cannot be blank.
- Transaction status is required and cannot be blank.
- Transaction ID must be unique.

For status updates:

- The new status is required and cannot be blank.
- The new status must be one of:
  - PENDING
  - COMPLETED
  - FAILED
  - CANCELLED
- A transaction whose current status is COMPLETED, FAILED, or CANCELLED cannot be changed again.

These business validations are implemented in the service layer.

## 4. API Endpoints

### Create Transaction

**POST** `/transactions`

Example request:

```json
{
  "transactionId": "TXN001",
  "customerId": "CUST001",
  "amount": 1500.50,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "transactionStatus": "PENDING"
}
~~~
### Get Transaction

**GET** `/transactions/{transactionId}`

Example request:

GET /transactions/TXN001

Returns the transaction with the given Transaction ID.

### Update Transaction Status

**PUT** `/transactions/{transactionId}/status`

Example request:

PUT /transactions/TXN001/status?status=COMPLETED

The status can be:

- PENDING
- COMPLETED
- FAILED
- CANCELLED

A transaction that is already COMPLETED, FAILED, or CANCELLED cannot be changed again.

### Get Customer Transactions

**GET** `/transactions/customer/{customerId}`

Example request:

GET /transactions/customer/CUST001

Returns all transactions belonging to the specified customer.

## 5. Testing

The project contains meaningful automated tests covering the main transaction operations and validation rules.

The tests cover:

- Successful transaction creation
- Rejection of a transaction with an invalid amount
- Rejection of duplicate transaction IDs
- Exception when a transaction does not exist
- Successful transaction status update
- Rejection of changing a transaction after a final status
- Retrieving transactions for a customer

The tests were executed using:

.\mvnw.cmd clean test

The test suite completed successfully with BUILD SUCCESS.

## 6. Design Notes

The application uses a layered design:

- **Transaction** - JPA entity representing a transaction.
- **TransactionRepository** - Spring Data JPA repository used for database operations.
- **TransactionService** - Contains business logic and validation.
- **TransactionController** - Exposes the REST API endpoints.
- **GlobalExceptionHandler** - Provides consistent HTTP error responses for validation and runtime errors.

Business validation is kept in the service layer so that the rules are applied consistently.

## 7. Known Limitations

This is a simple transaction-processing exercise. The application does not currently include:

- Authentication or authorization
- Advanced transaction state management
- Pagination for customer transaction results
- Production database configuration
- More detailed API-level validation
- Comprehensive API documentation

## 8. Improvements With More Time

With more time, I would improve the application by adding:

- More detailed validation for currency, transaction type, and transaction status.
- More specific custom exceptions for different error cases.
- A standardized error response structure.
- Pagination for customer transaction lookup.
- Additional controller-level integration tests.
- Production database configuration, logging, monitoring, and other production-readiness features.

## 9. AI Usage Disclosure

AI assistance was used during development to help with understanding the assignment requirements, generating initial implementation ideas, improving validation logic, and creating test cases.

The generated suggestions were reviewed and adapted to the requirements of this exercise.

I reviewed the suggested business rules and decided which validations and transaction status rules were appropriate for this implementation.

The AI-generated suggestions were not accepted blindly. The implementation was checked by compiling the project and running the automated test suite.

The final implementation was verified using:

.\mvnw.cmd clean test

The final test execution completed successfully with BUILD SUCCESS.

## 10. Conclusion

The Customer Transaction Service implements the four required transaction operations with validation, persistence using Spring Data JPA and H2, sensible error handling, and automated tests covering the main business scenarios.