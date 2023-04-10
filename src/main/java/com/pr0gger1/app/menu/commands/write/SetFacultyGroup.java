package com.pr0gger1.app.menu.commands.write;

import com.pr0gger1.app.menu.commands.Command;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

public class SetFacultyGroup extends Command {
    public SetFacultyGroup(int id, String title) {
        super(id, title);}

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        try {
            // загружаем факультеты из базы данных в список
            ArrayList<Integer> facultyIds = new ArrayList<>();
            ArrayList<String> facultyNames = new ArrayList<>();
            ResultSet faculties = Database.getRow(DataTables.FACULTIES, "*", "");

            while (faculties.next()) {
                int facultyId = faculties.getInt("id");
                String facultyName = faculties.getString("faculty_name");

                facultyIds.add(facultyId);
                facultyNames.add(facultyName);
            }
            faculties.close(); // закрываем ResultSet

            // выполняем цикл выбора факультета и добавления группы
            int chosenId;
            while (true) {
                System.out.println("Выберите факультет (введите ID) или 0 для выхода:");

                // выводим факультеты из списка
                int count = facultyIds.size();
                for (int i = 0; i < count; i++) {
                    int facultyId = facultyIds.get(i);

                    String facultyName = facultyNames.get(i);
                    System.out.printf("%d\t%s\n", facultyId, facultyName);
                }

                chosenId = scanner.nextInt();
                if (chosenId == 0)
                    return;

                // проверяем, есть ли выбранный факультет в списке
                if (!facultyIds.contains(chosenId)) {
                    System.out.println("Неверный ID!");
                    continue;
                }

                // запрашиваем название группы и добавляем ее в базу данных
                System.out.println("Введите название (номер) группы:");
                String groupName = scanner.next();

                String[] values = new String[] {"group_name", "faculty_id"};
                String query = String.format("%s, %d", groupName, chosenId);
                Database.createRow(DataTables.GROUPS.getTable(), values, query);

                System.out.println("Данные успешно добавлены");
            }
        }
        catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }
}
