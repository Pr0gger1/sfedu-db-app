package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Teacher;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;
import java.util.*;

public class SetFacultyTeacher extends Command {
    public SetFacultyTeacher(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        Faculty faculty = new Faculty();

        try {
            int chosenFacultyId = -1;

            if (faculty.getEntityTable().getRowsCount() > 0) {
                while (chosenFacultyId != 0) {

                    System.out.println("Выберите факультет (выберите ID) или 0 для выхода");
                    faculty.printEntityTable();

                    chosenFacultyId = scanner.nextInt();
                    scanner.nextLine();

                    // проверяем, есть ли выбранный факультет в таблице
                    if (!faculty.getEntityTable().fieldExists(chosenFacultyId) && chosenFacultyId != 0) {
                        System.out.println("Неверный ID");
                        continue;
                    }

                    Teacher newTeacher = new Teacher(chosenFacultyId);

                    System.out.println("Введите ФИО преподавателя: ");
                    newTeacher.fullName = scanner.nextLine();

                    System.out.println("Введите зарплату: ");
                    newTeacher.salary = scanner.nextFloat();
                    scanner.nextLine();

                    System.out.println("Введите специализацию: ");
                    newTeacher.specialization = scanner.nextLine();

                    newTeacher.setBirthdayFromConsole();

                    System.out.println("Введите номер телефона: ");
                    newTeacher.phone = scanner.nextLong();

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
