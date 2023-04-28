package com.pr0gger1.app.menu.commands.delete;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;

import java.sql.SQLException;

public class DeleteDirection extends Command {
    public DeleteDirection(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Direction direction = new Direction();
        Faculty faculty = new Faculty();
        try {
            faculty.setIdFromConsole();

            direction.setCurrentQuery(String.format(
                "SELECT * FROM %s WHERE faculty_id = %d",
                DataTables.DIRECTIONS.getTable(),
                faculty.getId()
            ));
            direction.setIdFromConsole();
            Database.deleteEntityRow(direction);
            System.out.println("Данные успешно удалены");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException cancelInput) {
            System.out.println(cancelInput.getMessage());
        }
    }
}
