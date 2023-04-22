package com.pr0gger1.app.menu.commands.update;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;

public class UpdateFaculty extends Command {
    Faculty faculty = new Faculty();



    public UpdateFaculty(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        while (true) {
            try {
                faculty.setIdFromConsole("факультет");
                faculty.fillEntity();
                faculty.updateData();

                Database.updateFaculty(faculty);
                System.out.println("Данные успешно обновлены");
            }
            catch (SQLException error) {
                error.printStackTrace();
            }
        }
    }
}