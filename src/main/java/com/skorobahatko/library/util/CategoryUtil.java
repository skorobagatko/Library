package com.skorobahatko.library.util;

import com.skorobahatko.library.entity.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryUtil {

    private List<Category> categories;

    public List<Category> getCategories() {
        if (categories == null) {
            getCategoriesFromDB();
        }
        return categories;
    }

    private void getCategoriesFromDB() {
        categories = new ArrayList<>();
        Connection conn = DatabaseUtil.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM library.category;")) {
            while(rs.next()) {
                Category genre = new Category(rs.getString("name"));
                categories.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
