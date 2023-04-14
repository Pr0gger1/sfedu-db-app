package com.pr0gger1.database;

import java.sql.*;
import java.util.Locale;

import com.pr0gger1.app.entities.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Database {
    private static Connection connection = null;

    public static void connect() {
        if (connection == null) {
            Dotenv dotenv = Dotenv.load();

            String url = dotenv.get("DB_URL");
            String dbUser = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");

            try {
                connection = DriverManager.getConnection(url, dbUser, dbPassword);
                SQLFileExecutor executor = new SQLFileExecutor(connection);
                executor.execute("tables.sql");

                System.out.println("Успешное подключение");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void createTable(String tableName, String body) throws SQLException {
        String query = String.format("CREATE TABLE IF NOT EXISTS %s %s", tableName, body);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }


    /**
     * @param tableName имя таблицы, в которую добавляются данные
     * @param columns массив строк с колонками таблицы, по которым производится добавление данных
     * @param values значения колонок таблицы
     */
    public static void createRow(DataTables tableName, String[] columns, String[] values) throws SQLException {
        StringBuilder insertValues = new StringBuilder("(");

        for (String column : columns)
            insertValues.append(column).append(", ");

        insertValues.append(")");

        String query = String.format(
                "INSERT INTO %s %s VALUES(%s)", tableName.getTable(),
                insertValues, String.join(", ", values)
        );

        System.out.println(query);

        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    /**
     *
     * @param student объект класса Student
     */
    public static void createStudent(Student student) throws SQLException {
        String query = String.format(Locale.ROOT,
          "INSERT INTO %s (full_name, course, direction, faculty, birthday, scholarship, phone) " +
          "VALUES('%s', %d, %d, %d, '%s', %f, %d)",
            DataTables.STUDENTS.getTable(),
            student.fullName, student.course,
            student.getDirectionId(), student.getFacultyId(),
            student.birthday, student.scholarship,
            student.phone
        );

        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    /**
     *
     * @param teacher объект класса Teacher
     */
    public static void createTeacher(Teacher teacher) throws SQLException {
        String query = String.format(Locale.ROOT,
            "INSERT INTO teachers (birthday, faculty_id, full_name, phone, salary, specialization) " +
            "VALUES('%s', %d, '%s', %d, %.2f, '%s')",
                teacher.birthday, teacher.getFacultyId(),
                teacher.fullName, teacher.phone,
                teacher.salary, teacher.specialization
        );

        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    /**
     *
     * @param group объект класса Group
     */
    public static void createGroup(StudentsGroup group) throws SQLException {
        String query = String.format(
            "INSERT INTO groups (faculty_id, group_name) " +
            "VALUES(%d, '%s')",
            group.getFacultyId(), group.groupName
        );

        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    /**
     *
     * @param faculty объект класса Faculty
     */
    public static void createFaculty(Faculty faculty) throws SQLException {
        String query = String.format(
            "INSERT INTO %s (" +
                "faculty_name, address, " +
                "phone, email" +
            ") " +
            "VALUES('%s', '%s', %d, '%s')",
            DataTables.FACULTIES.getTable(),
            faculty.facultyName, faculty.address,
            faculty.phone, faculty.email
        );

        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    public static void createDirection(Direction direction) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (faculty_id, direction_name, head)" +
                " VALUES(%d, '%s', %d)",
                DataTables.DIRECTIONS.getTable(),
                direction.getFacultyId(),
                direction.directionName,
                direction.getHeadOfDirectionId()
        );

        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    /**
     * @param tableName имя таблицы, к которой направляется запрос
     * @param columns массив строк со столбцами, которые нужно извлечь из таблицы
     * @param condition условие, по которому извлекаются данные
     * @return ResultSet
     */
    public static ResultSet getRow(DataTables tableName, String[] columns, String condition) throws SQLException {
        String query = String.format(
                "SELECT %s FROM %s %s", String.join(", ", columns), tableName.getTable(), condition
        );

        PreparedStatement statement = connection.prepareStatement(
                query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY
        );
        return statement.executeQuery();
    }

    /**
     *
     * @param tableName имя таблицы, к которой направляется запрос
     * @param columns строка с колонками, по которым происходит выборка данных.
     *                Колонки записывать через запятую
     * @param condition условие, по которому происходит выборка данных
     * @return ResultSet
     */
    public static ResultSet getRow(DataTables tableName, String columns, String condition) throws SQLException {
        String query = String.format("SELECT %s FROM %s %s", columns, tableName.getTable(), condition);

        PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery();
    }

    /**
     *
     * @param tableName имя таблицы, к которой направляется запрос
     * @param columns строка колонок, значения которых должны быть удалены
     * @param condition условие, по которому должно/ы быть удалено/ы поле/я
     */
    public static void deleteRow(String tableName, String columns, String condition) throws SQLException {
        String query = String.format("DELETE %s FROM %s %s", columns, tableName, condition);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    /**
     * @param tableName имя таблицы, к которой направляется запрос
     * @param columns массив строк колонок, значения которых должны быть удалены
     * @param condition условие, по которому должно/ы быть удалено/ы поле/я
     */
    public static void deleteRow(String tableName, String[] columns, String condition) throws SQLException {
        String query = String.format("DELETE %s FROM %s %s", String.join(", ", columns), tableName, condition);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }
}
