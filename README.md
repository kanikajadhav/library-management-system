# 📚 Library Management System

A full-stack Library Management System built with Java and MySQL, supporting complete book and member management operations.

## 🛠️ Technologies Used
- **Java 21** — Core application logic
- **MySQL** — Database storage and retrieval
- **JDBC** — Database connectivity
- **Maven** — Dependency management

## ✨ Features
- Add, view, and delete books
- Add, view, and delete members
- Issue books to members
- Return books with date tracking
- Transaction management with rollback support

## 🗄️ Database Setup
1. Open MySQL and run the script located at `database/library_db.sql`

## ▶️ How to Run
1. Clone the repository
2. Set your MySQL password in `src/main/java/com/library/db/DBConnection.java`
3. Run database setup script
4. Execute using Maven:
```bash
mvn compile
mvn exec:java "-Dexec.mainClass=com.library.App"
```

## 📁 Project Structure
```
src/main/java/com/library/
├── db/         → Database connection
├── model/      → Book and Member objects
├── dao/        → Database operations
└── ui/         → CLI Menu interface
```