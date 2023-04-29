package com.pr0gger1.app.menu.commands.grade;

import com.pr0gger1.app.ConsoleTable.Table;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Mark;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

public class GetMarks extends Command {
    public GetMarks(int commandId, String title) {
        super(commandId, title);
    }

    @Override
    public void execute() {
        while (true) {
            try {
                Table markTable;
                Student student = new Student();
                Faculty faculty = new Faculty();

                faculty.setIdFromConsole();
                student.setFacultyId(faculty.getId());
                student.setCurrentQuery(String.format(
                    "SELECT id, full_name FROM %s WHERE faculty_id = %d",
                    DataTables.STUDENTS.getTable(), student.getFacultyId()
                ));

                student.setIdFromConsole();

                Mark mark = new Mark();
                mark.setCurrentQuery(String.format(
                    "SELECT subj.subject_name, mark, year FROM %s" +
                    " join subjects subj on subject_id = subj.id WHERE student_id = %d",
                    DataTables.MARKS, student.getId()
                ));
                markTable = mark.getEntityTable();


                if (markTable.getRowsCount() == 0)
                    System.out.println("Успеваемости данного студента нет в базе данных");
                else System.out.println(markTable);
                if (!continueCommand()) return;

            } catch (CancelInputException | NoDataException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }
}
