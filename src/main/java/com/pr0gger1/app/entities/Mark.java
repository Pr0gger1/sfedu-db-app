package com.pr0gger1.app.entities;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelIOException;
import com.pr0gger1.exceptions.TooManyRowsException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mark extends Entity {
    private final Scanner scanner = new Scanner(System.in);
    private int studentId;
    private int subjectId;
    private short mark;
    private short year;

    public Mark() {
        super(
            DataTables.MARKS,
            new ArrayList<>(List.of("*"))
        );
    }

    public Mark(int studentId) {
        super(
            DataTables.MARKS,
            String.format(
                "SELECT m.id, subj.subject_name, mark, year FROM marks m" +
                " WHERE student_id = %d" +
                " JOIN subjects subj on m.subject_id = subj.id",
                    studentId
            )
        );
    }

    public int getStudentId() {
        return studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public short getMark() {
        return mark;
    }

    public short getYear() {
        return year;
    }

    public void setMark(short mark) {
        if (mark >= 0 && mark <= 100)
            this.mark = mark;
        else if (mark < 0) throw new IllegalArgumentException("Балл не может быть меньше 0");
        else throw new IllegalArgumentException("Балл не может быть больше 100");
    }

    public void setMarkFromConsole() {
        System.out.print("Введите балл за экзамен: ");
        setMark(scanner.nextShort());
        scanner.nextLine();
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentIdName(Student currentStudent) throws CancelIOException {
        boolean isChosen = false;

        while (!isChosen) {
            try {
                System.out.print("Введите ФИО студента или 0 для выхода: ");
                String studentName = scanner.nextLine();

                if (studentName.equals("0")) throw new CancelIOException("Отменен ввод данных");

                String condition = String.format("WHERE full_name LIKE '%%%s%%'", studentName);
                ResultSet studentResult = Database.getData(DataTables.STUDENTS, "*", condition);

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
                        if (studentId == chosenStudentId)
                            currentStudent.setFacultyId(facultyId);
                    }
                }
                else {
                    chosenStudentId = (int) studentTable.getRows().get(0).get(0);
                    this.setStudentId(chosenStudentId);
                    Integer facultyId = Integer.valueOf(studentTable.getColumnValues("faculty_id").get(0).toString());

                    if (facultyId != null) {
                        currentStudent.setFacultyId(facultyId);
                    }
                }
                isChosen = true;
            }
            catch (SQLException | TooManyRowsException error) {
                error.printStackTrace();
            }
        }
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public void setYearFromConsole() {
        System.out.print("Введите год семестра: ");
        setYear(scanner.nextShort());
        scanner.nextLine();
    }

    @Override
    public String toString() {
        return "Mark{" +
            "studentId=" + studentId +
            ", subjectId=" + subjectId +
            ", mark=" + mark +
            ", year=" + year +
            '}';
    }
}
