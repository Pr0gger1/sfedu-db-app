package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;

public class SetFaculty extends Command {
    public SetFaculty(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        try {
            while (true) {
                Faculty newFaculty = new Faculty();
                newFaculty.setFacultyNameFromConsole();

                if (newFaculty.getFacultyName().equals("0")) return;
                newFaculty.setFacultyDataFromConsole();

                Database.createFaculty(newFaculty);
                System.out.println("Данные успешно добавлены");
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
