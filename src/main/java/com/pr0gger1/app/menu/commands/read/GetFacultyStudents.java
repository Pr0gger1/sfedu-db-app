package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.ConsoleTable.exceptions.TooManyRowsException;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class GetFacultyStudents extends Command {

    public GetFacultyStudents(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        ArrayList<Object> captions = new ArrayList<>(Arrays.asList(
            "ID студента", "ФИО студента", "Курс", "ID факультета",
            "Факультет", "ID направления","Направление подготовки",
            "Дата рождения", "Стипендия", "Номер телефона"
        ));

        Table studentTable = new Table(captions);

        try {
            String[] columns = {
                "students.id", "full_name", "course", "direction",
                "d.direction_name", "faculty", "f.faculty_name",
                "birthday", "scholarship", "students.phone"
            };

            ResultSet students = Database.getRow(
                    DataTables.STUDENTS, columns,
            "join directions d on direction = d.id join faculties f on faculty = f.id"
            );

            ResultSetMetaData studentMetadata = students.getMetaData();

            while (students.next()) {
                ArrayList<Object> row = new ArrayList<>();

                for (int i = 1; i <= studentMetadata.getColumnCount(); i++) {
                    row.add(students.getObject(i));
                }

                studentTable.addRow(row);
            }

            System.out.println(studentTable);
        }
        catch (SQLException | TooManyRowsException error) {
            error.printStackTrace();
        }
    }
}
