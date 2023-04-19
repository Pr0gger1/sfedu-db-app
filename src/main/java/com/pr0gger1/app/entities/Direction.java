package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;

import java.util.ArrayList;
import java.util.Arrays;

public class Direction extends Entity {
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
    }

    public Direction() {
        super(DataTables.DIRECTIONS, new ArrayList<>(Arrays.asList("id", "direction_name")));
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

    public void setHeadOfDirectionId(int headOfDirectionId) {
        this.headOfDirectionId = headOfDirectionId;
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
