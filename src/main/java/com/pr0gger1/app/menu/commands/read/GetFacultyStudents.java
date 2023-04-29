package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        Faculty faculty = new Faculty();

        try {
            faculty.setIdFromConsole();
            int chosenFacultyId = faculty.getId();

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

            studentTable.fillTable(students);
            System.out.println(studentTable);

        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
