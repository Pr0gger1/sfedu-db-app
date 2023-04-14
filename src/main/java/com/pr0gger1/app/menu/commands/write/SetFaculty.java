package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.entities.Faculty;
import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class SetFaculty extends Command {
    public SetFaculty(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Faculty newFaculty = new Faculty();

            System.out.print("Введите название факультета или 0 для выхода: ");
            newFaculty.facultyName = scanner.nextLine();

            if (newFaculty.facultyName.equals("0")) break;

            System.out.print("Введите адрес факультета: ");
            newFaculty.address = scanner.nextLine();

            System.out.print("Введите адрес электронной почты: ");
            newFaculty.email = scanner.nextLine();

            System.out.print("Введите номер телефона: (пример 7800444323)\n");
            newFaculty.phone = scanner.nextLong();

            try {
                Database.createFaculty(newFaculty);
                System.out.println("Данные успешно добавлены");
            }
            catch (SQLException error) {
                System.out.println(error.getMessage());
            }
        }
    }
}
