package com.pr0gger1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static Connection connect() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/sfedu_db",
                    "sfedu_admin",
                    "12345678"
            );

            System.out.println("Успешное подключение");
            return c;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void createTable(Connection conn, String query) throws SQLException {
        if (conn != null) {
            final Statement state = conn.createStatement();
            state.executeQuery(query);
        }
    }
}
