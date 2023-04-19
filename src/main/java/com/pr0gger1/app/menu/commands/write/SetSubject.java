package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Subject;
import com.pr0gger1.app.entities.Teacher;
import com.pr0gger1.app.menu.commands.ConsoleGetter;
import com.pr0gger1.database.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class SetSubject extends ConsoleGetter {
    public SetSubject(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        Faculty faculty = new Faculty();

        if (faculty.getEntityTable().getRowsCount() > 0) {
            int chosenFacultyId;
            int chosenDirectionId;
            int chosenTeacherId;

            while (true) {
                chosenFacultyId = getFacultyIdFromConsole(faculty);

                if (chosenFacultyId == 0) return;
                if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                Direction direction = new Direction(chosenFacultyId);
                direction.printEntityTable();

                chosenDirectionId = getDirectionIdFromConsole(direction);

                if (!direction.getEntityTable().fieldExists(chosenDirectionId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                Teacher teacher = new Teacher(chosenFacultyId);
                teacher.printEntityTable();

                chosenTeacherId = getTeacherIdFromConsole(teacher);

                if (!teacher.getEntityTable().fieldExists(chosenTeacherId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                Subject newSubject = new Subject();
                System.out.print("Введите название предмета: ");
                newSubject.setSubjectName(scanner.nextLine());
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
