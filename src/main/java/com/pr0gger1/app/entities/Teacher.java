package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Human;
import com.pr0gger1.database.DataTables;

import java.util.ArrayList;
import java.util.Arrays;

public class Teacher extends Human {
    private int facultyId = -1;
    public long phone = -1;
    public String fullName;
    public String specialization;
    public float salary;

    public Teacher(int facultyId) {
        super(
            DataTables.TEACHERS,
            new ArrayList<>(Arrays.asList("id", "full_name")),
            String.format(
                "SELECT id, full_name FROM %s WHERE id NOT IN (SELECT head FROM %s)",
                    DataTables.TEACHERS, DataTables.DIRECTIONS
            )
        );
        this.facultyId = facultyId;
    }

    public Teacher() {
        super(DataTables.TEACHERS, new ArrayList<>(Arrays.asList("id", "full_name")));
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int id) {
        if (facultyId == -1) facultyId = id;
        else System.out.println("Невозможно изменить ID факультета");
    }


    @Override
    public String toString() {
        return String.format(
            "Teacher: {\n" +
            "\nfacultyId: %d," +
            "\nfull_name: %s," +
            "\nphone: %d," +
            "\nspecialization: %s," +
            "\nbirthday: %s" +
            "\nsalary: %f" +
            "\n}",
            facultyId, fullName, phone,
            specialization, birthday != null ? birthday : "null",
            salary
        );
    }
}
