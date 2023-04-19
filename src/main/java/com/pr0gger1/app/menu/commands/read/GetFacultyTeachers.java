package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Teacher;
import com.pr0gger1.exceptions.TooManyRowsException;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GetFacultyTeachers extends Command {

    public GetFacultyTeachers(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Object> tableCaption = new ArrayList<>(Arrays.asList(
            "ID", "ФИО преподавателя", "Должность",
            "Факультет", "Зарплата", "Дата рождения",
            "Номер телефона"
        ));

        Table teacherTable = new Table(tableCaption);
        Faculty faculties = new Faculty();

        try {
            ResultSet teachers = Database.getData(
                DataTables.TEACHERS,
                "teachers.id, full_name, specialization, f.faculty_name, salary, birthday, teachers.phone",
                "join faculties f on teachers.faculty_id = f.id"
            );

            ResultSetMetaData teachersMetaData = teachers.getMetaData();
            int columnCount = teachersMetaData.getColumnCount();

            while (teachers.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(teachers.getObject(i));
                }

                teacherTable.addRow(row);
            }

            faculties.printEntityTable();
            System.out.println("Выберите факультет");
            int chosenFacultyId = scanner.nextInt();
            scanner.nextLine();
            Teacher teachersList = new Teacher(chosenFacultyId);

            teachersList.setCurrentQuery(
                String.format("SELECT * FROM teachers WHERE faculty_id = %d", chosenFacultyId)
            );

            teachersList.printEntityTable();
        }
        catch (SQLException | TooManyRowsException error) {
            error.printStackTrace();
        }
    }
}
