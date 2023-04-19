package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.exceptions.TooManyRowsException;
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
        Table directionTable = new Table("Направление подготовки", "Факультет", "Глава направления");
        try {
            ResultSet directions = Database.getData(
                DataTables.DIRECTIONS,
            "f.faculty_name, direction_name, t.full_name",
            "join faculties f on directions.faculty_id = f.id " +
                    "join teachers t on directions.head = t.id"
            );

            while (directions.next()) {
                String faculty = directions.getString("faculty_name");
                String direction = directions.getString("direction_name");
                String teacherName = directions.getString("full_name");

                directionTable.addRow(direction, faculty, teacherName);
            }

            System.out.println(directionTable);
        }
        catch (SQLException | TooManyRowsException error) {
            error.printStackTrace();
        }
    }
}
