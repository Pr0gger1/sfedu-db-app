package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Teacher;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class SetDirection extends Command {
    public SetDirection(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        Direction newDirection = new Direction();
        Faculty faculty = new Faculty();
        Teacher teacher = new Teacher();

        try {
            int chosenFacultyId;
            int chosenTeacherId;

            if (faculty.getEntityTable().getRowsCount() > 0 && teacher.getEntityTable().getRowsCount() > 0) {
                while (true) {
                    System.out.println("Выберите факультет (выберите ID) или 0 для выхода");
                    faculty.printEntityTable();
                    chosenFacultyId = scanner.nextInt();

                    if (chosenFacultyId == 0) return;
                    if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                        System.out.println("Неверный ID");
                    }

                    System.out.println("Выберите главу направления подготовки среди преподавателей или 0 для выхода");
                    teacher.printEntityTable();
                    chosenTeacherId = scanner.nextInt();

                    if (chosenTeacherId == 0) return;
                    if (!teacher.getEntityTable().fieldExists(chosenTeacherId)) {
                        System.out.println("Неверный ID");
                        continue;
                    }

                    System.out.println("Введите название направления подготовки");
                    newDirection.directionName = scanner.next();

                    newDirection.setFacultyId(chosenFacultyId);
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
