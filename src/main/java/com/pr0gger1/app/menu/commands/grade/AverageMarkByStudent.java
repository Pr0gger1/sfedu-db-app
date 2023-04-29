package com.pr0gger1.app.menu.commands.grade;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AverageMarkByStudent extends Command {
    public AverageMarkByStudent(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        Faculty faculty = new Faculty();
        Direction direction = new Direction();

        Table gradeTable = new Table();

        try {
            faculty.setIdFromConsole();
            direction.setCurrentQuery(String.format(
                "SELECT * FROM %s WHERE faculty_id = %d",
                DataTables.DIRECTIONS.getTable(), faculty.getId()
            ));

            direction.setIdFromConsole();

            System.out.print("Введите курс: ");
            short course = scanner.nextShort();
            scanner.nextLine();

            String studentQuery = String.format(
                "SELECT s.id, full_name, course, direction_name, AVG(mark) as avg_mark FROM %s s " +
                "JOIN %s m ON s.id = m.student_id JOIN %s d ON s.direction_id = d.id " +
                "WHERE direction_id = %d AND course = %d GROUP BY s.id, direction_name",
                DataTables.STUDENTS.getTable(), DataTables.MARKS.getTable(),
                DataTables.DIRECTIONS.getTable(), direction.getId(), course
            );

            ResultSet students = Database.getData(studentQuery);
            gradeTable.fillTable(students);
            System.out.println(gradeTable);

        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}