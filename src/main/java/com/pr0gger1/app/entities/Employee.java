package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Human;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;

import java.sql.ResultSet;
import java.sql.SQLException;
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

        setLocalizedColumns(new ArrayList<>(Arrays.asList(
            "ФИО сотрудника", "Зарплата", "Специальность",
            "Факультет", "Дата рождения", "Номер телефона"
        )));
    }

    public Employee() {
        super(DataTables.EMPLOYEES, new ArrayList<>(Arrays.asList("id", "full_name")));
        setLocalizedColumns(new ArrayList<>(Arrays.asList(
            "ФИО сотрудника", "Зарплата", "Специальность",
            "Факультет", "Дата рождения", "Номер телефона"
        )));
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

    public void updateData() throws CancelInputException {
        ArrayList<Runnable> setters = new ArrayList<>(
            Arrays.asList(
                () -> {
                    System.out.print("Введите ФИО: ");
                    fullName = scanner.nextLine();
                },
                () -> {
                    System.out.print("Введите зарплату: ");
                    salary = scanner.nextFloat();
                },
                () -> {
                    System.out.print("Введите специальность: ");
                    specialization = scanner.nextLine();
                },
                () -> {
                    Faculty newFaculty = new Faculty();
                    try {
                        newFaculty.setIdFromConsole();
                        facultyId = newFaculty.getId();
                    }
                    catch (CancelInputException e) {
                        System.out.println(e.getMessage());
                    }
                },
                this::setBirthdayFromConsole,
                () -> {
                    System.out.print("Введите номер телефона: ");
                    phone = scanner.nextLong();
                }
            )
        );
        super.updateData(setters);
    }

    @Override
    public void fillEntity() {
        String query = String.format(
            "SELECT * FROM %s WHERE id = %d",
            DataTables.EMPLOYEES.getTable(), id
        );

        try {
            ResultSet data = Database.getData(query);
            while (data.next()) {
                if (fullName == null)
                    fullName = data.getString("full_name");
                if (salary == 0)
                    salary = data.getFloat("salary");
                if (specialization == null)
                    specialization = data.getString("specialization");
                if (facultyId == 0)
                    facultyId = data.getInt("faculty_id");
                if (birthday == null)
                    birthday = data.getDate("birthday").toLocalDate();
                if (phone == 0)
                    phone = data.getLong("phone");
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
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
