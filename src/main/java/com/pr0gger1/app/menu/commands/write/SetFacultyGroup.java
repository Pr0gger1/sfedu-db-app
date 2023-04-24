package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.entities.StudentsGroup;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelIOException;

import java.sql.SQLException;

public class SetFacultyGroup extends Command {
    Faculty faculty = new Faculty();
    StudentsGroup newStudentsGroup = new StudentsGroup();

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

                    newStudentsGroup.setGroupNameFromConsole();
                    newStudentsGroup.setFacultyId(chosenFacultyId);

                    Database.createGroup(newStudentsGroup);
                    System.out.println("Данные успешно добавлены");

                }
                catch (SQLException error) {error.printStackTrace();}
                catch(CancelIOException cancelException){
                    System.out.println(cancelException.getMessage());
                    return;
                }
            }
        } else System.out.println("В базе данных отсутствуют факультеты");
    }
}
