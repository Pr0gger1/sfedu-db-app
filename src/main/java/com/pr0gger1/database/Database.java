package com.pr0gger1.database;

import java.io.IOException;
import java.sql.*;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.exceptions.TooManyRowsException;
import com.pr0gger1.app.entities.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Date;
import java.util.ArrayList;

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
            catch (SQLException | IOException error) {
                error.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    private static int getMaxId(DataTables table) {
        int newId = 0;
        try {
            String maxIdQuery = "SELECT MAX(id) FROM " + table.getTable();

            PreparedStatement countStatement = connection.prepareStatement(maxIdQuery);

            ResultSet resultSet =  countStatement.executeQuery();
            while (resultSet.next()) newId = resultSet.getInt("max") + 1;

        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        return newId;
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
        String query = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(++columnIndex, getMaxId(DataTables.STUDENTS));
            statement.setString(++columnIndex, student.getFullName());
            statement.setShort(++columnIndex, student.getCourse());
            statement.setInt(++columnIndex, student.getDirectionId());
            statement.setInt(++columnIndex, student.getFacultyId());
            statement.setDate(++columnIndex, Date.valueOf(student.getBirthday()));
            statement.setFloat(++columnIndex, student.getScholarship());
            statement.setLong(++columnIndex, student.getPhone());

            statement.executeUpdate();
        }
    }

    public static void updateStudent(Student student) throws SQLException {
        String query = "UPDATE students SET" +
            " full_name = ?, course = ?, " +
            "direction_id = ?, faculty_id = ?," +
            "birthday = ?, scholarship = ?, phone = ? WHERE id = ?";

        int columnIndex = 0;

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(++columnIndex, student.getFullName());
            statement.setShort(++columnIndex, student.getCourse());
            statement.setInt(++columnIndex, student.getDirectionId());
            statement.setInt(++columnIndex, student.getFacultyId());
            statement.setDate(++columnIndex, Date.valueOf(student.getBirthday()));
            statement.setFloat(++columnIndex, student.getScholarship());
            statement.setLong(++columnIndex, student.getPhone());
            statement.setInt(++columnIndex, student.getId());

            statement.executeUpdate();
        }
    }

    public static void createMark(Mark mark) throws SQLException {
        String query = "INSERT INTO marks VALUES(?, ?, ?, ?, ?)";
        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(++columnIndex, getMaxId(DataTables.MARKS));
            statement.setInt(++columnIndex, mark.getStudentId());
            statement.setInt(++columnIndex, mark.getSubjectId());
            statement.setShort(++columnIndex, mark.getMark());
            statement.setShort(++columnIndex, mark.getYear());

            statement.executeUpdate();
        }
    }

    /**
     *
     * @param teacher объект класса Teacher
     */
    public static void createTeacher(Teacher teacher) throws SQLException {
        String query = "INSERT INTO teachers VALUES(?, ?, ?, ?, ?, ?, ?)";
        int columnIndex = 0;


        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(++columnIndex, getMaxId(DataTables.TEACHERS));
            statement.setString(++columnIndex, teacher.getFullName());
            statement.setFloat(++columnIndex, teacher.getSalary());
            statement.setString(++columnIndex, teacher.getSpecialization());
            statement.setInt(++columnIndex, teacher.getFacultyId());
            statement.setDate(++columnIndex, Date.valueOf(teacher.getBirthday()));
            statement.setLong(++columnIndex, teacher.getPhone());

            statement.executeUpdate();
        }
    }

    public static void updateTeacher(Teacher teacher) throws SQLException {
        String query = "UPDATE teachers SET " +
            "full_name = ?, salary = ?," +
            "specialization = ?, faculty_id = ?," +
            "birthday = ?, phone = ? WHERE id = ?";

        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(++columnIndex, teacher.getFullName());
            statement.setFloat(++columnIndex, teacher.getSalary());
            statement.setString(++columnIndex, teacher.getSpecialization());
            statement.setInt(++columnIndex, teacher.getFacultyId());
            statement.setDate(++columnIndex, Date.valueOf(teacher.getBirthday()));
            statement.setLong(++columnIndex, teacher.getPhone());
            statement.setInt(++columnIndex, teacher.getId());

            statement.executeUpdate();
        }
    }

    /**
     *
     * @param group объект класса Group
     */
    public static void createGroup(StudentsGroup group) throws SQLException {
        String query = "INSERT INTO groups VALUES(?, ?, ?)";
        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(++columnIndex, getMaxId(DataTables.GROUPS));
            statement.setInt(++columnIndex, group.getFacultyId());
            statement.setString(++columnIndex, group.getGroupName());

            statement.executeUpdate();
        }
    }

    public static void updateGroup(StudentsGroup group) throws SQLException {
        String query = "UPDATE groups SET faculty_id = ?, group_name = ? WHERE id = ?";
        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(++columnIndex, group.getFacultyId());
            statement.setString(++columnIndex, group.getGroupName());
            statement.setInt(++columnIndex, group.getId());

            statement.executeUpdate();
        }
    }

    /**
     *
     * @param faculty объект класса Faculty
     */
    public static void createFaculty(Faculty faculty) throws SQLException {
        String query = "INSERT INTO faculties VALUES (?, ?, ?, ?, ?)";

        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(++columnIndex, getMaxId(DataTables.FACULTIES));
            statement.setString(++columnIndex, faculty.getFacultyName());
            statement.setString(++columnIndex, faculty.getAddress());
            statement.setLong(++columnIndex, faculty.getPhone());
            statement.setString(++columnIndex, faculty.getEmail());

            statement.executeUpdate();
        }
    }

    public static void updateFaculty(Faculty faculty) throws SQLException {
        String query = "UPDATE faculties SET " +
            "faculty_name = ?, address = ?," +
            "phone = ?, email = ? WHERE id = ?";

        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(++columnIndex, faculty.getFacultyName());
            statement.setString(++columnIndex, faculty.getAddress());
            statement.setLong(++columnIndex, faculty.getPhone());
            statement.setString(++columnIndex, faculty.getEmail());
            statement.setInt(++columnIndex, faculty.getId());

            statement.executeUpdate();
        }
    }

    public static void createDirection(Direction direction) throws SQLException {
        String query = "INSERT INTO directions VALUES(?, ?, ?, ?)";
        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(++columnIndex, getMaxId(DataTables.DIRECTIONS));
            statement.setInt(++columnIndex, direction.getFacultyId());
            statement.setString(++columnIndex, direction.getDirectionName());
            statement.setInt(++columnIndex, direction.getHeadOfDirectionId());

            statement.executeUpdate();
        }
    }

    public static void updateDirection(Direction direction) throws SQLException {
        String query = "UPDATE directions SET " +
            "faculty_id = ?, direction_name = ?," +
            "head = ? WHERE id = ?";
        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(++columnIndex, direction.getFacultyId());
            statement.setString(++columnIndex, direction.getDirectionName());
            statement.setInt(++columnIndex, direction.getHeadOfDirectionId());
            statement.setInt(++columnIndex, direction.getId());

            statement.executeUpdate();
        }
    }

    public static void createSubject(Subject subject) throws SQLException {
        String query = "INSERT INTO subjects VALUES(?, ?, ?, ?, ?)";
        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(++columnIndex, getMaxId(DataTables.SUBJECTS));
            statement.setString(++columnIndex, subject.getSubjectName());
            statement.setInt(++columnIndex, subject.getFacultyId());
            statement.setInt(++columnIndex, subject.getDirectionId());
            statement.setInt(++columnIndex, subject.getTeacherId());

            statement.executeUpdate();
        }
    }

    public void updateSubject(Subject subject) throws SQLException {
        String query = "UPDATE subjects SET " +
            "subject_name = ?, faculty_id = ?," +
            "direction_id = ?, teacher_id = ? WHERE id = ?";

        int columnIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(++columnIndex, subject.getSubjectName());
            statement.setInt(++columnIndex, subject.getFacultyId());
            statement.setInt(++columnIndex, subject.getDirectionId());
            statement.setInt(++columnIndex, subject.getTeacherId());
            statement.setInt(++columnIndex, subject.getId());

            statement.executeUpdate();
        }
    }

    /**
     * @param tableName имя таблицы, к которой направляется запрос
     * @param columns массив строк со столбцами, которые нужно извлечь из таблицы
     * @param condition условие, по которому извлекаются данные
     * @return ResultSet
     */
    public static ResultSet getData(DataTables tableName, String[] columns, String condition) throws SQLException {
        String query = String.format(
                "SELECT %s FROM %s %s ORDER BY id", String.join(", ", columns), tableName.getTable(), condition
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
    public static ResultSet getData(DataTables tableName, String columns, String condition) throws SQLException {
        String query = String.format("SELECT %s FROM %s %s", columns, tableName.getTable(), condition);

        PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        return statement.executeQuery();
    }

    public static ResultSet getData(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );

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

    public static Table getFormedTable(ResultSet queryResult) throws SQLException, TooManyRowsException {
        ResultSetMetaData queryMetaData = queryResult.getMetaData();
        Table table = new Table();

        int columnCount = queryMetaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            table.addColumn(queryMetaData.getColumnName(i));
        }

        while (queryResult.next()) {
            ArrayList<Object> row =  new ArrayList<>();

            for (int i = 1; i <= columnCount; i++) {
                row.add(queryResult.getObject(i));
            }
            table.addRow(row);
        }

        return table;
    }
}