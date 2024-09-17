# Library Management System

This project is a **Library Management System** implemented in Java (IDE used is NetBeans). It demonstrates proficiency in Java concepts like **inheritance**, **exception handling**, and **database connectivity** using **JDBC**. The system allows users to manage books and DVDs, perform operations like adding, borrowing, and returning items, and interact with a MySQL database.

## Features
- **Object-Oriented Design**: Utilizes inheritance, with a base `Item` class and derived `Book` and `DVD` classes.
- **Exception Handling**: Custom exceptions for specific scenarios, such as `ItemUnavailableException` and `InvalidItemTypeException`.
- **Database Connectivity**: Uses JDBC to connect to a MySQL database for storing and retrieving item details and borrowing history.
- **Command-Line Interface**: Simple CLI to interact with the system, allowing users to add, search, borrow, and return items.

## Class Hierarchy
- **`Item` Class**: 
  - Base class representing a library item (e.g., books, DVDs).
  - Common attributes: `id`, `title`, `available`.
  - Methods: `borrow()`, `returnItem()`, `displayDetails()`.
  
- **`Book` Class**:
  - Inherits from `Item`.
  - Additional attributes: `author`, `genre`, `isbn`.
  - Overrides `displayDetails()` to include book-specific details.
  
- **`DVD` Class**:
  - Inherits from `Item`.
  - Additional attributes: `director`, `duration`.
  - Overrides `displayDetails()` to include DVD-specific details.

## Custom Exceptions
- **`ItemUnavailableException`**: Thrown when trying to borrow an unavailable item.
- **`InvalidItemTypeException`**: Thrown when attempting to add an item of an invalid type.

## Database Connectivity
- **MySQL** is used for database management.
- **JDBC** handles database connections and CRUD operations (Create, Read, Update, Delete).(You have to add JDBC in you IDE.)
- Database stores items (books, DVDs) and borrowing history.

## Project Structure

```bash
📂 librarymanage/
 ├── 📄 Book.java             # Book class, inherits from Item
 ├── 📄 DVD.java              # DVD class, inherits from Item
 ├── 📄 Item.java             # Abstract base class representing library items
 ├── 📄 ItemUnavailableException.java  # Custom exception for unavailable items
 ├── 📄 InvalidItemTypeException.java  # Custom exception for invalid item types
 ├── 📄 DatabaseConnector.java  # Handles database connectivity using JDBC
 ├── 📄 LibraryManage.java      # Main class, includes CLI interface for managing the library
