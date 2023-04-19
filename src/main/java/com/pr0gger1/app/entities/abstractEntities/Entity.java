package com.pr0gger1.app.entities.abstractEntities;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.exceptions.TooManyRowsException;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.*;
import java.util.ArrayList;

public abstract class Entity {
    Table entityTable;
    private final DataTables entityTableName;

    private String currentQuery = "SELECT %s FROM %s";

    protected Entity(DataTables table, ArrayList<String> columns) {
        entityTableName = table;
        currentQuery = String.format(currentQuery, String.join(", ", columns), entityTableName.getTable());
        entityTable = getEntityTable();
    }

    protected Entity(DataTables table, String query) {
        entityTableName = table;
        currentQuery = query;
        entityTable = getEntityTable();
    }

    public void setCurrentQuery(String currentQuery) {
        this.currentQuery = currentQuery;
    }

    public Table getEntityTable() {
        Table entityTable = new Table();
        try {
            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(currentQuery);

            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (entityTable.getColumnsCount() == 0) {
                for (int i = 1; i <= columnCount; i++)
                    entityTable.addColumn(metaData.getColumnName(i));
            }

            while (result.next()) {
                ArrayList<Object> currentRow = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++)
                    currentRow.add(result.getObject(i));

                entityTable.addRow(currentRow);
            }

        }
        catch (SQLException | TooManyRowsException error) {
            error.printStackTrace();
        }
        return entityTable;
    }


    public void printEntityTable() {
        System.out.println(getEntityTable());
    }
}