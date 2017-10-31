package com.skorobahatko.library.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtil {

    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                InitialContext ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("jdbc/Library");
                conn = ds.getConnection();
            } catch (NamingException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
