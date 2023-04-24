package com.pr0gger1.app.entities.abstractEntities;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.exceptions.CancelIOException;
import com.pr0gger1.exceptions.TooManyRowsException;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Entity {
    private final Scanner scanner = new Scanner(System.in);
    protected int id;

    Table entityTable;
    private final DataTables entityTableName;
    private ArrayList<String> localizedColumns = new ArrayList<>();
    private String currentQuery = "SELECT %s FROM %s ORDER BY id";


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

    public ArrayList<String> getLocalizedColumns() {
        return localizedColumns;
    }

    public void setLocalizedColumns(ArrayList<String> localizedColumns) {
        this.localizedColumns = localizedColumns;
    }

    public void setIdFromConsole() throws CancelIOException {
        if (getEntityTable().getRowsCount() == 0) {
            System.out.println("В базе данных отсутствуют данные");
            return;
        }
        while (true) {
            printEntityTable();
            String message = "Выберите %s или 0 для выхода: ";

            switch (entityTableName) {
                case DIRECTIONS -> System.out.printf(message, "направление подготовки");
                case FACULTIES -> System.out.printf(message, "факультет");
                case GROUPS -> System.out.printf(message, "студенческую группу");
                case STUDENTS -> System.out.printf(message, "студента");
                case SUBJECTS -> System.out.printf(message, "предмет");
                case MARKS -> System.out.printf(message, "балл за экзамен");
                case EMPLOYEES -> System.out.printf(message, "преподавателя");
            }
            id = scanner.nextInt();
            scanner.nextLine();

            if (id == 0) throw new CancelIOException("Отменен ввод данных");
            else if (!getEntityTable().fieldExists(id)) System.out.println("Неверный ID");
            else break;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void fillEntity() {}
    public void updateData(ArrayList<Runnable> setters) {
        System.out.println("Какие данные желаете изменить?");
        for (int i = 0; i < getLocalizedColumns().size(); i++) {
            if (i == 0) System.out.printf("%d. Выход%n", i);
            System.out.printf("%d. %s%n", i + 1, getLocalizedColumns().get(i));
        }

        int chosenPoint = scanner.nextInt();
        scanner.nextLine();

        if (chosenPoint == 0) return;
        setters.get(chosenPoint - 1).run();
    }
}