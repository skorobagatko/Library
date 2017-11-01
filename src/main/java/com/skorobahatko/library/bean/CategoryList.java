package com.skorobahatko.library.bean;

import com.skorobahatko.library.entity.Category;
import com.skorobahatko.library.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryList {

    private List<Category> categories;

    public List<Category> getCategories() {
        if (categories == null) {
            getCategoriesFromDB();
        }
        return categories;
    }

    public Category getCategoryById(String id) {
        Category category = null;
        Connection conn = DatabaseUtil.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM library.category WHERE id=?;")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            category = new Category(rs.getString("name"));
            category.setId(Long.parseLong(rs.getString("id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    private void getCategoriesFromDB() {
        categories = new ArrayList<>();
        Connection conn = DatabaseUtil.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM library.category;")) {
            while(rs.next()) {
                Category category = new Category(rs.getString("name"));
                category.setId(rs.getLong("id"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
