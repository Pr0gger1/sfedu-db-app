package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Subject;
import com.pr0gger1.app.entities.Employee;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelIOException;

import java.sql.SQLException;

public class SetSubject extends Command {
    public SetSubject(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Faculty faculty = new Faculty();

        if (faculty.getEntityTable().getRowsCount() > 0) {
            int chosenFacultyId;
            int chosenDirectionId;
            int chosenTeacherId;

            while (true) {
                try {
                    faculty.setIdFromConsole();
                    chosenFacultyId = faculty.getId();

                    if (chosenFacultyId == 0) return;
                    if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                        System.out.println("Неверный ID");
                        continue;
                    }

                    Direction direction = new Direction(chosenFacultyId);
                    direction.setIdFromConsole();
                    chosenDirectionId = direction.getId();

                    Employee employee = new Employee(chosenFacultyId);

                    employee.setIdFromConsole();
                    chosenTeacherId = employee.getId();

                    Subject newSubject = new Subject();
                    newSubject.setSubjectNameFromConsole();
                    newSubject.setDirectionId(chosenDirectionId);
                    newSubject.setFacultyId(chosenFacultyId);
                    newSubject.setTeacherId(chosenTeacherId);

                    try {
                        Database.createSubject(newSubject);
                        System.out.println("Предмет успешно добавлен");
                    }
                    catch (SQLException error) {
                        error.printStackTrace();
                    }
                }
                catch (CancelIOException cancelException) {
                    System.out.println(cancelException.getMessage());
                    return;
                }
            }
        }
        else System.out.println("В базе данных отсутствуют факультеты");
    }
}
