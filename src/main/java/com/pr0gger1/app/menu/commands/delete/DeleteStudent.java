package com.pr0gger1.app.menu.commands.delete;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.SQLException;

public class DeleteStudent extends Command {
    public DeleteStudent(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Faculty faculty = new Faculty();
        Student student = new Student();
        try {
            faculty.setIdFromConsole();
            student.setCurrentQuery(String.format(
                "SELECT * FROM %s WHERE faculty_id = %d",
                DataTables.STUDENTS.getTable(),
                faculty.getId()
            ));

            student.setIdFromConsole();
            Database.deleteEntityRow(student);
            System.out.println("Данные успешно удалены");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
