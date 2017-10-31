package com.skorobahatko.library.util;

import com.skorobahatko.library.entity.Author;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorUtil {

    private List<Author> authors;

    public List<Author> getAuthors() {
        if (authors == null) {
            getAuthorsFromDB();
        }
        return authors;
    }

    private void getAuthorsFromDB() {
        authors = new ArrayList<>();
        Connection conn = DatabaseUtil.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM library.author ORDER BY name;")) {
            while(rs.next()) {
                Author author = new Author(rs.getString("name"));
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}