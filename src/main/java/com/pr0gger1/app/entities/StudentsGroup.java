package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentsGroup extends Entity {
    private int facultyId = -1;
    private int groupId = -1;
    public String groupName;

    public StudentsGroup(int facultyId) {
        super(DataTables.GROUPS, new ArrayList<>(Arrays.asList("id", "group_name")));
        this.facultyId = facultyId;
    }

    public StudentsGroup(int groupId, int facultyId, String groupName) {
        super(DataTables.GROUPS, new ArrayList<>(Arrays.asList("id", "group_name")));

        this.groupId = groupId;
        this.facultyId = facultyId;
        this.groupName = groupName;
    }

    public StudentsGroup(int facultyId, String groupName) {
        super(DataTables.GROUPS, new ArrayList<>(Arrays.asList("id", "group_name")));
        this.facultyId = facultyId;
        this.groupName = groupName;
    }

    public StudentsGroup() {
        super(DataTables.GROUPS, new ArrayList<>(Arrays.asList("id", "group_name")));
    }


    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        if (this.facultyId == -1)
            this.facultyId = facultyId;
        else System.out.println("Невозможно изменить ID");
    }

    @Override
    public String toString() {
        return String.format(
            "StudentsGroup:{\n" +
            "\ngroupId: %d," +
            "\ngroupName: %s," +
            "\nfacultyId: %d," +
            "\n}",
            groupId, groupName, facultyId
        );
    }
}
