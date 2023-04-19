package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;

import java.util.ArrayList;
import java.util.Arrays;

public class Faculty extends Entity {
    private int facultyId;
    private String facultyName;
    private String address;
    private String email;
    private long phone;

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

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
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

    public String toString() {
        return String.format(
            "{\n" +
            "\n\tfacultyId: %d," +
            "\n\tfacultyName: %s," +
            "\n\taddress: %s," +
            "\n\tphone: %d," +
            "\n\temail: %s," +
            "\n}",
            facultyId, facultyName,
            address, phone, email
        );
    }
}
