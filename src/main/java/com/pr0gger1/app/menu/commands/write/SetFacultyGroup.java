package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.StudentsGroup;
import com.pr0gger1.app.menu.commands.ConsoleGetter;
import com.pr0gger1.database.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class SetFacultyGroup extends ConsoleGetter {
    Faculty faculty = new Faculty();
    StudentsGroup newStudentsGroup = new StudentsGroup();

    public SetFacultyGroup(int id, String title) {
        super(id, title);}

    @Override
    public void execute() {
        try {
            // выполняем цикл выбора факультета и добавления группы
            int chosenFacultyId;

            if (faculty.getEntityTable().getRowsCount() > 0) {
                while (true) {
                    chosenFacultyId = getFacultyIdFromConsole(faculty);

                    if (chosenFacultyId == 0) return;

                    // проверяем, есть ли выбранный факультет в списке
                    if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                        System.out.println("Неверный ID");
                        continue;
                    }

                    getStudentGroupName(newStudentsGroup);

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
