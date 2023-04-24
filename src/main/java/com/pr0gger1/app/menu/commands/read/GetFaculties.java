package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetFaculties extends Command {
    public GetFaculties(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Table facultyTable = new Table("ID", "Название", "Адрес", "Email", "Телефон");
        try {
            ResultSet facultyQueryResult = Database.getData(DataTables.FACULTIES, "*", "ORDER BY id");
            facultyTable.fillTable(facultyQueryResult);
            System.out.println(facultyTable);
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
