package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Human;
import com.pr0gger1.database.DataTables;

import java.util.ArrayList;
import java.util.Arrays;

public class Teacher extends Human {
    private int facultyId;
    private long phone;
    private String fullName;
    private String specialization;
    private float salary;

    public Teacher(int facultyId) {
        super(
            DataTables.TEACHERS,
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

    public float getSalary() {
        return salary;
    }

    public long getPhone() {
        return phone;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setFacultyId(int id) {
        facultyId = id;
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
