## Sleep Logger Application

### API

Implemented Sleep Logger API that currently consists from 3 endpoints:
* POST `/api/sleep/log`: creating sleep log
* GET `/api/sleep/last-night`: fetching information about last night sleep
* GET `/api/sleep/last-30-days`: getting the last 30-day averages

### Technologies
Using Java 11 and Spring Boot 2.7.17 as backend core.

Using PostgreSQL as data storage and Flyway as data migration mechanism.

Implemented unit-tests for Service layer and Repository layer with using of TestContainers (https://testcontainers.com/).
Test container technology was chosen to make unit testing independent of environment.

### Testing

To run unit tests use
``gradlew --info clean build test``
command inside `sleep` folder.

Created `api-test.http` script for Intellij Idea HTTP Client
(https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html)
for manual API checking.

### Application Start

To run everything, as before simply execute `docker-compose up`. To build and run, execute `docker-compose up --build`.






