package com.pr0gger1.app.formatOut;

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

    public void addRow(ArrayList<Object> row) {
        // Если есть поля в таблице -> сравнить длину массива входной строки с массивом строк
        // Иначе просто добавляем строку в таблицу
        if (rows.size() > 0) {
            if (row.size() == rows.get(0).size())
                rows.add(row);
            else
                System.out.println("Количество элементов в строке превышает количество столбов");
        }
        else rows.add(row);
    }

    public void addRow(Object... rowData) {
        if (rows.size() > 0) {
            if (rowData.length == rows.get(0).size())
                rows.add(new ArrayList<>(Arrays.asList(rowData)));
            else System.out.println("Во входной строке больше строк, чем в существующих");
        }
        else rows.add(new ArrayList<>(Arrays.asList(rowData)));
    }

    public void addRows(ArrayList<ArrayList<Object>> newRows) {
        if (rows.size() > 0) {
            if (newRows.get(0).size() == rows.get(0).size())
                rows.addAll(newRows);

            else System.out.println("Строки больше, чем количество столбцов");
            return;
        }
        rows.addAll(newRows);
    }

    public void addColumns(ArrayList<String> columns) throws Exception {
        this.columns.addAll(columns);
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

    public boolean fieldExists(int field) {
        for (ArrayList<Object> row : rows) {
            if (row.contains(field))
                return true;
        }
        return false;
    }

    public boolean fieldExists(String field) {
        for (ArrayList<Object> row : rows) {
            if (row.contains(field))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getTable().toString();
    }
}