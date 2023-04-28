package com.pr0gger1.app.menu.commands.grade;

import com.pr0gger1.app.entities.Mark;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;

import java.sql.SQLException;

public class DeleteMark extends Command {
    public DeleteMark(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Student student = new Student();
        Mark mark = new Mark();
        try {
            student.searchStudent();
            mark.setCurrentQuery(String.format(
                "SELECT * FROM %s WHERE student_id = %d",
                DataTables.MARKS.getTable(),
                student.getId()
            ));
            mark.setIdFromConsole();

            Database.deleteEntityRow(mark);
            if (mark.getId() != 0)
                System.out.println("Данные успешно удалены");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        catch (CancelInputException cancelInput) {
            System.out.println(cancelInput.getMessage());
        }
    }
}
