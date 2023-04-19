package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Direction;
import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.Student;
import com.pr0gger1.app.menu.commands.ConsoleGetter;
import com.pr0gger1.database.Database;

import java.sql.SQLException;

public class SetFacultyStudent extends ConsoleGetter {
    public SetFacultyStudent(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Student newStudent = new Student();
        Faculty faculty = new Faculty();

        try {
            int chosenDirectionId;
            int chosenFacultyId;

            while (true) {
                chosenFacultyId = getFacultyIdFromConsole(faculty);

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

                chosenDirectionId = getDirectionIdFromConsole(facultyDirections);

                if (!facultyDirections.getEntityTable().fieldExists(chosenDirectionId)) {
                    System.out.println("Неверный ID");
                    continue;
                }

                newStudent.setDirectionId(chosenDirectionId);
                newStudent.setFacultyId(chosenFacultyId);

                setStudentDataFromConsole(newStudent);

                Database.createStudent(newStudent);
                System.out.println("Студент создан!");
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
