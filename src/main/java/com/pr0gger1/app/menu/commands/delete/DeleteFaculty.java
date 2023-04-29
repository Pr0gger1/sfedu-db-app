package com.pr0gger1.app.menu.commands.delete;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.SQLException;

public class DeleteFaculty extends Command {
    public DeleteFaculty(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Faculty faculty = new Faculty();
        try {
            faculty.setIdFromConsole();
            Database.deleteEntityRow(faculty);
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
