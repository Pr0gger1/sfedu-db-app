package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class SetFacultyStudent extends Command {
    public SetFacultyStudent(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        Student newStudent = new Student();
        Direction direction = new Direction();
        Faculty faculty = new Faculty();

        try {
            int chosenDirectionId;
            int chosenFacultyId;

            while (true) {
                System.out.println("Выберите факультет (выберите ID) или 0 для выхода");
                faculty.printEntityTable();
                chosenFacultyId = scanner.nextInt();

                if (chosenFacultyId == 0) return;
                if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                System.out.println("Выберите направление подготовки (выберите ID) или 0 для выхода");
                direction.printEntityTable();
                chosenDirectionId = scanner.nextInt();

                if (chosenDirectionId == 0) return;
                if (!direction.getEntityTable().fieldExists(chosenDirectionId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                newStudent.setDirectionId(chosenDirectionId);
                newStudent.setFacultyId(chosenFacultyId);

                System.out.println("Введите ФИО студента:");
                newStudent.fullName = scanner.next();

                System.out.println("Введите номер курса:");
                newStudent.course = scanner.nextShort();

                newStudent.setBirthdayFromConsole();

                System.out.println("Введите размер стипендии:");
                newStudent.scholarship = scanner.nextFloat();

                System.out.println("Введите номер телефона:");
                newStudent.phone = scanner.nextInt();

                Database.createStudent(newStudent);
                System.out.println("Студент создан!");
            }
        }
        catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }
}
