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

public class Direction extends Entity {
    Scanner scanner = new Scanner(System.in);

    private int facultyId;
    private int headOfDirectionId;
    private String directionName;


    public Direction(int facultyId) {
        super(
            DataTables.DIRECTIONS,
            String.format(
                "SELECT id, direction_name FROM %s WHERE faculty_id = %d",
                DataTables.DIRECTIONS.getTable(), facultyId
            )
        );
        this.facultyId = facultyId;

        setLocalizedColumns(new ArrayList<>(Arrays.asList(
                "Факультет", "Название направления", "Глава направления"
        )));
    }

    public Direction() {
        super(DataTables.DIRECTIONS, new ArrayList<>(Arrays.asList("id", "direction_name")));
        setLocalizedColumns(new ArrayList<>(Arrays.asList(
                "ID", "Факультет",
                "Название направления", "Глава направления"
        )));
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getDirectionName() {
        return directionName;
    }

    public int getHeadOfDirectionId() {
        return headOfDirectionId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public void setDirectionNameFromConsole() {
        System.out.println("Введите название направления подготовки");
        setDirectionName(scanner.nextLine());
    }

    public void setHeadOfDirectionId(int headOfDirectionId) {
        this.headOfDirectionId = headOfDirectionId;
    }

    @Override
    public void fillEntity() {
        try {
            if (id == 0) throw new InvalidIDException("Неверный ID");
            String query = String.format(
                "SELECT * FROM %s WHERE id = %d",
                DataTables.DIRECTIONS.getTable(), id
            );

            ResultSet data = Database.getData(query);

            while (data.next()) {
                if (id == 0)
                    id = data.getInt("id");
                if (facultyId == 0)
                    facultyId = data.getInt("faculty_id");
                if (directionName == null)
                    directionName = data.getString("direction_name");
                if (headOfDirectionId == 0)
                    headOfDirectionId = data.getInt("head");
            }
        }
        catch (SQLException | InvalidIDException error) {
            error.printStackTrace();
        }
    }
    public void updateData() throws CancelInputException {
        ArrayList<Runnable> setters = new ArrayList<>(Arrays.asList(
            () -> {
                Faculty newFaculty = new Faculty();
                Employee newHead = new Employee();
                try {
                    newFaculty.setCurrentQuery(
                        String.format(
                            "SELECT * FROM %s WHERE id != %d ORDER BY id",
                            DataTables.FACULTIES.getTable(), facultyId
                        )
                    );
                    newFaculty.setIdFromConsole();
                    facultyId = newFaculty.getId();

                    newHead.setCurrentQuery(
                        String.format(
                            "SELECT id, full_name FROM %s WHERE faculty_id = %d",
                            DataTables.EMPLOYEES.getTable(), facultyId
                        )
                    );

                    newHead.setIdFromConsole();
                    headOfDirectionId = newHead.getId();
                }
                catch (CancelInputException error) {
                    System.out.println(error.getMessage());
                }
            },
            () -> {
                System.out.print("Введите название направления подготовки: ");
                directionName = scanner.nextLine();
            },
            () -> {
                Employee newEmployee = new Employee();
                try {
                    newEmployee.setIdFromConsole();
                    headOfDirectionId = newEmployee.getId();
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
        return String.format(
            "Direction: {\n" +
            "\n\tfaculty_id: %d," +
            "\n\tdirection_name: %s," +
            "\n\thead_id: %d," +
            "\n}",
            facultyId, directionName,
            headOfDirectionId
        );
    }

}
