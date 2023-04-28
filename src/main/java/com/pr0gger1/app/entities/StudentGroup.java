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

public class StudentGroup extends Entity {
    private final Scanner scanner = new Scanner(System.in);
    private int facultyId;
    private String groupName;

    public StudentGroup(int facultyId) {
        super(DataTables.GROUPS, new ArrayList<>(Arrays.asList("id", "group_name")));
        this.facultyId = facultyId;
        setLocalizedColumns(new ArrayList<>(Arrays.asList("Факультет", "Название группы")));
    }

    public StudentGroup(int facultyId, String groupName) {
        super(DataTables.GROUPS, new ArrayList<>(Arrays.asList("id", "group_name")));
        setLocalizedColumns(new ArrayList<>(Arrays.asList("Факультет", "Название группы")));
        this.facultyId = facultyId;
        this.groupName = groupName;
    }

    public StudentGroup() {
        super(DataTables.GROUPS, new ArrayList<>(Arrays.asList("id", "group_name")));
        setLocalizedColumns(new ArrayList<>(Arrays.asList("Факультет", "Название группы")));
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupNameFromConsole() {
        System.out.println("Введите название/номер группы:");
        setGroupName(scanner.nextLine());
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public void updateData() throws CancelInputException {
        Runnable updateGroupName = () -> {
            System.out.print("Введите название группы: ");
            groupName = scanner.nextLine();
        };

        ArrayList<Runnable> setters = new ArrayList<>(Arrays.asList(
                () -> {
                    Faculty faculty = new Faculty();
                    try {
                        faculty.setIdFromConsole();
                        facultyId = faculty.getId();
                        updateGroupName.run();
                    }
                    catch (CancelInputException e) {
                        System.out.println(e.getMessage());
                    }
                },
                updateGroupName
        ));

        super.updateData(setters);
    }

    @Override
    public void fillEntity() {
        try {
            if (id == 0) throw new InvalidIDException("Неверный ID");
            String query = String.format(
                "SELECT * FROM %s WHERE id = %d",
                DataTables.GROUPS, id
            );
            ResultSet data = Database.getData(query);
            while (data.next()) {
                if (facultyId == 0)
                    facultyId = data.getInt("faculty_id");
                if (groupName == null)
                    groupName = data.getString("group_name");
            }
        }
        catch (SQLException | InvalidIDException error) {
            error.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format(
            "StudentsGroup:{\n" +
            "\tgroupName: %s," +
            "\n\tfacultyId: %d" +
            "\n}",
            groupName, facultyId
        );
    }
}
