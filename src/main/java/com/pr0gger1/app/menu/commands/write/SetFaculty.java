package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
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
            String facultyName;
            String address;
            String email;
            long phone;

            String[] insertValues = new String[] {
                    "faculty_name",
                    "address",
                    "phone",
                    "email"
            };


            System.out.print("Введите название факультета или 0 для выхода: ");
            facultyName = scanner.nextLine();

            if (facultyName.equals("0")) break;

            System.out.print("Введите адрес факультета: ");
            address = scanner.nextLine();

            System.out.print("Введите адрес электронной почты: ");
            email = scanner.nextLine();

            System.out.print("Введите номер телефона: (пример 7800444323)\n");
            phone = scanner.nextLong();

            String body = String.format("'%s', '%s', %d, '%s'", facultyName, address, phone, email);

            try {
                Database.createRow(DataTables.FACULTIES.getTable(), insertValues, body);
                System.out.println("Данные успешно добавлены");
            }
            catch (SQLException error) {
                System.out.println(error.getMessage());
            }

        }
    }
}
