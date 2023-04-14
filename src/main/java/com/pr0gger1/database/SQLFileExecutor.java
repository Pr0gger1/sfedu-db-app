package com.pr0gger1.database;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class SQLFileExecutor {
    private final Connection connection;

    SQLFileExecutor(Connection connection) {
        this.connection = connection;
    }

    public void execute(String queryFile) {
        try {
            Statement statement = connection.createStatement();

            String sql = new String(
                Files.readAllBytes(
                    Paths.get(
                        String.format(
                            "./src/main/resources/%s", queryFile
                        )
                    )
                )
            );

            statement.executeUpdate(sql);
            statement.close();

        } catch (Exception e) {
             System.out.println(e.getMessage());
        }
    }
}
