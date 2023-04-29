package com.pr0gger1.app.menu.commands.grade;

import com.pr0gger1.app.entities.Mark;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.SQLException;

public class UpdateMark extends Command {
    public UpdateMark(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Student student = new Student();

        try {
            student.searchStudent();

            Mark mark = new Mark(student.getId());
            mark.setIdFromConsole();
            mark.fillEntity();
            mark.updateData();

            Database.updateMark(mark);
            System.out.println("Данные успешно изменены");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException | NoDataException error) {
            System.out.println(error.getMessage());
        }
    }
}
