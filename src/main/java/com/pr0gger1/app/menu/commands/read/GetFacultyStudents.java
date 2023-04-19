package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.Faculty;
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
            int chosenFacultyId = getFacultyIdFromConsole();

            String[] columns = {
                "students.id", "full_name", "course", "direction_id",
                "d.direction_name", "students.faculty_id", "f.faculty_name",
                "birthday", "scholarship", "students.phone"
            };

            ResultSet students = Database.getData(
                DataTables.STUDENTS, columns,
                String.format(
                    "join directions d on direction_id = d.id" +
                    " join faculties f on students.faculty_id = f.id where f.id = %d",
                    chosenFacultyId
                )
            );

            generateAndPrintTable(students, studentTable);

        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }

    public int getFacultyIdFromConsole() {
        Scanner scanner = new Scanner(System.in);
        Faculty faculties = new Faculty();

        System.out.print("Выберите факультет: ");
        faculties.printEntityTable();

        int chosenFacultyId = scanner.nextInt();
        scanner.nextLine();

        return chosenFacultyId;
    }

    public void generateAndPrintTable(ResultSet students, Table studentTable) {
        try {
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
