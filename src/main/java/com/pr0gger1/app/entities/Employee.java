package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Human;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.exceptions.CancelIOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Employee extends Human {
    private final Scanner scanner = new Scanner(System.in);

    private int facultyId;
    private long phone;
    private String fullName;
    private String specialization;
    private float salary;

    public Employee(int facultyId) {
        super(
            DataTables.EMPLOYEES,
            String.format(
                "SELECT id, full_name FROM %s" +
                " WHERE id NOT IN (SELECT head FROM %s)" +
                " AND faculty_id = (SELECT faculty_id FROM directions WHERE faculty_id = %d)",
                    DataTables.EMPLOYEES, DataTables.DIRECTIONS, facultyId
            )
        );
        this.facultyId = facultyId;
    }

    public Employee() {
        super(DataTables.EMPLOYEES, new ArrayList<>(Arrays.asList("id", "full_name")));
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

    public void setEmployeeDataFromConsole() {
        System.out.println("Введите ФИО преподавателя: ");
        setFullName(scanner.nextLine());

        System.out.println("Введите зарплату: ");
        setSalary(scanner.nextFloat());
        scanner.nextLine();

        System.out.println("Введите специализацию: ");
        setSpecialization(scanner.nextLine());

        setBirthdayFromConsole();

        System.out.println("Введите номер телефона: ");
        setPhone(scanner.nextLong());
        scanner.nextLine();
    }

    @Override
    public void setIdFromConsole() throws CancelIOException {
        setCurrentQuery(
            String.format(
                "SELECT id, full_name FROM %s " +
                "WHERE id NOT IN (SELECT s.employee_id FROM directions d JOIN subjects s ON d.head != s.employee_id) " +
                "AND faculty_id = (SELECT faculty_id FROM directions WHERE faculty_id = %d)",
                DataTables.EMPLOYEES.getTable(), facultyId
            )
        );
        super.setIdFromConsole();
    }

    @Override
    public String toString() {
        return String.format(
            "Employee: {\n" +
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
