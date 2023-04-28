package com.pr0gger1.app.entities;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.abstractEntities.Human;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.InvalidArgument;
import com.pr0gger1.exceptions.InvalidIDException;
import com.pr0gger1.exceptions.TooManyRowsException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Student extends Human {
    private final Scanner scanner = new Scanner(System.in);

    private int facultyId;
    private int directionId;
    private short course;
    private float scholarship;
    private long phone;
    private String fullName;

    public Student() {
        super(DataTables.STUDENTS, new ArrayList<>(Arrays.asList("id", "full_name")));
        setLocalizedColumns(new ArrayList<>(Arrays.asList(
            "ID", "ФИО", "Курс", "Направление подготовки", "Факультет",
            "Дата рождения", "Стипендия", "Номер телефона"
        )));
    }

    public Student(int directionId, int facultyId) {
        super(DataTables.STUDENTS, new ArrayList<>(Arrays.asList("id", "full_name")));
        this.directionId = directionId;
        this.facultyId = facultyId;
    }

    public int getDirectionId() {
        return directionId;
    }
    public int getFacultyId() {
        return facultyId;
    }

    public float getScholarship() {
        return scholarship;
    }

    public long getPhone() {
        return phone;
    }

    public short getCourse() {
        return course;
    }

    public String getFullName() {
        return fullName;
    }


    public void setCourse(short course) {
        try {
            if (course < 1) throw new InvalidArgument("Неверное значение курса");
            this.course = course;
        }
        catch (InvalidArgument iaError) {
            iaError.printStackTrace();
        }
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setScholarship(float scholarship) {
        try {
            if (scholarship < 0) throw new InvalidArgument("Стипендия не может быть меньше 0");
            this.scholarship = scholarship;
        }
        catch (InvalidArgument iaError) {
            iaError.printStackTrace();
        }
    }
    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public void fillEntityFromConsole() {
        System.out.print("Введите ФИО студента:");
        setFullName(scanner.nextLine());

        System.out.print("Введите номер курса:");
        setCourse(scanner.nextShort());
        scanner.nextLine();

        setBirthdayFromConsole();

        System.out.print("Введите размер стипендии (0, если нет): ");
        setScholarship(scanner.nextFloat());
        scanner.nextLine();

        System.out.print("Введите номер телефона:");
        setPhone(scanner.nextLong());
        scanner.nextLine();
    }

    @Override
    public void fillEntity() {
        try {
            if (id == 0) throw new InvalidIDException("Неверный ID");
            String query = String.format(
                "SELECT * FROM %s WHERE id = %d",
                DataTables.STUDENTS.getTable(), id
            );
            ResultSet data = Database.getData(query);
            while (data.next()) {
                fullName = data.getString("full_name");
                course = data.getShort("course");
                directionId = data.getInt("direction_id");
                facultyId = data.getInt("faculty_id");
                birthday = data.getDate("birthday").toLocalDate();
                scholarship = data.getFloat("scholarship");
                phone = data.getLong("phone");
            }
        }
        catch (SQLException | InvalidIDException error) {
            error.printStackTrace();
        }
    }

    public void updateData() throws CancelInputException {
        ArrayList<Runnable> setters = new ArrayList<>(
            Arrays.asList(
                () -> {
                    System.out.print("Введите ID");
                    id = scanner.nextInt();
                    scanner.nextLine();
                },
                () -> {
                    System.out.print("Введите ФИО студента: ");
                    fullName = scanner.nextLine();
                },
                () -> {
                    System.out.print("Введите курс: ");
                    course = scanner.nextShort();
                    scanner.nextLine();
                },
                () -> {
                    Direction newDirection = new Direction();
                    try {
                        newDirection.setCurrentQuery(
                            String.format(
                                "SELECT * FROM %s WHERE faculty_id = %d",
                                    DataTables.DIRECTIONS.getTable(), facultyId
                            )
                        );
                        newDirection.setIdFromConsole();
                        directionId = newDirection.getId();
                    }
                    catch (CancelInputException error) {
                        error.printStackTrace();
                    }
                },
                () -> {
                    Faculty newFaculty = new Faculty();
                    Direction newDirection = new Direction();

                    try {
                        newFaculty.setIdFromConsole();
                        facultyId = newFaculty.getId();

                        // Если изменяется факультет, то изменяется и направление подготовки
                        newDirection.setCurrentQuery(
                            String.format(
                                "SELECT * FROM %s WHERE faculty_id = %d",
                                DataTables.DIRECTIONS.getTable(),
                                facultyId
                            )
                        );
                        newDirection.setIdFromConsole();
                        directionId = newDirection.getId();
                    }
                    catch (CancelInputException error) {
                        error.printStackTrace();
                    }
                },
                this::setBirthdayFromConsole,
                () -> {
                    System.out.print("Введите размер стипендии: ");
                    setScholarship(scanner.nextFloat());
                    scanner.nextLine();
                },
                () -> {
                    System.out.println("Введите номер телефона: ");
                    setPhone(scanner.nextLong());
                    scanner.nextLine();
                }
            )
        );
        super.updateData(setters);
    }

    @Override
    public void printEntityTable() {
        if (facultyId != 0)
            setCurrentQuery(
                String.format(
                    "SELECT * FROM %s WHERE faculty_id = %d",
                    DataTables.STUDENTS.getTable(),
                    facultyId
                )
            );
        super.printEntityTable();
    }

    public void searchStudent() throws CancelInputException {
         boolean isChosen = false;

        while (!isChosen) {
            try {
                System.out.print("Введите ФИО студента или 0 для выхода: ");
                String studentName = scanner.nextLine();

                if (studentName.equals("0")) throw new CancelInputException("Отменен ввод данных");

                String query = String.format(
                    "SELECT s.id, full_name, course, d.id as direction_id, direction_name, f.id as faculty_id, faculty_name, scholarship, s.phone " +
                    "FROM %s s JOIN %s d ON s.direction_id = d.id JOIN %s f ON s.faculty_id = f.id " +
                    "WHERE full_name LIKE '%%%s%%'",
                    DataTables.STUDENTS.getTable(),
                    DataTables.DIRECTIONS.getTable(),
                    DataTables.FACULTIES.getTable(),
                    studentName
                );

                if (facultyId != 0)
                    query += " AND faculty_id = " + facultyId;

                ResultSet studentResult = Database.getData(query);
                Table studentTable = Database.getFormedTable(studentResult);

                if (studentTable.getRowsCount() == 0) {
                    System.out.printf("Студент: %s не найден\n", studentName);
                    break;
                }

                System.out.println(studentTable);

                int chosenStudentId;
                if (studentTable.getRowsCount() > 1) {
                    System.out.println("Ваш запрос вернул больше 1 запроса, выберите конкретного студента");
                    chosenStudentId = scanner.nextInt();
                    scanner.nextLine();

                    if (!studentTable.fieldExists(chosenStudentId)) {
                        System.out.println("Неверный ID");
                        continue;
                    }

                    while (studentResult.next()) {
                        int facultyId = studentResult.getInt("faculty");
                        int studentId = studentResult.getInt("id");
                        if (studentId == chosenStudentId) {
                            setId(studentId);
                            setFacultyId(facultyId);
                            fillEntity();
                        }
                    }
                } else {
                    setId(Integer.parseInt(studentTable.getColumnValues("id").get(0).toString()));
                    fillEntity();
                }
                isChosen = true;

            } catch (SQLException | TooManyRowsException error) {
                error.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
            "Student:{\n" +
            "\tfacultyId: %d," +
            "\n\tdirectionId: %d," +
            "\n\tfull_name: %s," +
            "\n\tcourse: %d," +
            "\n\tbirthday: %s," +
            "\n\tscholarship: %f," +
            "\n\tphone: %d," +
            "\n}",
            facultyId, directionId,
            fullName, course,
            birthday != null ? birthday.toString() : "null",
            scholarship, phone
        );
    }

}
