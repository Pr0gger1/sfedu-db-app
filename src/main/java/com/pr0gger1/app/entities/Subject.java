package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;
import com.pr0gger1.database.Database;
import com.pr0gger1.exceptions.CancelInputException;
import com.pr0gger1.exceptions.InvalidIDException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Subject extends Entity {
    private final Scanner scanner = new Scanner(System.in);

    private int facultyId;
    private int directionId;
    private int teacherId;
    private String subjectName;

    public Subject() {
        super(
            DataTables.SUBJECTS, new ArrayList<>(
                Arrays.asList("id", "subject_name", "faculty_id")
            )
        );
        setLocalizedColumns(new ArrayList<>(Arrays.asList(
                "Название предмета", "Факультет",
                "Направление подготовки", "Преподаватель"
        )));
    }

    public Subject(int facultyId) {
        super(
            DataTables.SUBJECTS,
            String.format(
                "SELECT subj.id, subj.subject_name, f.faculty_name, e.full_name" +
                " FROM %s subj join %s f on subj.faculty_id = f.id" +
                " join %s e on subj.employee_id = e.id",
                DataTables.SUBJECTS.getTable(),
                DataTables.FACULTIES.getTable(),
                DataTables.EMPLOYEES.getTable()
            )
        );
        setLocalizedColumns(new ArrayList<>(Arrays.asList(
                "Название предмета", "Факультет",
                "Направление подготовки", "Преподаватель"
        )));
        this.facultyId = facultyId;
    }
    public int getFacultyId() {
        return facultyId;
    }

    public int getDirectionId() {
        return directionId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }


    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectNameFromConsole() {
        System.out.print("Введите название предмета: ");
        setSubjectName(scanner.nextLine());
    }

    @Override
    public void fillEntity() {
        try {
            if (id == 0) throw new InvalidIDException("Неверный ID");
            String query = String.format(
                "SELECT * FROM %s WHERE id = %d",
                DataTables.SUBJECTS.getTable(), id
            );
            ResultSet data = Database.getData(query);

            while (data.next()) {
                if (subjectName == null)
                    subjectName = data.getString("subject_name");
                if (facultyId == 0)
                    facultyId = data.getInt("faculty_id");
                if (directionId == 0)
                    directionId = data.getInt("direction_id");
                if (teacherId == 0)
                    teacherId = data.getInt("employee_id");
            }
        }
        catch (SQLException | InvalidIDException error) {
            error.printStackTrace();
        }
    }

    public void updateData() throws CancelInputException {
        ArrayList<Runnable> setters = new ArrayList<>(Arrays.asList(
            () -> {
                System.out.print("Введите название предмета: ");
                subjectName = scanner.nextLine();
            },
            () -> {
                Faculty faculty = new Faculty();
                Direction direction = new Direction();
                Employee employee = new Employee();

                try {
                    faculty.setIdFromConsole();
                    facultyId = faculty.getId();

                    direction.setIdFromConsole();
                    directionId = direction.getId();

                    employee.setCurrentQuery(String.format(
                        "SELECT * FROM %s WHERE faculty_id = %d",
                        DataTables.EMPLOYEES.getTable(), facultyId
                    ));
                    employee.setIdFromConsole();
                    teacherId = employee.getId();
                }
                catch (CancelInputException e) {
                    System.out.println(e.getMessage());
                }
            },
            () -> {
                Direction direction = new Direction();
                if (facultyId != 0)
                    direction.setCurrentQuery(String.format(
                        "SELECT * FROM %s WHERE faculty_id = %d",
                        DataTables.DIRECTIONS.getTable(), facultyId
                    ));

                try {
                    direction.setIdFromConsole();
                    directionId = direction.getId();
                }
                catch (CancelInputException e) {
                    System.out.println(e.getMessage());
                }
            },
            () -> {
                Employee employee = new Employee();
                try {
                    employee.setIdFromConsole();
                    teacherId = employee.getId();
                }
                catch (CancelInputException e) {
                    System.out.println(e.getMessage());
                }
            }
        ));

        super.updateData(setters);
    }

    @Override
    public String toString() {
        return String.format("Subject:{\n" +
            "\tid: %d," +
            "\n\tsubject_name: %s," +
            "\n\tfaculty_id: %d," +
            "\n\tdirection_id: %d," +
            "\n\tteacher_id: %d\n}",
            id, subjectName, facultyId,
            directionId, teacherId
        );
    }
}
