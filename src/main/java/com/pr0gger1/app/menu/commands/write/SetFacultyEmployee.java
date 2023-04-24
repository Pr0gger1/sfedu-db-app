package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Employee;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelIOException;

import java.sql.SQLException;

public class SetFacultyEmployee extends Command {
    public SetFacultyEmployee(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Faculty faculty = new Faculty();

        int chosenFacultyId;

        if (faculty.getEntityTable().getRowsCount() > 0) {
            while (true) {
                try {
                    faculty.setIdFromConsole();
                    chosenFacultyId = faculty.getId();

                    if (chosenFacultyId == 0) return;
                    // проверяем, есть ли выбранный факультет в таблице
                    if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                        System.out.println("Неверный ID");
                        continue;
                    }

                    Employee newEmployee = new Employee(chosenFacultyId);
                    newEmployee.setEmployeeDataFromConsole();

                    Database.createEmployee(newEmployee);
                    System.out.println("Данные успешно добавлены");
                }
                catch (SQLException error) {
                    error.printStackTrace();
                }
                catch (CancelIOException cancelException) {
                    System.out.println(cancelException.getMessage());
                    return;
                }

            }
        } else System.out.println("В базе данных отсутствуют факультеты");
    }
}
