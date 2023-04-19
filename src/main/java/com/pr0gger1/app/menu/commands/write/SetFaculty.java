package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.ConsoleGetter;
import com.pr0gger1.database.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class SetFaculty extends ConsoleGetter {
    public SetFaculty(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                Faculty newFaculty = new Faculty();
                System.out.print("Введите название факультета или 0 для выхода: ");
                String facultyName = scanner.nextLine();

                if (facultyName.equals("0")) return;
                else newFaculty.setFacultyName(facultyName);
                setFacultyDataFromConsole(newFaculty);

                Database.createFaculty(newFaculty);
                System.out.println("Данные успешно добавлены");
            }
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
