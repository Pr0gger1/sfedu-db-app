package com.pr0gger1.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLFileExecutor {
    private final Connection connection;

    SQLFileExecutor(Connection connection) {
        this.connection = connection;
    }

    public void execute(String queryFile) throws SQLException, IOException {
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
    }
}
