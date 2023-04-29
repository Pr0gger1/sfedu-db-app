package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetFacultyGroups extends Command {
    public GetFacultyGroups(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Table groupTable = new Table("ID", "Название");
        Faculty faculty = new Faculty();

        try {
            faculty.setIdFromConsole();
            String query = String.format(
                "SELECT id, group_name FROM groups WHERE faculty_id = %s ORDER BY id",
                faculty.getId()
            );

            ResultSet groupQueryResult = Database.getData(query);
            groupTable.fillTable(groupQueryResult);
            System.out.println(groupTable);
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
