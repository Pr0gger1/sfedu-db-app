package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Mark;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.exceptions.CancelIOException;
import com.pr0gger1.app.entities.Subject;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;

public class SetMark extends Command {
    private final Mark mark = new Mark();
    private final Student currentStudent = new Student();

    public SetMark(int commandId, String title) {
        super(commandId, title);
    }

    @Override
    public void execute() {
        if (currentStudent.getEntityTable().getRowsCount() > 0) {
            while (true) {
            try {
                mark.setStudentIdName(currentStudent);
                boolean isChosen = false;

                while (!isChosen) {
                    int facultyId = currentStudent.getFacultyId();
                    Subject subject = new Subject(facultyId);
                    subject.setCurrentQuery(
                        String.format(
                            "SELECT subj.id, subject_name, faculty_name FROM " +
                            "subjects subj JOIN faculties f ON subj.faculty_id = f.id " +
                            "WHERE subj.faculty_id = %d",
                            facultyId
                        )
                    );

                    subject.setIdFromConsole();
                    int chosenSubjectId = subject.getId();

                    mark.setSubjectId(chosenSubjectId);
                    isChosen = true;
                }
                mark.setMarkFromConsole();
                mark.setYearFromConsole();

                Database.createMark(mark);
                System.out.println("Данные успешно добавлены");
            }
            catch (SQLException error) {error.printStackTrace();}
            catch (CancelIOException cancelError) {return;}
            }
        }
        else System.out.println("В базе данных отсутствуют студенты");

    }
}
