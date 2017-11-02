package com.skorobahatko.library.bean;

import com.skorobahatko.library.entity.*;
import com.skorobahatko.library.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookList {

    private List<Book> books;

    public List<Book> getBooks() {
        if (books == null) {
            String sql = "SELECT b.id, b.name, b.publish_year, b.content, b.image, p.name as publisher, " +
                    "a.name AS author, c.name AS category " +
                    "FROM library.book b " +
                    "INNER JOIN library.author a ON b.author_id=a.id " +
                    "INNER JOIN library.category c ON b.category_id=c.id " +
                    "INNER JOIN library.publisher p ON b.publisher_id=p.id;";
            books = getBooksFromDB(sql);
        }
        return books;
    }

    public List<Book> getBooksByCategory(String categoryId) {
        if (books != null) {
            return books.stream()
                    .filter(b -> b.getCategory().getId() == Long.parseLong(categoryId))
                    .collect(Collectors.toList());
        }
        return getBooksByCategoryFromDB(categoryId);
    }

    public List<Book> getBooksByAuthor(String authorId) {
        if (books != null) {
            return books.stream()
                    .filter(b -> b.getAuthor().getId() == Long.parseLong(authorId))
                    .collect(Collectors.toList());
        }
        return getBooksByAuthorFromDB(authorId);
    }

    public List<Book> getBooksByQuery(String query, QueryType queryType) {
        String like = "%" + query + "%";
        String sql = "SELECT b.id, b.name, b.publish_year, b.content, b.image, p.name as publisher, " +
                "a.name AS author, c.name AS category " +
                "FROM library.book b " +
                "INNER JOIN library.author a ON b.author_id=a.id " +
                "INNER JOIN library.category c ON b.category_id=c.id " +
                "INNER JOIN library.publisher p ON b.publisher_id=p.id " +
                "WHERE ";
        if (queryType == QueryType.AUTHOR) {
            sql += "a.name LIKE '" + like + "';";
        } else {
            sql += "b.name LIKE '" + like + "';";
        }
        return getBooksByQueryFromDB(sql);
    }

    private List<Book> getBooksByQueryFromDB(String sql) {
        return getBooksFromDB(sql);
    }

    private List<Book> getBooksByAuthorFromDB(String authorId) {
        String sql = "SELECT b.id, b.name, b.publish_year, b.content, b.image, p.name as publisher, " +
                "a.name AS author, c.name AS category " +
                "FROM library.book b " +
                "INNER JOIN library.author a ON b.author_id=a.id " +
                "INNER JOIN library.category c ON b.category_id=c.id " +
                "INNER JOIN library.publisher p ON b.publisher_id=p.id " +
                "WHERE author_id=" + authorId + ";";
        return getBooksFromDB(sql);
    }

    private List<Book> getBooksByCategoryFromDB(String categoryId) {
        String sql = "SELECT b.id, b.name, b.publish_year, b.content, b.image, p.name as publisher, " +
                "a.name AS author, c.name AS category " +
                "FROM library.book b " +
                "INNER JOIN library.author a ON b.author_id=a.id " +
                "INNER JOIN library.category c ON b.category_id=c.id " +
                "INNER JOIN library.publisher p ON b.publisher_id=p.id " +
                "WHERE category_id=" + categoryId + ";";
        return getBooksFromDB(sql);
    }

    private List<Book> getBooksFromDB(String sql) {
        List<Book> result = new ArrayList<>();
        Connection conn = DatabaseUtil.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while(rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setName(rs.getString("name"));
                book.setAuthor(new Author(rs.getString("author")));
                book.setCategory(new Category(rs.getString("category")));
                book.setPublisher(new Publisher(rs.getString("publisher")));
                book.setYear(rs.getInt("publish_year"));
                book.setImage(rs.getString("image"));
                book.setContent(rs.getString("content"));
                result.add(book);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
