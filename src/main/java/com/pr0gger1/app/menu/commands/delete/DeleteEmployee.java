package com.pr0gger1.app.menu.commands.delete;

import com.pr0gger1.app.entities.Employee;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.SQLException;

public class DeleteEmployee extends Command {
    public DeleteEmployee(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Faculty faculty = new Faculty();
        Employee employee = new Employee();
        try {
            faculty.setIdFromConsole();

            employee.setCurrentQuery(String.format(
                "SELECT * FROM %s WHERE faculty_id = %d",
                DataTables.EMPLOYEES.getTable(),
                faculty.getId()
            ));
            employee.setIdFromConsole();
            Database.deleteEntityRow(employee);
            System.out.println("Данные успешно удалены");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
