package com.pr0gger1.app.menu.commands.update;

import com.pr0gger1.app.entities.Student;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;

import java.sql.SQLException;

public class UpdateStudent extends Command {
    public UpdateStudent(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        while (true) {
            Student student = new Student();
            try {
                student.searchStudent();
                student.updateData();

                Database.updateStudent(student);
                System.out.println("Данные успешно обновлены");
            }
            catch (SQLException error) {
                error.printStackTrace();
            }
            catch (CancelInputException cancelException) {
                return;
            }
        }
    }
}
