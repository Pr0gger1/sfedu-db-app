package com.pr0gger1.app.menu.commands.update;

import com.pr0gger1.app.entities.Employee;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.SQLException;

public class UpdateEmployee extends Command {
    public UpdateEmployee(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Employee employee = new Employee();
        Faculty faculty = new Faculty();
        try {
            faculty.setIdFromConsole();

            employee.setCurrentQuery(String.format(
                "SELECT * FROM %s WHERE faculty_id = %d",
                DataTables.EMPLOYEES, faculty.getId()
            ));
            employee.setIdFromConsole();
            employee.fillEntity();
            employee.updateData();

            Database.updateEmployee(employee);
            System.out.println("Данные успешно обновлены");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
