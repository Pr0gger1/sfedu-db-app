package com.pr0gger1.app.menu.commands.update;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;

import java.sql.SQLException;

public class UpdateFaculty extends Command {
    public UpdateFaculty(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        while (true) {
            Faculty faculty = new Faculty();
            try {
                faculty.setIdFromConsole();
                faculty.fillEntity();
                faculty.updateData();

                Database.updateFaculty(faculty);
                System.out.println("Данные успешно обновлены");
            }
            catch (SQLException error) {error.printStackTrace();}
            catch (CancelInputException cancelException) {
                System.out.println(cancelException.getMessage());
                return;
            }
        }
    }
}
