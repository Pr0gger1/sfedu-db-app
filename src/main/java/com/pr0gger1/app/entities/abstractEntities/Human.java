package com.pr0gger1.app.entities.abstractEntities;

import com.pr0gger1.database.DataTables;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Human extends Entity {
    protected LocalDate birthday;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    protected Human(DataTables table, ArrayList<String> columns) {
        super(table, columns);
    }

    protected Human(DataTables table, ArrayList<String> columns, String query) {
        super(table, columns, query);
    }

    public void setBirthdayFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите дату рождения (пример 12.01.1985): ");

        while (true) {
            try {
                birthday = LocalDate.parse(scanner.next(), dateTimeFormatter);
                break;
            }
            catch (DateTimeParseException parseError) {
                System.out.println("Ошибка ввода даты: " + parseError.getMessage());
            }
        }
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getFormattedBirthday() {
        return birthday.format(dateTimeFormatter);
    }
}
