package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentsGroup extends Entity {
    private int facultyId;
    private String groupName;

    public StudentsGroup(int facultyId) {
        super(DataTables.GROUPS, new ArrayList<>(Arrays.asList("id", "group_name")));
        this.facultyId = facultyId;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
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
