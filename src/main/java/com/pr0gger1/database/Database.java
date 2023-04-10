package com.pr0gger1.database;

import java.sql.*;

import io.github.cdimascio.dotenv.Dotenv;

public class Database {
    private static Connection connection = null;
    private Database() {}

    public static void connect() {
        if (connection == null) {
            Dotenv dotenv = Dotenv.load();

            String url = dotenv.get("DB_URL");
            String dbUser = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");

            try {
                connection = DriverManager.getConnection(url, dbUser, dbPassword);
                initializeTables();
                System.out.println("Успешное подключение");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void createTable(String tableName, String body) throws SQLException {
        String query = String.format("CREATE TABLE IF NOT EXISTS %s %s", tableName, body);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public static void createRow(String tableName, String[] values, String body) throws SQLException {
        String insertValues = "";

        if (values.length > 0)
            insertValues = String.format("(%s)", String.join(", ", values));

        String query = String.format("INSERT INTO %s %s VALUES(%s)", tableName, insertValues, body);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    /**
     * @param tableName имя таблицы, к которой направляется запрос
     * @param values массив строк со столбцами, которые нужно извлечь из таблицы
     * @param condition условие, по которому извлекаются данные
     * @return ResultSet
     */
    public static ResultSet getRow(DataTables tableName, String[] values, String condition) throws SQLException {
        String query = String.format("SELECT %s FROM %s %s", String.join(", ", values), tableName.getTable(), condition);
        System.out.println(query);

        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    public static ResultSet getRow(DataTables tableName, String values, String condition) throws SQLException {
        String query = String.format("SELECT %s FROM %s %s", values, tableName.getTable(), condition);

        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    public static void deleteRow(String tableName, String values, String condition) throws SQLException {
        String query = String.format("DELETE %s FROM %s %s", values, tableName, condition);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    private static void initializeTables() {
        try {
            String facultiesBody = "(id SERIAL PRIMARY KEY," +
                "faculty_name VARCHAR(128) UNIQUE NOT NULL," +
                "address VARCHAR(256) UNIQUE NOT NULL," +
                "phone BIGINT," +
                "email VARCHAR(64))";

            String teachersBody = "(id SERIAL PRIMARY KEY," +
                    "full_name VARCHAR(64) NOT NULL," +
                    "salary REAL DEFAULT 0 NOT NULL," +
                    "birthday DATE DEFAULT NOW() NOT NULL," +
                    "faculty_id INTEGER REFERENCES faculties (id) ON DELETE CASCADE NOT NULL," +
                    "phone BIGINT)";

            String subjectsBody = "(id SERIAL PRIMARY KEY," +
                    "subject_name VARCHAR(64)," +
                    "teacher_id INTEGER REFERENCES teachers (id) ON DELETE SET NULL," +
                    "direction_id INTEGER REFERENCES directions (id) ON DELETE CASCADE)";

            String groupsBody = "(id SERIAL PRIMARY KEY," +
                    "faculty_id INTEGER REFERENCES faculties (id) ON DELETE CASCADE NOT NULL," +
                    "group_name VARCHAR(16) NOT NULL)";


            String directionBody = "(id SERIAL PRIMARY KEY," +
                    "faculty_id INTEGER REFERENCES faculties (id) ON DELETE SET NULL," +
                    "head INTEGER REFERENCES teachers (id) ON DELETE SET NULL)";

            String marksBody = "(id SERIAL PRIMARY KEY," +
                    "student_id INTEGER REFERENCES students (id) ON DELETE CASCADE NOT NULL," +
                    "subject_id INTEGER REFERENCES subjects (id) ON DELETE CASCADE NOT NULL," +
                    "mark SMALLINT NOT NULL," +
                    "year SMALLINT NOT NULL)";

            String studentBody = "(id SERIAL PRIMARY KEY," +
                "full_name VARCHAR(64) NOT NULL," +
                "course SMALLINT DEFAULT 1 NOT NULL," +
                "direction INTEGER REFERENCES directions (id) ON DELETE CASCADE NOT NULL," +
                "faculty INTEGER REFERENCES faculties (id) ON DELETE CASCADE NOT NULL," +
                "birthday DATE DEFAULT NOW() NOT NULL," +
                "scholarship REAL DEFAULT 0," +
                "phone BIGINT)";

            Database.createTable("faculties", facultiesBody);
            Database.createTable("teachers", teachersBody);
            Database.createTable("directions", directionBody);
            Database.createTable("groups", groupsBody);
            Database.createTable("subjects", subjectsBody);
            Database.createTable("students", studentBody);
            Database.createTable("marks", marksBody);
        }
        catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }
}
