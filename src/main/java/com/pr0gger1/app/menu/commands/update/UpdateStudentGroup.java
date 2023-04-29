package com.pr0gger1.app.menu.commands.update;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.StudentGroup;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.SQLException;

public class UpdateStudentGroup extends Command {
    public UpdateStudentGroup(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        StudentGroup group = new StudentGroup();
        Faculty faculty = new Faculty();
        try {
            faculty.setIdFromConsole();

            group.setCurrentQuery(String.format(
                "SELECT * FROM %s WHERE faculty_id = %d",
                    DataTables.GROUPS.getTable(), faculty.getId()
            ));

            group.setIdFromConsole();
            group.fillEntity();
            group.updateData();

            Database.updateGroup(group);
            System.out.println("Данные успешно обновлены");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
