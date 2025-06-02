# ms-library-svc
This project is to demonstrate the simple Library System

Table of Content
-----
- [Overview](#overview)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
  - [GET /v1/book/book-list](#get-v1bookbook-list)
  - [POST /v1/book/register-book](#post-v1bookregister-book)
  - [POST /v1/book/deregister-book](#post-v1bookderegister-book)
  - [POST /v1/borrower/register-borrower](#post-v1borrowerregister-borrower)
  - [POST /v1/borrower/deregister-borrower](#post-v1borrowerderegister-borrower)
  - [POST /v1/borrower/borrow-book/{isbn}](#post-v1borrowerborrow-bookisbn)
  - [POST /v1/borrower/return-book/{isbn}](#post-v1borrowerreturn-bookisbn)

# Overview
This project is a Library System built with Spring Boot, Spring Data JPA, and PostgreSQL, designed to manage a collection of books and their borrowers in a clean, RESTful API architecture.

## The core focus of this system is on:
- Robust data validation
- Clean, self-documenting repository methods
- Enum handling, pagination, filtering
- Meaningful exception handling

#### The application follows best practices of layered architecture (Controller → Service → Repository) and provides a scalable foundation for any library/book-lending system.

## The project serves as a backend system for:
- Managing books in a library (title, author, ISBN, status)
- Tracking which borrower has checked out which book
- Supporting advanced search and filtering by book metadata
- Demonstrating clean API validation and custom exception handling

## It’s useful for:
- Learning Spring Boot with JPA and REST APIs
- Building backend services that include validation, pagination, and error handling
- A starting point for more complex systems (e.g. inventory, borrowing history, reservations)

## Key Goals:
- Define strong DTO-layer validation using Jakarta Bean Validation (@NotBlank, @Pattern, etc.)
- Support custom enums (Status) mapped to the database
- Use @PathVariable, @RequestParam, and @RequestBody effectively
- Filter with dynamic query methods like findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndStatus
- Enable pagination with Pageable and sort support
- Centralized error handling using @RestControllerAdvice
- Support self-defined exceptions (e.g., BookValidationException)
- Use modern SQL with PostgreSQL, UUID primary keys, and JPA mapping

## Functionality:
- **Book Management**
  - Add new books with constraints like:
    - Max 100 characters in title 
    - Alphanumeric ISBN following specific format (e.g. ABC12345)
  - Update existing book records 
  - Filter/search books by:
    - Title (case-insensitive partial match)
      Author (case-insensitive partial match)
  - View paginated lists of books with optional sorting
- **Borrower Management**
  - Create borrower records with name and email validation
- **Validation**
  - DTO-level field validation using annotations like:
    - @NotBlank
    - @Pattern
    - @Size
    - @Email
    - @Null (for system-generated fields)
  - Path variable and query parameter validation with @Validated and @Pattern
- **Exception Handling**
  - Global exception handler that:
    - Handles MethodArgumentNotValidException (for invalid request bodies)
    - Handles ConstraintViolationException (for invalid path/query values)
    - Handles custom exceptions like BookValidationException
    - Returns structured, user-friendly error messages
- **Database & Persistence**
  - PostgreSQL schema with two main tables:
    - t_book
    - t_borrower
  - UUID-based primary keys for all records

# Technology Stack
`Java 17+` `Spring Boot 3.x` `Spring Data JPA` `Hibernate Validator` `PostgreSQL` `Maven`

# Getting Started
Prerequisites
- Java 17 or higher
- Maven 3.8+
- PostgreSQL 17+

Installation
- clone repo from github: `git clone https://github.com/923Hao/ms-library-svc.git`
- configure database connection in application.yaml file here <ins>src/main/resources/application.yaml</ins>
```
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdbname
spring.datasource.username=youruser
spring.datasource.password=yourpassword
```
- build and run
```
mvn clean install
mvn spring-boot:run
```

# API Endpoints
### GET `/v1/book/book-list`

**Description**

Retrieves a paginated list of books filtered by optional title, author, and sorted by title or author. Case-insensitive search is supported.

**Query Parameters**

| Name     | Type   | Required | Description                                          |
|----------|--------|----------|------------------------------------------------------|
| `title`  | String | No       | Partial match on book title (case-insensitive)       |
| `author` | String | No       | Partial match on author name (case-insensitive)      |
| `sort`   | String | No       | Sorting field, e.g., `title`, `author`, `title,desc` |
| `page`   | Int    | No       | Page number (zero-based), default is `0`             |
| `size`   | Int    | No       | Page size, default is `10`                           |

**Example**
```
curl --location 'http://localhost:8080/v1/book/book-list?title=&author=&sort=title,asc&page=0&size=10'
```

**Sample Response**
```
{
    "content": [
        {
            "id": "ac2edae3-6096-428f-9736-b5962a054aa3",
            "isbn": "ABC12345",
            "title": "The Title of Book",
            "author": "The Author",
            "borrowedBy": null,
            "status": "A"
        }
    ],
    "page": {
        "size": 10,
        "number": 0,
        "totalElements": 1,
        "totalPages": 1
    }
}
```

**Validation & Error Response**

| HTTP Code | Reason                                                   | Example Message                              |
|-----------|----------------------------------------------------------|----------------------------------------------|
| `200`     | Gracefully handled on parameter values validation failed | `"Only alphanumeric characters are allowed"` |
| `500`     | Internal server error                                    | `"Unexpected error occurred"`                |

---

### POST `/v1/book/register-book`

**Description**

Registers a new book in the system with provided details such as ISBN, title, and author.

**Headers**
- Content-Type: application/json

**Body**

| Field  | Type   | Required | Description                                              |
|--------|--------|----------|----------------------------------------------------------|
| isbn   | String | Yes      | Unique identifier code for the book (e.g., `"ABC12345"`) |
| title  | String | Yes      | Title of the book                                        |
| author | String | Yes      | Author of the book                                       |

**Example**
```
curl --location 'http://localhost:8080/v1/book/register-book' \
--header 'Content-Type: application/json' \
--data '{
    "isbn": "ABC12345",
    "title": "The Title of Book",
    "author": "The Author"
}'
```

**Sample Response**
```
{
    "id": "ac2edae3-6096-428f-9736-b5962a054aa3",
    "isbn": "ABC12345",
    "title": "The Title of Book",
    "author": "The Author",
    "borrowedBy": null,
    "status": "A"
}
```

**Validation & Error Response**

| HTTP Code | Reason                                                   | Example Message                                                |
|-----------|----------------------------------------------------------|----------------------------------------------------------------|
| `200`     | Gracefully handled on parameter values validation failed | `"Only alphanumeric characters are allowed"`                   |
| `200`     | Gracefully handled on isbn validation                    | `"Entered title and author is not matched with existing ISBN"` |
| `500`     | Internal server error                                    | `"Unexpected error occurred"`                                  |

---

### POST `/v1/book/deregister-book`

**Description**

Removes or deregisters an existing book from the system using the provided book details.

**Headers**
- Content-Type: application/json

**Body**

| Field  | Type   | Required | Description                                                            |
|--------|--------|----------|------------------------------------------------------------------------|
| isbn   | String | Yes      | Unique identifier code for the book to deregister (e.g., `"ABC12345"`) |
| title  | String | Yes      | Title of the book                                                      |
| author | String | Yes      | Author of the book                                                     |

**Example**
```
curl --location 'http://localhost:8080/v1/book/deregister-book' \
--header 'Content-Type: application/json' \
--data '{
    "isbn": "ABC12345",
    "title": "The Title of Book",
    "author": "The Author"
}'
```

**Sample Response**
```
{
    "id": "ac2edae3-6096-428f-9736-b5962a054aa3",
    "isbn": "ABC12345",
    "title": "The Title of Book",
    "author": "The Author",
    "borrowedBy": null,
    "status": "I"
}
```

**Validation & Error Response**

| HTTP Code | Reason                                                   | Example Message                              |
|-----------|----------------------------------------------------------|----------------------------------------------|
| `200`     | Gracefully handled on parameter values validation failed | `"Only alphanumeric characters are allowed"` |
| `200`     | Gracefully handled on book validation                    | `"Book not found"`                           |
| `500`     | Internal server error                                    | `"Unexpected error occurred"`                |

---

### POST `/v1/borrower/register-borrower`

**Description**

Registers a new borrower in the system with their name and email address.
- Email address is expected to be unique.

**Headers**
- Content-Type: application/json

**Body**

| Field          | Type   | Required | Description                         |
|----------------|--------|----------|-------------------------------------|
| `name`         | String | Yes      | Full name of the borrower           |
| `emailAddress` | String | Yes      | Valid email address of the borrower |

**Example**
```
curl --location 'http://localhost:8080/v1/borrower/register-borrower' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Borrower Name",
    "emailAddress": "email@address.com"
}'
```

**Sample Response**
```
{
    "id": "4cfebeb1-b219-4c79-b6db-bae0fb05916f",
    "name": "Borrower Name",
    "emailAddress": "email@address.com",
    "status": "A"
}
```

**Validation & Error Response**

| HTTP Code | Reason                                                   | Example Message                                    |
|-----------|----------------------------------------------------------|----------------------------------------------------|
| `200`     | Gracefully handled on parameter values validation failed | `"Only alphanumeric characters are allowed"`       |
| `200`     | Gracefully handled on email address validation           | `"Borrower with this email address already exist"` |
| `500`     | Internal server error                                    | `"Unexpected error occurred"`                      |

---

### POST `/v1/borrower/deregister-borrower`

**Description**

Deregisters a borrower from the system using their name and email address.

**Headers**
- Content-Type: application/json

**Body**

| Field          | Type   | Required | Description                              |
|----------------|--------|----------|------------------------------------------|
| `name`         | String | Yes      | Full name of the borrower                |
| `emailAddress` | String | Yes      | Valid and existing email of the borrower |

**Example**
```
curl --location 'http://localhost:8080/v1/borrower/deregister-borrower' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Borrower Name",
    "emailAddress": "email@address.com"
}'
```

**Sample Response**
```
{
    "id": "4cfebeb1-b219-4c79-b6db-bae0fb05916f",
    "name": "Borrower Name",
    "emailAddress": "email@address.com",
    "status": "I"
}
```

**Validation & Error Response**

| HTTP Code | Reason                                                    | Example Message                              |
|-----------|-----------------------------------------------------------|----------------------------------------------|
| `200`     | Gracefully handled on parameter values validation failed  | `"Only alphanumeric characters are allowed"` |
| `200`     | Gracefully handled on pending return validation           | `"Borrower still have pending return book"`  |
| `200`     | Gracefully handled on existence validation                | `"Borrower does not exist"`                  |
| `500`     | Internal server error                                     | `"Unexpected error occurred"`                |

---

### POST `/v1/borrower/borrow-book/{isbn}`

**Description**

Allows a registered borrower to borrow a book by specifying the book's ISBN and providing borrower details (name and email).
The system will validate that:
- The borrower exists and is active
- The book exists and is available

Note that there is no validation on the number of book borrowed by each borrower.

**Headers**
- Content-Type: application/json

**Path Variable**

| Name   | Type   | Required | Description                     |
|--------|--------|----------|---------------------------------|
| `isbn` | String | Yes      | ISBN of the book to be borrowed |

**Body**

| Field          | Type   | Required | Description                              |
|----------------|--------|----------|------------------------------------------|
| `name`         | String | Yes      | Full name of the borrower                |
| `emailAddress` | String | Yes      | Registered email address of the borrower |

**Example**
```
curl --location 'http://localhost:8080/v1/borrower/borrow-book/ABC12345' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Borrower Name",
    "emailAddress": "email@address.com"
}'
```

**Sample Response**
```
{
    "id": "234bde3a-01f3-40e3-ae4b-3a3d4ca13cec",
    "isbn": "ABC12345",
    "title": "The Title of Book",
    "author": "The Author",
    "borrowedBy": "c6de3294-9b35-424c-a484-73322f3b01e5",
    "status": "A"
}
```

**Validation & Error Response**

| HTTP Code | Reason                                                   | Example Message                                |
|-----------|----------------------------------------------------------|------------------------------------------------|
| `200`     | Gracefully handled on parameter values validation failed | `"Only alphanumeric characters are allowed"`   |
| `200`     | Gracefully handled on availability validation            | `"No books available to borrow for this ISBN"` |
| `200`     | Gracefully handled on existence validation               | `"Book not found"`                             |
| `500`     | Internal server error                                    | `"Unexpected error occurred"`                  |

---

### POST `/v1/borrower/return-book/{isbn}`

**Description**

Allows a borrower to return a book they previously borrowed by providing the ISBN and their borrower details.
The system validates the borrower and book status before marking the book as returned.

**Headers**
- Content-Type: application/json

**Path Variable**

| Name   | Type   | Required | Description                     |
|--------|--------|----------|---------------------------------|
| `isbn` | String | Yes      | ISBN of the book being returned |

**Body**

| Field          | Type   | Required | Description                   |
|----------------|--------|----------|-------------------------------|
| `name`         | String | Yes      | Full name of the borrower     |
| `emailAddress` | String | Yes      | Email address of the borrower |

**Example**
```
curl --location 'http://localhost:8080/v1/borrower/return-book/ABC12345' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Borrower Name",
    "emailAddress": "email@address.com"
}'
```

**Sample Response**
```
{
    "id": "234bde3a-01f3-40e3-ae4b-3a3d4ca13cec",
    "isbn": "ABC12345",
    "title": "The Title of Book",
    "author": "The Author",
    "borrowedBy": null,
    "status": "A"
}
```

**Validation & Error Response**

| HTTP Code | Reason                                                   | Example Message                              |
|-----------|----------------------------------------------------------|----------------------------------------------|
| `200`     | Gracefully handled on parameter values validation failed | `"Only alphanumeric characters are allowed"` |
| `200`     | Gracefully handled on availability validation            | `"Borrower does not borrowing this book"`    |
| `200`     | Gracefully handled on existence validation               | `"Book not found"`                           |
| `500`     | Internal server error                                    | `"Unexpected error occurred"`                |
