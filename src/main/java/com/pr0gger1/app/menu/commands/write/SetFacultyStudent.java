package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelIOException;

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

                if (chosenFacultyId == 0) return;
                if (!faculty.getEntityTable().fieldExists(chosenFacultyId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                Direction facultyDirections = new Direction(chosenFacultyId);
                if (facultyDirections.getEntityTable().getRowsCount() == 0) {
                    System.out.println("В этом факультете не числится ни одно направление подготовки");
                    return;
                }

                facultyDirections.setIdFromConsole();
                chosenDirectionId = facultyDirections.getId();

                if (!facultyDirections.getEntityTable().fieldExists(chosenDirectionId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                newStudent.setDirectionId(chosenDirectionId);
                newStudent.setFacultyId(chosenFacultyId);
                newStudent.fillStudentData();

                Database.createStudent(newStudent);
                System.out.println("Студент создан!");
            }
            catch (SQLException error) {
                error.printStackTrace();
            }
            catch (CancelIOException cancelException) {
                System.out.println(cancelException.getMessage());
                return;
            }

        }
    }
}
