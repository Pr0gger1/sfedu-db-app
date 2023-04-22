package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Teacher;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;

public class SetFacultyTeacher extends Command {
    public SetFacultyTeacher(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Faculty faculty = new Faculty();

        try {
            int chosenFacultyId;

            if (faculty.getEntityTable().getRowsCount() > 0) {
                while (true) {
                    faculty.setIdFromConsole("факультет");
                    chosenFacultyId = faculty.getId();

                    if (chosenFacultyId == 0) return;
                    // проверяем, есть ли выбранный факультет в таблице
                    if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                        System.out.println("Неверный ID");
                        continue;
                    }

                    Teacher newTeacher = new Teacher(chosenFacultyId);
                    newTeacher.setTeacherDataFromConsole();

                    Database.createTeacher(newTeacher);
                    System.out.println("Данные успешно добавлены");
                }
            }
            else System.out.println("В базе данных отсутствуют факультеты");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
