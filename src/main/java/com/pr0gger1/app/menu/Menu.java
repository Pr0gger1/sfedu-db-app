package com.pr0gger1.app.menu;

import com.pr0gger1.app.menu.commands.*;
import com.pr0gger1.app.menu.commands.DeleteLevel;
import com.pr0gger1.app.menu.commands.ReadLevel;
import com.pr0gger1.app.menu.commands.WriteLevel;
import java.util.Scanner;

public class Menu {
    private static byte level = 1;

    private static final Command[] firstLevel = {
        new ReadLevel(1, "Чтение данных"),
        new WriteLevel(2, "Запись данных"),
        new DeleteLevel(3, "Удаление данных"),
        new Exit()
    };


    public static void open() {
        outMenu(
            firstLevel,
    "Вас приветствует менеджер базы данных ЮФУ" +
            "\nВыберите интересующую вас операцию:"
        );
    }

    public static void levelUp() {
        byte maxLevel = 2;
        if (level < maxLevel)
            level++;
    }

    public static void levelDown() {
        byte minLevel = 1;
        if (level > minLevel)
            level--;
    }


    public static void outMenu(Command[] commands, String message) {
        Scanner scanner = new Scanner(System.in);
        int menuValue;

        while (true) {
            if (message.length() != 0)
                System.out.println(message);

            // Вывод списка команд
            for (Command command : commands)
                System.out.printf("%d. - %s\n", command.getId(), command.getCommandTitle());

            // Ввод команды
            menuValue = scanner.nextInt();

            if (menuValue == 0 && level > 1) {
                levelDown();
                break;
            }


            if (menuValue < commands.length)
                for (Command command : commands)
                    if (command.getId() == menuValue)
                        command.execute();

            else System.out.println("Неверная команда");
        }
    }
}