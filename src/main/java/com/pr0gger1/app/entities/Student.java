package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Student extends Entity {
    private int facultyId = -1;
    private int directionId = -1;
    public short course;
    public float scholarship;
    public int phone;
    public String birthday;
    public String fullName;

    public Student() {
        super(DataTables.STUDENTS, new ArrayList<>(Arrays.asList("id", "full_name")));
    }

    public Student(int directionId, int facultyId) {
        super(DataTables.STUDENTS, new ArrayList<>(Arrays.asList("id", "full_name")));
        this.directionId = directionId;
        this.facultyId = facultyId;
    }

    public int getDirectionId() {
        return directionId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
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
}
