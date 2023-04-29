package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.StudentGroup;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.NoDataException;

import java.sql.SQLException;

public class SetFacultyGroup extends Command {
    Faculty faculty = new Faculty();
    StudentGroup newStudentGroup = new StudentGroup();

    public SetFacultyGroup(int id, String title) {
        super(id, title);}

    @Override
    public void execute() {
        int chosenFacultyId;

        if (faculty.getEntityTable().getRowsCount() > 0) {
            while (true) {
                try {
                    faculty.setIdFromConsole();
                    chosenFacultyId = faculty.getId();

                    newStudentGroup.setGroupNameFromConsole();
                    newStudentGroup.setFacultyId(chosenFacultyId);

                    Database.createGroup(newStudentGroup);
                    System.out.println("Данные успешно добавлены");

                }
                catch (SQLException error) {error.printStackTrace();}
                catch(CancelInputException | NoDataException error){
                    System.out.println(error.getMessage());
                    return;
                }
            }
        } else System.out.println("В базе данных отсутствуют факультеты");
    }
}
