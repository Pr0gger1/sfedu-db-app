package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Employee;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class GetFacultyEmployeers extends Command {

    public GetFacultyEmployeers(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        ArrayList<Object> tableCaption = new ArrayList<>(Arrays.asList(
            "ID", "ФИО преподавателя", "Должность",
            "Факультет", "Зарплата", "Дата рождения",
            "Номер телефона"
        ));

        Table employeeTable = new Table(tableCaption);
        Faculty faculties = new Faculty();

        try {
            String query = String.format(
                "SELECT e.id, full_name, specialization, f.faculty_name, salary, birthday, e.phone FROM %s e " +
                "JOIN %s f on e.faculty_id = f.id",
                DataTables.EMPLOYEES.getTable(),
                DataTables.FACULTIES.getTable()
            );

            ResultSet employeesQueryResult = Database.getData(query);
            employeeTable.fillTable(employeesQueryResult);

            faculties.setIdFromConsole();
            int chosenFacultyId = faculties.getId();

            Employee teachersList = new Employee(chosenFacultyId);

            // изменение запроса для формирования таблицы преподавателей
            teachersList.setCurrentQuery(String.format(
                    "SELECT * FROM %s WHERE faculty_id = %d",
                    DataTables.EMPLOYEES.getTable(),chosenFacultyId
                )
            );
            teachersList.printEntityTable();
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
