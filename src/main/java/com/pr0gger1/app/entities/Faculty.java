package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Faculty extends Entity {
    private final Scanner scanner = new Scanner(System.in);
    private String facultyName;
    private String address;
    private String email;
    private long phone;


    public Faculty(int facultyId) {
        super(DataTables.FACULTIES, new ArrayList<>(Arrays.asList("id", "faculty_name")));
        setLocalizedColumns(new ArrayList<>(Arrays.asList(
            "ID", "Название факультета", "Адрес", "Номер телефона", "Email"
        )));

        this.id = facultyId;
    }

    public Faculty() {
        super(DataTables.FACULTIES, new ArrayList<>(Arrays.asList("id", "faculty_name")));
        setLocalizedColumns(new ArrayList<>(Arrays.asList(
            "ID", "Название факультета", "Адрес", "Номер телефона", "Email"
        )));
    }

    public long getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public void setFacultyNameFromConsole() {
        System.out.print("Введите название факультета или 0 для выхода: ");
        facultyName = scanner.nextLine();
    }

    public void setFacultyDataFromConsole() {
        System.out.print("Введите адрес факультета: ");
        setAddress(scanner.nextLine());

        System.out.print("Введите адрес электронной почты: ");
        setEmail(scanner.nextLine());

        System.out.print("Введите номер телефона: (пример 7800444323)\n");
        setPhone(scanner.nextLong());
        scanner.nextLine();
    }

    @Override
    public void fillEntity() {
        String facultyDataQuery = String.format("SELECT * FROM %s WHERE id = %d", DataTables.FACULTIES, id);
        try {
            ResultSet data = Database.getData(facultyDataQuery);

            while (data.next()) {
                facultyName = data.getString("faculty_name");
                address = data.getString("address");
                email = data.getString("email");
                phone = data.getLong("phone");
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }

    public void updateData() {
        ArrayList<Runnable> setters = new ArrayList<>(Arrays.asList(
            () -> {
                System.out.print("Введите ID: ");
                setId(scanner.nextInt());
                scanner.nextLine();
            },
            () -> {
                System.out.print("Введите новое название факультета: ");
                setFacultyName(scanner.nextLine());
            },
            () -> {
                System.out.print("Введите новый адрес: ");
                setAddress(scanner.nextLine());
            },
             () -> {
                System.out.print("Введите новый номер телефона: ");
                setPhone(scanner.nextLong());
                scanner.nextLine();
            },
            () -> {
                System.out.print("Введите новый email: ");
                setEmail(scanner.nextLine());
            }
        ));

        super.updateData(setters);
    }

    public String toString() {
        return String.format(
            "{\n" +
            "\n\tfacultyId: %d," +
            "\n\tfacultyName: %s," +
            "\n\taddress: %s," +
            "\n\tphone: %d," +
            "\n\temail: %s," +
            "\n}",
            id, facultyName,
            address, phone, email
        );
    }
}
