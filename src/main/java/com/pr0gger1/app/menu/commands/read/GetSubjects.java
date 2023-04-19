package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.exceptions.TooManyRowsException;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GetSubjects extends Command {
    public GetSubjects(int commandId, String title) {
        super(commandId, title);
    }

    @Override
    public void execute() {
        Table subjectsTable = new Table("ID", "Предмет", "Факультет", "Направление подготовки", "Преподаватель");

        try {
            ResultSet subjects = Database.getData(
                DataTables.SUBJECTS,
                "subjects.id, subject_name, f.faculty_name, d.direction_name, t.full_name",
                "join faculties f on subjects.faculty_id = f.id " +
                        "join directions d on subjects.direction_id = d.id " +
                        "join teachers t on subjects.teacher_id = t.id"
            );

            ResultSetMetaData subjectsMetaData = subjects.getMetaData();
            int columnCount = subjectsMetaData.getColumnCount();

            while (subjects.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(subjects.getObject(i));
                }

                subjectsTable.addRow(row);
            }
            System.out.println(subjectsTable);
        }
        catch (SQLException | TooManyRowsException error) {
            error.printStackTrace();
        }
    }
}
