package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Teacher;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;

public class SetDirection extends Command {
    Faculty faculty = new Faculty();
    Teacher teacher = new Teacher();
    Direction newDirection = new Direction();

    public SetDirection(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        try {
            int chosenFacultyId;
            int chosenTeacherId;

            if (faculty.getEntityTable().getRowsCount() > 0 && teacher.getEntityTable().getRowsCount() > 0) {
                while (true) {
                    faculty.setIdFromConsole("факультет");
                    chosenFacultyId = faculty.getId();

                    if (chosenFacultyId == 0) return;
                    if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                        System.out.println("Неверный ID");
                    }

                    teacher.setIdFromConsole("преподавателя");
                    chosenTeacherId = teacher.getId();

                    if (chosenTeacherId == 0) return;
                    if (!teacher.getEntityTable().fieldExists(chosenTeacherId)) {
                        System.out.println("Неверный ID");
                        continue;
                    }

                    newDirection.setDirectionNameFromConsole();
                    newDirection.setHeadOfDirectionId(chosenTeacherId);

                    Database.createDirection(newDirection);
                }
            }
            else System.out.println("В базе данных отсутствуют факультеты или преподаватели");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
