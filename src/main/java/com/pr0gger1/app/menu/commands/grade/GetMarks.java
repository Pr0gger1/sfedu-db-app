package com.pr0gger1.app.menu.commands.grade;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.TooManyRowsException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GetMarks extends Command {
    public GetMarks(int commandId, String title) {
        super(commandId, title);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                Table markTable = new Table("Предмет", "Балл", "Год семестра");
                Student student = new Student();
                int chosenStudentId;

                student.printEntityTable();
                System.out.println("Введите ID студента или 0 для выхода: ");

                chosenStudentId = scanner.nextInt();
                scanner.nextLine();

                if (chosenStudentId == 0) return;
                if (!student.getEntityTable().fieldExists(chosenStudentId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                String query = String.format(
                    "SELECT subj.subject_name, mark, year FROM %s" +
                    " join subjects subj on subject_id = subj.id WHERE student_id = %d",
                    DataTables.MARKS, chosenStudentId
                );

                ResultSet marks = Database.getData(query);

                while (marks.next()) {
                    ArrayList<Object> row = new ArrayList<>();
                    row.add(marks.getString("subject_name"));
                    row.add(marks.getShort("mark"));
                    row.add(marks.getShort("year"));

                    markTable.addRow(row);
                }

                if (markTable.getRowsCount() == 0)
                    System.out.println("Успеваемости данного студента нет в базе данных");
                else System.out.println(markTable);

                System.out.println("Продолжить?");
                System.out.println("1. Да");
                System.out.println("0. Нет");

                int answer = scanner.nextInt();
                scanner.nextLine();
                if (answer != 1) return;
            }
            catch (SQLException | TooManyRowsException error) {
                error.printStackTrace();
            }
        }
    }
}
