package com.pr0gger1.app.menu.commands.update;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Subject;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;

import java.sql.SQLException;

public class UpdateSubject extends Command {
    public UpdateSubject(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Subject subject = new Subject();
        Faculty faculty = new Faculty();
        try {
            faculty.setIdFromConsole();
            subject.setCurrentQuery(String.format(
                "SELECT * FROM %s WHERE faculty_id = %d",
                DataTables.SUBJECTS.getTable(), faculty.getId()
            ));
            subject.setIdFromConsole();
            subject.fillEntity();
            subject.updateData();

            Database.updateSubject(subject);
            System.out.println("Данные успешно обновлены");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException cancelInput) {
            System.out.println(cancelInput.getMessage());
        }
    }
}
