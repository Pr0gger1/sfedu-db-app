package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Subject;
import com.pr0gger1.app.entities.Teacher;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;

public class SetSubject extends Command {
    public SetSubject(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Faculty faculty = new Faculty();

        if (faculty.getEntityTable().getRowsCount() > 0) {
            int chosenFacultyId;
            int chosenDirectionId;
            int chosenTeacherId;

            while (true) {
                faculty.setIdFromConsole("факультет");
                chosenFacultyId = faculty.getId();

                if (chosenFacultyId == 0) return;
                if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                Direction direction = new Direction(chosenFacultyId);
                direction.setIdFromConsole("направление подготовки");
                chosenDirectionId = direction.getId();

                Teacher teacher = new Teacher(chosenFacultyId);

                teacher.setIdFromConsole("преподавателя");
                chosenTeacherId = teacher.getId();

                Subject newSubject = new Subject();
                newSubject.setSubjectNameFromConsole();
                newSubject.setDirectionId(chosenDirectionId);
                newSubject.setFacultyId(chosenFacultyId);
                newSubject.setTeacherId(chosenTeacherId);

                try {
                    Database.createSubject(newSubject);
                    System.out.println("Предмет успешно добавлен");
                }
                catch (SQLException error) {
                    error.printStackTrace();
                }
            }
        }
        else System.out.println("В базе данных отсутствуют факультеты");
    }
}
