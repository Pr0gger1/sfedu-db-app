package com.pr0gger1.app.ConsoleTable;

import com.pr0gger1.exceptions.TooManyColumnsException;
import com.pr0gger1.exceptions.TooManyRowsException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Table {
    private ArrayList<Object> columns = new ArrayList<>();
    private final ArrayList<ArrayList<Object>> rows = new ArrayList<>();

    public Table(ArrayList<Object> columns, ArrayList<ArrayList<Object>> rows) {
        this.columns.addAll(columns);
        this.rows.addAll(rows);
    }

    public Table(ArrayList<Object> columns) {
        this.columns = columns;
    }

    public Table(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
    }

    public void addRow(ArrayList<Object> row) throws TooManyRowsException {
        // Если есть поля в таблице -> сравнить длину массива входной строки с массивом строк
        // Иначе просто добавляем строку в таблицу
        if (rows.size() > 0) {
            if (row.size() != this.columns.size())
                throw new TooManyRowsException("Количество элементов входной строки превышает количество столбцов");
            if (row.size() == rows.get(0).size())
                rows.add(row);
            else throw new TooManyRowsException(
                "Количество элементов входной строки превышает количество элементов в существующих"
            );
        }
        else rows.add(row);
    }

    public void addRow(Object... row) throws TooManyRowsException {
        if (rows.size() > 0) {
            if (row.length != this.columns.size())
                throw new TooManyRowsException("Количество элементов входной строки превышает количество столбцов");
            else if (row.length == rows.get(0).size())
                rows.add(new ArrayList<>(Arrays.asList(row)));
            else throw new TooManyRowsException(
                "Количество элементов входной строки превышает количество элементов в существующих"
            );
        }
        else rows.add(new ArrayList<>(Arrays.asList(row)));
    }

    public void addRows(ArrayList<ArrayList<Object>> rows) throws TooManyRowsException {
        if (this.rows.get(0).size() > 0 && columns.size() > 0) {
            for (ArrayList<Object> row : rows) {
                if (row.size() == columns.size())
                    this.rows.add(row);
                else throw new TooManyRowsException("Элементов строки больше, чем количество столбцов");
            }
        }
        else this.rows.addAll(rows);
    }

    public void addColumns(ArrayList<String> columns) throws TooManyColumnsException {
        if (this.columns.size() > 0)
            if (columns.size() == this.columns.size())
                this.columns.addAll(columns);
            else throw new TooManyColumnsException(
                "Входная последовательность колонок больше существующей"
            );

        else this.columns.addAll(columns);
    }

    public void addColumn(String column) {
        this.columns.add(column);
    }

    public StringBuilder getTable() {
        int[] maxLengths = getRowMaxLength();
        StringBuilder table = new StringBuilder();
        StringBuilder horizontalBorder = getBorder(maxLengths);

        table.append(horizontalBorder).append("\n").append("| ");

        for (int i = 0; i < columns.size(); i++)
            table.append(String.format("%-" + (maxLengths[i] + 1) + "s| ", columns.get(i)));

        table.append("\n").append(horizontalBorder);


        for (ArrayList<Object> row : rows) {
            table.append("\n| ");
            for (int i = 0; i < row.size(); i++)
                table.append(String.format("%-" + (maxLengths[i] + 1) + "s| ", row.get(i)));

            table.append("\n").append(horizontalBorder);
        }

        return table;
    }

    private int[] getRowMaxLength() {
        int[] maxLengths = new int[columns.size()];

        // учитываем длины заголовков колонок
        for (int i = 0; i < columns.size(); i++) {
            maxLengths[i] = columns.get(i).toString().length();
        }

        for (ArrayList<Object> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                int length = row.get(i).toString().length();
                if (length > maxLengths[i]) {
                    maxLengths[i] = length;
                }
            }
        }

        return maxLengths;
    }

    private StringBuilder getBorder(int[] rowMaxLengths) {
        StringBuilder horizontalBorder = new StringBuilder("+");
        for (int maxLength : rowMaxLengths) {
            horizontalBorder.append("-".repeat(maxLength + 2)).append("+");
        }

        return horizontalBorder;
    }


    public ArrayList<Object> getColumns() {
        return columns;
    }

    public ArrayList<ArrayList<Object>> getRows() {
        return rows;
    }

    public int getRowsCount() {
        return rows.size();
    }

    public int getColumnsCount() {
        return columns.size();
    }

    public boolean fieldExists(Object field) {
        for (ArrayList<Object> row : rows) {
            if (row.contains(field))
                return true;
        }
        return false;
    }

    public ArrayList<Object> getColumnValues(String column) {
        if (columns.contains(column)) {
            if (rows.size() == 0) return null;
            int columnIndex = this.columns.indexOf(column);

            ArrayList<Object> values = new ArrayList<>();
            for (ArrayList<Object> row : rows) {
                values.add(row.get(columnIndex));
            }
            return values;
        }
        return null;
    }

    public void fillTable(ResultSet queryResult) {
        try {
            ResultSetMetaData entityMetaData = queryResult.getMetaData();
            int columnCount = entityMetaData.getColumnCount();

            if (columns.size() == 0) {
                for (int i = 1; i <= columnCount; i++)
                    addColumn(entityMetaData.getColumnName(i));
            }

            while (queryResult.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++)
                    row.add(queryResult.getObject(i));

                addRow(row);
            }
        }
        catch (SQLException | TooManyRowsException error) {
            error.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getTable().toString();
    }
}