package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.Mark;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.exceptions.CancelIOException;
import com.pr0gger1.exceptions.TooManyRowsException;
import com.pr0gger1.app.entities.Subject;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SetMark extends Command {
    private final Mark mark = new Mark();
    private final Student currentStudent = new Student();

    public SetMark(int commandId, String title) {
        super(commandId, title);
    }

    @Override
    public void execute() {
        try {
            while (true) {
                setStudentName();
                setSubject();
                setScore();
                setYear();
            }
        }
        catch (CancelIOException cancelError) {
            cancelError.printStackTrace();
        }
    }

    private void setStudentName() throws CancelIOException {
        Scanner scanner = new Scanner(System.in);
        boolean isChosen = false;

        try {
            while (!isChosen) {
                System.out.print("Введите ФИО студента или 0 для выхода: ");
                String studentName = scanner.nextLine();

                if (studentName.equals("0")) return;

                String condition = String.format("WHERE full_name LIKE '%%%s%%'", studentName);
                ResultSet studentResult = Database.getData(DataTables.STUDENTS, "*", condition);
                Table studentTable = Database.getFormedTable(studentResult);

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
                    if (studentTable.getRowsCount() > 0) {
                        chosenStudentId = (int) studentTable.getRows().get(0).get(0);
                        mark.setStudentId(chosenStudentId);
                        currentStudent.setFacultyId(
                            Integer.parseInt(studentTable.getColumnValues("faculty").get(0).toString())
                        );
                    }
                }
                isChosen = true;
            }
        }
        catch (SQLException | TooManyRowsException error) {
            error.printStackTrace();
        }
    }

    private void setSubject() {
        Scanner scanner = new Scanner(System.in);
        boolean isChosen = false;

        while (!isChosen) {
            int facultyId = currentStudent.getFacultyId();
            Subject subject = new Subject(facultyId);
            subject.printEntityTable();

            System.out.print("Выберите предмет (выберите ID):");
            int chosenSubjectId = scanner.nextInt();
            scanner.nextLine();

            if (!subject.getEntityTable().fieldExists(chosenSubjectId)) {
                System.out.println("Неверный ID");
                continue;
            }

            mark.setSubjectId(chosenSubjectId);
            isChosen = true;
        }
    }

    private void setScore() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите балл за экзамен: ");
        mark.setMark(scanner.nextShort());
        scanner.nextLine();
    }

    private void setYear() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите год семестра: ");
        mark.setYear(scanner.nextShort());
        scanner.nextLine();
    }
}
