package com.library.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.library.dao.BookDAO;
import com.library.dao.MemberDAO;
import com.library.model.Book;
import com.library.model.Member;

public class Menu {

    private BookDAO bookDAO = new BookDAO();
    private MemberDAO memberDAO = new MemberDAO();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n===== 📚 Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Delete Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Add Member");
            System.out.println("7. View All Members");
            System.out.println("8. Delete Member");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> viewAllBooks();
                    case 3 -> deleteBook();
                    case 4 -> issueBook();
                    case 5 -> returnBook();
                    case 6 -> addMember();
                    case 7 -> viewAllMembers();
                    case 8 -> deleteMember();
                    case 0 -> {
                        System.out.println("Goodbye! 👋");
                        return;
                    }
                    default -> System.out.println("❌ Invalid option, try again.");
                }
            } catch (SQLException e) {
                System.out.println("❌ Database error: " + e.getMessage());
            }
        }
    }

    private void addBook() throws SQLException {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        bookDAO.addBook(new Book(0, title, author, genre, true));
    }

    private void viewAllBooks() throws SQLException {
        List<Book> books = bookDAO.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            books.forEach(System.out::println);
        }
    }

    private void deleteBook() throws SQLException {
        System.out.print("Enter book ID to delete: ");
        int id = scanner.nextInt();
        bookDAO.deleteBook(id);
    }

    private void issueBook() throws SQLException {
        System.out.print("Enter book ID to issue: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter member ID: ");
        int memberId = scanner.nextInt();
        bookDAO.issueBook(bookId, memberId);
    }

    private void returnBook() throws SQLException {
        System.out.print("Enter book ID to return: ");
        int bookId = scanner.nextInt();
        bookDAO.returnBook(bookId);
    }

    private void addMember() throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        memberDAO.addMember(new Member(0, name, email));
    }

    private void viewAllMembers() throws SQLException {
        List<Member> members = memberDAO.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members found.");
        } else {
            members.forEach(System.out::println);
        }
    }

    private void deleteMember() throws SQLException {
        System.out.print("Enter member ID to delete: ");
        int id = scanner.nextInt();
        memberDAO.deleteMember(id);
    }
}