package com.skorobahatko.library.controller;

import com.skorobahatko.library.bean.Author;
import com.skorobahatko.library.bean.Book;
import com.skorobahatko.library.bean.Category;
import com.skorobahatko.library.bean.Publisher;
import com.skorobahatko.library.entity.QueryType;
import com.skorobahatko.library.util.DatabaseUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class SearchController implements Serializable {

    private static Map<String, QueryType> allQueryTypes;
    private static String baseSql = "SELECT b.id, b.name, b.publish_year, b.content, " +
            "b.image, b.description, p.name as publisher, " +
            "a.name AS author, c.name AS category " +
            "FROM library.book b " +
            "INNER JOIN library.author a ON b.author_id=a.id " +
            "INNER JOIN library.category c ON b.category_id=c.id " +
            "INNER JOIN library.publisher p ON b.publisher_id=p.id";

    static {
        allQueryTypes = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        allQueryTypes.put(rb.getString("search_by_author"), QueryType.AUTHOR);
        allQueryTypes.put(rb.getString("search_by_name"), QueryType.NAME);
    }

    private List<Book> bookList; // current book list
    private String userQuery;
    private String[] letters;
    private int booksPerPage = 2;
    private int booksCount;
    private int currentPage = 1; // current page number
    private List<Integer> pageNumbers = new ArrayList<>();
    private String lastSql; // last executed sql query
    private QueryType userQueryType;


    public SearchController() {
        ResourceBundle rb = ResourceBundle.getBundle("messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        letters = rb.getString("letters").split(" ");

        getAllBooks();
    }

    public Map<String, QueryType> getAllQueryTypes() {
        return allQueryTypes;
    }

    public QueryType getUserQueryType() {
        return userQueryType;
    }

    public void setUserQueryType(QueryType userQueryType) {
        this.userQueryType = userQueryType;
    }

    public void getAllBooks() {
        getBooksFromDB(baseSql);
    }

    public void getBooksByCategory() {

        currentPage = 1;

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int categoryId = Integer.parseInt(params.get("category_id"));
        String sql = baseSql + " WHERE category_id=" + categoryId;
        getBooksFromDB(sql);
    }

    public void getBooksByLetter() {

        currentPage = 1;

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String letter = params.get("letter");
        String sql = baseSql + " WHERE b.name LIKE '" + letter + "%';";
        getBooksFromDB(sql);
    }

    public String getBooksByQuery() {

        currentPage = 1;

        if (userQuery.trim().isEmpty()) {
            getBooksFromDB(baseSql);
        } else {
            String like = "%" + getUserQuery() + "%";
            String sql = baseSql + " WHERE ";
            if (getUserQueryType() == QueryType.AUTHOR) {
                sql += "lower(a.name) LIKE '" + like.toLowerCase() + "';";
            } else {
                sql += "lower(b.name) LIKE '" + like.toLowerCase() + "';";
            }
            getBooksFromDB(sql);
        }
        return "books";
    }

    public void selectPage() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        currentPage = Integer.parseInt(params.get("page"));
        getBooksFromDB(lastSql);
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public String[] getLetters() {
        return letters;
    }

    public String getUserQuery() {
        return userQuery;
    }

    public void setUserQuery(String userQuery) {
        this.userQuery = userQuery;
    }

    public int getBooksPerPage() {
        return booksPerPage;
    }

    public void setBooksPerPage(int booksPerPage) {
        this.booksPerPage = booksPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(List<Integer> pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public int getBooksCount() {
        return booksCount;
    }

    private void fillPageNumbers(int booksCount) {
        int pageCount = booksCount > 0 ? (booksCount / booksPerPage) + 1 : 0;
        pageNumbers.clear();
        for (int i = 1; i <= pageCount; i++)
            pageNumbers.add(i);
    }

    private void getBooksFromDB(String sql) {
        lastSql = sql;

        Connection conn;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.createStatement();

            // pages count
            rs = stmt.executeQuery(sql);
            rs.last();
            booksCount = rs.getRow();
            fillPageNumbers(booksCount);

            if (booksCount > booksPerPage) {
                StringBuilder sqlBuilder = new StringBuilder(sql);
//                int offset = currentPage == 1 ? currentPage : currentPage * booksPerPage;
                sqlBuilder.append(" LIMIT ")
                        .append(currentPage * booksPerPage - booksPerPage)
                        .append(",")
                        .append(currentPage * booksPerPage);
                sql = sqlBuilder.toString();
            }

            rs = stmt.executeQuery(sql);

            bookList = new ArrayList<>();
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
                book.setDescription(rs.getString("description"));
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Book.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
