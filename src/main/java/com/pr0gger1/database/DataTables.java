package com.pr0gger1.database;

public enum DataTables {
    FACULTIES("faculties"),
    DIRECTIONS("directions"),
    GROUPS("groups"),
    MARKS("marks"),
    TEACHERS("teachers"),
    STUDENTS("students"),
    SUBJECTS("subjects"),
    ;
    private final String table;

    DataTables(String name) {
        table = name;
    }

    public String getTable() {
        return this.table;
    }

}
