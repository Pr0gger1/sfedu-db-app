package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Teacher extends Entity {
    private int id = -1;
    private int facultyId = -1;
    public long phone = -1;
    public String fullName;
    public String specialization;
    public String birthday;
    public float salary;

    public Teacher(int id, int facultyId) {
        super(DataTables.TEACHERS, new ArrayList<>(Arrays.asList("id", "full_name")));
        this.id = id;
        this.facultyId = facultyId;
    }

    public Teacher(int facultyId) {
        super(DataTables.TEACHERS, new ArrayList<>(Arrays.asList("id", "full_name")));
        this.facultyId = facultyId;
    }

    public Teacher() {
        super(DataTables.TEACHERS, new ArrayList<>(Arrays.asList("id", "full_name")));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id == -1) this.id = id;
        else System.out.println("Невозможно изменить ID");
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int id) {
        if (facultyId == -1) facultyId = id;
        else System.out.println("Невозможно изменить ID факультета");
    }

    public void setBirthdayFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите дату рождения (пример 1984-05-25): ");

        while (true) {
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

                Date date = dateFormatter.parse(scanner.next());
                birthday = dateFormatter.format(date);

                break;
            } catch (ParseException parseError) {
                System.out.println(parseError.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Teacher: {\n" +
                "\nid: %d," +
                "\nfacultyId: %d," +
                "\nfull_name: %s," +
                "\nphone: %d," +
                "\nspecialization: %s," +
                "\nbirthday: %s" +
                "\nsalary: %f" +
                "\n}",
                id, facultyId, fullName, phone,
                specialization, birthday != null ? birthday : "null", salary
        );
    }
}
