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

public class AverageMarkBySubject extends Command {
    public AverageMarkBySubject(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Table avgGradeTable = new Table("Предмет", "Направление подготовки", "Средний балл");
        Faculty faculty = new Faculty();

        try {
            faculty.setIdFromConsole();

            Direction direction = new Direction(faculty.getId());
            direction.setIdFromConsole();

            String subjectQuery = String.format(
                "SELECT subject_name, d.direction_name, AVG(mark) average_mark FROM %s m " +
                "JOIN %s s ON m.subject_id = s.id " +
                "JOIN %s d ON s.direction_id = d.id " +
                "WHERE d.id = %d " +
                "GROUP BY subject_name, d.direction_name",
                DataTables.MARKS.getTable(),
                DataTables.SUBJECTS.getTable(),
                DataTables.DIRECTIONS.getTable(),
                direction.getId()
            );

            ResultSet averageMarks = Database.getData(subjectQuery);

            avgGradeTable.fillTable(averageMarks);
            System.out.println(avgGradeTable);
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
