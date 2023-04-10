package com.pr0gger1.app.formatOut;

import java.util.ArrayList;

public class Table {
    private ArrayList<String> columns = new ArrayList<>();
    private final ArrayList<ArrayList<String>> rows = new ArrayList<>();



    public Table(ArrayList<String> columns, ArrayList<ArrayList<String>> rows) {
        this.columns.addAll(columns);
        this.rows.addAll(rows);
    }

    public Table(ArrayList<String> columns) {
        this.columns = columns;
    }

    public void addRow(ArrayList<String> row) {
        // Если есть поля в таблице -> сравнить длину массива входной строки с массивом строк
        if (rows.size() > 0 && row.size() == rows.get(0).size()) {
            rows.add(row);
            return;
        }
        rows.add(row);
    }

    public void addRows(ArrayList<ArrayList<String>> newRows) {
        if (rows.size() > 0 && newRows.get(0).size() == rows.get(0).size()) {
            rows.addAll(newRows);
            return;
        }
        rows.addAll(newRows);
    }

    public void printTable() {
        int[] maxLengths = getRowMaxLength();

        StringBuilder horizontalBorder = new StringBuilder("+");
        for (int maxLength : maxLengths) {
            horizontalBorder.append("-".repeat(maxLength + 2)).append("+");
        }

        System.out.println(horizontalBorder);

        System.out.print("| ");
        for (int i = 0; i < columns.size(); i++) {
            System.out.printf("%-" + (maxLengths[i] + 1) + "s| ", columns.get(i));
        }
        System.out.println();

        System.out.println(horizontalBorder);

        for (ArrayList<String> row : rows) {
            System.out.print("| ");
            for (int i = 0; i < row.size(); i++) {
                System.out.printf("%-" + (maxLengths[i] + 1) + "s| ", row.get(i));
            }
            System.out.println();

            System.out.println(horizontalBorder);
        }
    }

    private int[] getRowMaxLength() {
        int[] maxLengths = new int[columns.size()];

        // учитываем длины заголовков колонок
        for (int i = 0; i < columns.size(); i++) {
            maxLengths[i] = columns.get(i).length();
        }

        for (ArrayList<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                int length = row.get(i).length();
                if (length > maxLengths[i]) {
                    maxLengths[i] = length;
                }
            }
        }

        return maxLengths;
    }
    public void addColumn(ArrayList<String> column) throws Exception {
        columns.addAll(column);
    }

    private StringBuilder border() {
        int[] maxLengths = getRowMaxLength();
        StringBuilder horizontalBorder = new StringBuilder();

        horizontalBorder.append("+");
        horizontalBorder.append("+");
        return horizontalBorder;
    }


    public ArrayList<String> getColumns() {
        return columns;
    }

    public ArrayList<ArrayList<String>> getRows() {
        return rows;
    }
}
