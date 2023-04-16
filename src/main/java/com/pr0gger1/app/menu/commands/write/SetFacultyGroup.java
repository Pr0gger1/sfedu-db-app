package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.StudentsGroup;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class SetFacultyGroup extends Command {
    public SetFacultyGroup(int id, String title) {
        super(id, title);}

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        Faculty faculty = new Faculty();
        StudentsGroup newStudentsGroup = new StudentsGroup();

        try {
            // выполняем цикл выбора факультета и добавления группы
            int chosenFacultyId = -1;

            if (faculty.getEntityTable().getRowsCount() > 0) {
                while (chosenFacultyId != 0) {
                    System.out.println("Выберите факультет (введите ID) или 0 для выхода:");
                    faculty.printEntityTable();
                    chosenFacultyId = scanner.nextInt();

                    // проверяем, есть ли выбранный факультет в списке
                    if (!faculty.getEntityTable().fieldExists(chosenFacultyId) && chosenFacultyId != 0) {
                        System.out.println("Неверный ID");
                        continue;
                    }

                    System.out.println("Введите название (номер) группы:");
                    newStudentsGroup.groupName = scanner.next();

                    newStudentsGroup.setFacultyId(chosenFacultyId);

                    Database.createGroup(newStudentsGroup);
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
