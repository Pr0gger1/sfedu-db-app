package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Employee;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.SQLException;

public class SetDirection extends Command {
    public SetDirection(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Faculty faculty = new Faculty();
        Employee employee = new Employee();
        Direction newDirection = new Direction();

        int chosenEmployeeId;

        if (faculty.getEntityTable().getRowsCount() > 0 && employee.getEntityTable().getRowsCount() > 0) {
            while (true) {
                try {
                    faculty.setIdFromConsole();

                    employee.setCurrentQuery(String.format(
                        "SELECT id, full_name FROM %s WHERE faculty_id = %d",
                        DataTables.EMPLOYEES.getTable(), faculty.getId()
                    ));
                    employee.setIdFromConsole();
                    chosenEmployeeId = employee.getId();

                    newDirection.setFacultyId(faculty.getId());
                    newDirection.setDirectionNameFromConsole();
                    newDirection.setHeadOfDirectionId(chosenEmployeeId);

                    Database.createDirection(newDirection);
                    System.out.println("Данные успешно добавлены");

                }
                catch (SQLException error) {error.printStackTrace();}
                catch (CancelInputException | NoDataException error) {
                    System.out.println(error.getMessage());
                    return;
                }
            }
        } else System.out.println("В базе данных отсутствуют факультеты или преподаватели");
    }
}
