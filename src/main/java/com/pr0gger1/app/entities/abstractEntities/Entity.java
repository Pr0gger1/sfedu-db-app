package com.pr0gger1.app.entities.abstractEntities;

import com.pr0gger1.app.formatOut.Table;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Entity {
    Table entityTable;
    private final DataTables entityTableName;
    private final ArrayList<String> requiredColumns = new ArrayList<>();

    protected Entity(DataTables table, ArrayList<String> columns) {
        entityTableName = table;
        requiredColumns.addAll(columns);
        entityTable = getEntityTable();
    }

    public Table getEntityTable() {
        Table entityTable = new Table();
        try {
            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(
                String.format(
                        "SELECT %s FROM %s",
                        String.join(", ", requiredColumns), entityTableName.getTable()
                )
            );

            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            if (entityTable.getColumnsCount() == 0) {
                for (int i = 1; i <= columnCount; i++) {
                    for (String column : requiredColumns) {
                        String columnName = metaData.getColumnName(i);
                        if (Objects.equals(columnName, column))
                            entityTable.addColumn(metaData.getColumnName(i));
                    }
                }
            }

            while (result.next()) {
                ArrayList<Object> currentRow = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    currentRow.add(result.getObject(i));
                }
                entityTable.addRow(currentRow);
            }

        }
        catch (SQLException error) {
            System.out.println(error.getMessage());
        }

        return entityTable;
    }

    public void printEntityTable() {
        System.out.println(getEntityTable());
    }
}