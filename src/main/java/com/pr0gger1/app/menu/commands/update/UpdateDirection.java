package com.pr0gger1.app.menu.commands.update;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.SQLException;

public class UpdateDirection extends Command {
    public UpdateDirection(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Direction direction = new Direction();

        try {
            direction.setIdFromConsole();
            direction.fillEntity();

            direction.updateData();
            Database.updateDirection(direction);
            System.out.println("Данные обновлены");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
