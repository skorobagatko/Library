package com.skorobahatko.library.bean;

import com.skorobahatko.library.entity.Author;
import com.skorobahatko.library.entity.Book;
import com.skorobahatko.library.entity.Category;
import com.skorobahatko.library.entity.Publisher;
import com.skorobahatko.library.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookList {

    private List<Book> books;

    public List<Book> getBooks() {
        if (books == null) {
            getBooksFromDB();
        }
        return books;
    }

    public List<Book> getBooksByCategory(String category) {
        if (books != null) {
            return books.stream()
                    .filter(b -> b.getCategory().getName().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }
        return getBooksByCategoryFromDB(category);
    }

    private List<Book> getBooksByCategoryFromDB(String category) {
        String sql = "SELECT b.name, b.publish_year, b.isbn, b.image, p.name as publisher, a.name AS author, c.name AS category " +
                "FROM library.book b " +
                "INNER JOIN library.author a ON b.author_id=a.id " +
                "INNER JOIN library.category c ON b.category_id=c.id " +
                "INNER JOIN library.publisher p ON b.publisher_id=p.id " +
                "WHERE category_id=?";
        List<Book> booksByCategory = new ArrayList<>();
        Connection conn = DatabaseUtil.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Book book = new Book();
                book.setName(rs.getString("name"));
                book.setIsbn(rs.getString("isbn"));
                book.setYear(rs.getInt("publish_year"));
                book.setImage(rs.getString("image"));
                booksByCategory.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksByCategory;
    }

    private void getBooksFromDB() {
        String sql = "SELECT b.name, b.publish_year, b.isbn, b.image, p.name as publisher, a.name AS author, c.name AS category " +
                "FROM library.book b " +
                "INNER JOIN library.author a ON b.author_id=a.id " +
                "INNER JOIN library.category c ON b.category_id=c.id " +
                "INNER JOIN library.publisher p ON b.publisher_id=p.id";
        books = new ArrayList<>();
        Connection conn = DatabaseUtil.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()) {
                Book book = new Book();
                book.setName(rs.getString("name"));
                book.setIsbn(rs.getString("isbn"));
                book.setAuthor(new Author(rs.getString("author")));
                book.setCategory(new Category(rs.getString("category")));
                book.setPublisher(new Publisher(rs.getString("publisher")));
                book.setYear(rs.getInt("publish_year"));
                book.setImage(rs.getString("image"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
