package com.pr0gger1.app.menu.commands.delete;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.StudentGroup;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.SQLException;

public class DeleteFacultyGroup extends Command {
    public DeleteFacultyGroup(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Faculty faculty = new Faculty();
        StudentGroup studentGroup = new StudentGroup();
        try {
            faculty.setIdFromConsole();

            studentGroup.setCurrentQuery(String.format(
                "SELECT * FROM %s WHERE faculty_id = %d",
                DataTables.GROUPS.getTable(),
                faculty.getId()
            ));
            studentGroup.setIdFromConsole();
            Database.deleteEntityRow(studentGroup);
            System.out.println("Данные успешно удалены");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
