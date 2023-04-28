package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetSubjects extends Command {
    public GetSubjects(int commandId, String title) {
        super(commandId, title);
    }

    @Override
    public void execute() {
        Table subjectsTable = new Table("ID", "Предмет", "Факультет", "Направление подготовки", "Преподаватель");
        Faculty faculty = new Faculty();

        try {
            faculty.setIdFromConsole();

            String query = String.format(
                "SELECT subj.id, subject_name, f.faculty_name, d.direction_name, e.full_name FROM %s subj " +
                "JOIN %s f on subj.faculty_id = f.id " +
                "JOIN %s d on subj.direction_id = d.id " +
                "JOIN %s e on subj.employee_id = e.id " +
                "WHERE f.id = %s",
                DataTables.SUBJECTS.getTable(),
                DataTables.FACULTIES.getTable(),
                DataTables.DIRECTIONS.getTable(),
                DataTables.EMPLOYEES.getTable(),
                faculty.getId()
            );

            ResultSet subjectQueryResult = Database.getData(query);

            subjectsTable.fillTable(subjectQueryResult);
            System.out.println(subjectsTable);
        }
        catch (SQLException | CancelInputException error) {
            error.printStackTrace();
        }
    }
}
