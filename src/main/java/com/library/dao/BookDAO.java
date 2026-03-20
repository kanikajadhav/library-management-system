package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.db.DBConnection;
import com.library.model.Book;

public class BookDAO {

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (title, author, genre) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getGenre());
            stmt.executeUpdate();
            System.out.println("✅ Book added successfully!");
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getBoolean("is_available")
                ));
            }
        }
        return books;
    }

    public void deleteBook(int id) throws SQLException {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅ Book deleted successfully!");
        }
    }

    public void issueBook(int bookId, int memberId) throws SQLException {
        String updateBook = "UPDATE books SET is_available = FALSE WHERE id = ?";
        String insertIssue = "INSERT INTO issued_books (book_id, member_id, issue_date) VALUES (?, ?, CURDATE())";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement(updateBook);
                 PreparedStatement stmt2 = conn.prepareStatement(insertIssue)) {
                stmt1.setInt(1, bookId);
                stmt1.executeUpdate();
                stmt2.setInt(1, bookId);
                stmt2.setInt(2, memberId);
                stmt2.executeUpdate();
                conn.commit();
                System.out.println("✅ Book issued successfully!");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void returnBook(int bookId) throws SQLException {
        String updateBook = "UPDATE books SET is_available = TRUE WHERE id = ?";
        String updateIssue = "UPDATE issued_books SET return_date = CURDATE() WHERE book_id = ? AND return_date IS NULL";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement(updateBook);
                 PreparedStatement stmt2 = conn.prepareStatement(updateIssue)) {
                stmt1.setInt(1, bookId);
                stmt1.executeUpdate();
                stmt2.setInt(1, bookId);
                stmt2.executeUpdate();
                conn.commit();
                System.out.println("✅ Book returned successfully!");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}