package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;

import java.sql.SQLException;

public class SetFacultyStudent extends Command {
    public SetFacultyStudent(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Student newStudent = new Student();
        Faculty faculty = new Faculty();

        int chosenDirectionId;
        int chosenFacultyId;

        while (true) {
            try {
                faculty.setIdFromConsole();
                chosenFacultyId = faculty.getId();

                Direction facultyDirections = new Direction(chosenFacultyId);
                if (facultyDirections.getEntityTable().getRowsCount() == 0) {
                    System.out.println("В этом факультете не числится ни одно направление подготовки");
                    return;
                }

                facultyDirections.setIdFromConsole();
                chosenDirectionId = facultyDirections.getId();

                newStudent.setDirectionId(chosenDirectionId);
                newStudent.setFacultyId(chosenFacultyId);
                newStudent.fillEntityFromConsole();

                Database.createStudent(newStudent);
                System.out.println("Студент создан!");
            }
            catch (SQLException error) {
                error.printStackTrace();
            }
            catch (CancelInputException cancelException) {
                System.out.println(cancelException.getMessage());
                return;
            }

        }
    }
}
