package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Employee;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

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

                    Employee newEmployee = new Employee(chosenFacultyId);
                    newEmployee.setEmployeeDataFromConsole();

                    Database.createEmployee(newEmployee);
                    System.out.println("Данные успешно добавлены");
                }
                catch (SQLException error) {
                    error.printStackTrace();
                }
                catch (CancelInputException | NoDataException error) {
                    System.out.println(error.getMessage());
                    return;
                }

            }
        } else System.out.println("В базе данных отсутствуют факультеты");
    }
}
