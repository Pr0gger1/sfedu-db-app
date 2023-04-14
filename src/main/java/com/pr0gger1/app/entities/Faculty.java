package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;

import java.util.ArrayList;
import java.util.Arrays;

public class Faculty extends Entity {
    private int facultyId = -1;
    public String facultyName;
    public String address;
    public String email;
    public long phone;

    public Faculty(int facultyId) {
        super(DataTables.FACULTIES, new ArrayList<>(Arrays.asList("id", "faculty_name")));
        this.facultyId = facultyId;
    }

    public Faculty(int facultyId, String facultyName, String email, String address, long phone) {
        super(DataTables.FACULTIES, new ArrayList<>(Arrays.asList("id", "faculty_name")));

        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Faculty() {
        super(DataTables.FACULTIES, new ArrayList<>(Arrays.asList("id", "faculty_name")));
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        if (this.facultyId == -1)
            this.facultyId = facultyId;
        else System.out.println("Невозможно изменить ID");
    }

    public String toString() {
        return String.format(
            "{\n" +
            "\nfacultyId: %d," +
            "\nfacultyName: %s" +
            "\naddress: %s" +
            "\nphone: %d" +
            "\nemail: %s" +
            "\n}",
            facultyId, facultyName,
            address, phone, email
        );
    }
}
