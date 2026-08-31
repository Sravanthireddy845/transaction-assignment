# AI Usage Disclosure

## Tools Used

I used ChatGPT as an AI coding assistant while working on this project.

## How AI Was Used

ChatGPT was used to help me understand the requirements, plan the implementation, explain Spring Boot and Java concepts, and assist with writing and reviewing parts of the transaction service and automated tests.

## Significant AI Suggestions

The AI suggested approaches for implementing the four required transaction operations, including transaction creation, retrieving a transaction, updating transaction status, and retrieving transactions for a customer.

It also suggested validation rules such as checking required fields, ensuring the transaction amount is greater than zero, preventing duplicate transaction IDs, and validating transaction statuses.

## What I Changed or Verified

I reviewed the suggested implementation and used the requirements from the engineering challenge to decide which validations and business rules to include.

I also verified the implementation by running the Maven test command and checking that the automated tests completed successfully with BUILD SUCCESS.

## AI Limitations and Corrections

I did not rely on AI output without testing it. I checked the generated code by compiling and running the project's automated tests.

Where necessary, I adjusted the implementation and documentation to match the requirements of the starter project.

## Final Verification

The final implementation was verified using:

    mvnw.cmd clean test

The test execution completed successfully with BUILD SUCCESS.