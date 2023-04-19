package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.exceptions.TooManyRowsException;
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
        Table facultyTable = new Table("Название", "Адрес", "Email", "Телефон");
        try {
            ResultSet faculties = Database.getData(DataTables.FACULTIES, "*", "");

            while (faculties.next()) {
                String facultyName = faculties.getString("faculty_name");
                String address = faculties.getString("address");
                String email = faculties.getString("email");
                long phone = faculties.getLong("phone");

                facultyTable.addRow(facultyName, address, email, String.valueOf(phone));
            }

            System.out.println(facultyTable);
        }
        catch (SQLException | TooManyRowsException error) {
            error.printStackTrace();
        }
    }
}
