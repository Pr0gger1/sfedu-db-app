package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetAllDirections extends Command {
    public GetAllDirections(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Table directionTable = new Table("ID", "Направление подготовки", "Факультет", "Глава направления");
        try {
            String query = String.format(
                "SELECT d.id, f.faculty_name, direction_name, emp.full_name FROM %s d " +
                "JOIN %s f on d.faculty_id = f.id join %s emp on d.head = emp.id",
                DataTables.DIRECTIONS.getTable(),
                DataTables.FACULTIES.getTable(),
                DataTables.EMPLOYEES.getTable()
            );
            ResultSet directionQueryResult = Database.getData(query);

            directionTable.fillTable(directionQueryResult);
            System.out.println(directionTable);
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
