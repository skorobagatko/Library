package com.skorobahatko.library.controller;

import com.skorobahatko.library.bean.Category;
import com.skorobahatko.library.util.DatabaseUtil;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(eager = true)
@ApplicationScoped
public class CategoryController implements Serializable {

    private List<Category> categories;

    public CategoryController() {
        getCategories();
    }

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
