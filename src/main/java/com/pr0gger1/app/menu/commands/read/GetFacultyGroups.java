package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.exceptions.TooManyRowsException;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetFacultyGroups extends Command {
    public GetFacultyGroups(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Table table = new Table("Название группы", "Факультет");
        int count = 0;

        try {
            ResultSet groupData = Database.getData(DataTables.GROUPS, "*", "");
            while (groupData.next()) {
                count++;

                int facultyId = groupData.getInt("faculty_id");
                String groupName = groupData.getString("group_name");

                String facultyFilter = String.format("WHERE id = %d", facultyId);
                ResultSet facultyName = Database.getData(
                    DataTables.FACULTIES, "faculty_name", facultyFilter
                );

                if (facultyName.first()) {
                    table.addRow(groupName, facultyName.getString("faculty_name"));
                }
                else System.out.println("Факультеты еще не добавлены в базу");
            }

            if (count == 0) System.out.println("Группы еще не добавлены");
            else System.out.println(table);
        }
        catch (SQLException | TooManyRowsException error) {
            error.printStackTrace();
        }
    }
}
