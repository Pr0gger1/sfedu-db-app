package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.formatOut.Table;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class GetFaculties extends Command {
    public GetFaculties(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        ArrayList<String> facultyColumns = new ArrayList<>(Arrays.asList(
                "Название", "Адрес", "Email", "Телефон"
        ));

        ArrayList<ArrayList<String>> facultyRows = new ArrayList<>();
        Table facultyTable = new Table(facultyColumns);

        try {
            ResultSet faculties = Database.getRow(DataTables.FACULTIES, "*", "");

            while (faculties.next()) {
                String facultyName = faculties.getString("faculty_name");
                String address = faculties.getString("address");
                String email = faculties.getString("email");
                long phone = faculties.getLong("phone");


                ArrayList<String> row = new ArrayList<>(
                    Arrays.asList(
                        facultyName, address, email, String.valueOf(phone)
                ));

                facultyRows.add(row);
            }
            facultyTable.addRows(facultyRows);
            facultyTable.printTable();
        }
        catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }
}
