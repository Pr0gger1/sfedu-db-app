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
        Faculty faculty = new Faculty();

        try {
            int chosenDirectionId;
            int chosenFacultyId;

            while (true) {
                System.out.println("Выберите факультет (выберите ID) или 0 для выхода");
                faculty.printEntityTable();
                chosenFacultyId = scanner.nextInt();
                scanner.nextLine();

                if (chosenFacultyId == 0) return;
                if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                Direction facultyDirections = new Direction(chosenFacultyId);
                if (facultyDirections.getEntityTable().getRowsCount() == 0) {
                    System.out.println("В этом факультете не числится ни одно направление подготовки");
                    return;
                }

                System.out.println("Выберите направление подготовки (выберите ID) или 0 для выхода");
                facultyDirections.printEntityTable();
                chosenDirectionId = scanner.nextInt();
                scanner.nextLine();

                if (chosenDirectionId == 0) return;
                if (!facultyDirections.getEntityTable().fieldExists(chosenDirectionId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                newStudent.setDirectionId(chosenDirectionId);
                newStudent.setFacultyId(chosenFacultyId);

                System.out.print("Введите ФИО студента:");
                newStudent.fullName = scanner.nextLine();

                System.out.print("Введите номер курса:");
                newStudent.course = scanner.nextShort();
                scanner.nextLine();

                newStudent.setBirthdayFromConsole();

                System.out.print("Введите размер стипендии (0, если нет): ");
                newStudent.scholarship = scanner.nextFloat();
                scanner.nextLine();

                System.out.print("Введите номер телефона:");
                newStudent.phone = scanner.nextInt();

                Database.createStudent(newStudent);
                System.out.println("Студент создан!");
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
