# 📚 Library Management System (LMS) Web :

This is a full-stack **Library Management System** built using **Java**, **Spring Boot**, **JPA**, and a frontend with **HTML**, **CSS**, **JavaScript**, and **Thymeleaf**. The system allows patrons to borrow books from a library and provides a responsive web UI for all interactions.

---

## 🚀 Features

- Add Book
- Delete Book
- Add Patron
- Delete Patron
- Borrow Book
- Return Book
- List Borrowed Books by Patron ID
- View All Books
- View All Patrons

---

## 🧩 Entities & Relationships

### 1. 📘 Book
Represents a book in the library.

**Attributes:**
- `bookId` (Primary Key)
- `title`
- `author`
- `quantity`
- `available` (boolean)
- `totalQuantity`

**Book Lifecycle:**
- A book can be borrowed by multiple patron till quantity > 0.
- Quantity is reduced on borrow.
- When quantity reaches 0, the book is unavailable.

---

### 2. 👤 Patron
Represents a library member.

**Attributes:**
- `pid` (Primary Key)
- `name`

A patron can borrow multiple books at the same time.

---

### 3. 🔗 Borrow
Join table between Book and Patron.

**Attributes:**
- `id` (Primary Key)
- `name`(Foreign Key)
- `book` (Foreign Key)
- `patron` (Foreign Key)

Each record represents a single borrow action.

---

## 🛠️ Technologies Used

- **Backend:** Java, Spring Boot, Spring Data JPA
- **Frontend:** HTML, CSS, JavaScript, Thymeleaf
- **Database:** MySQL 

---

## 🗃️ Database Configuration

- **Database Name:** `libraryStore`

### Setup Steps:
CREATE DATABASE library;
USE library;

## 🧑‍💻 Getting Started
Clone the repository.
Set up your database (library) in MySQL.
Update DB credentials in application.properties.
Run the Spring Boot application.
Access the proejct via your browser at http://localhost:8082.
