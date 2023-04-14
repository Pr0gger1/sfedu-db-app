package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;

import java.util.ArrayList;
import java.util.Arrays;

public class Direction extends Entity {
    private int facultyId = -1;
    private int headOfDirectionId = -1;
    public String directionName;


    public Direction(int directionId, int facultyId) {
        super(DataTables.DIRECTIONS, new ArrayList<>(Arrays.asList("id", "direction_name")));
        this.facultyId = facultyId;
    }

    public Direction(int facultyId) {
        super(DataTables.DIRECTIONS, new ArrayList<>(Arrays.asList("id", "direction_name")));
        this.facultyId = facultyId;
    }

    public Direction() {
        super(DataTables.DIRECTIONS, new ArrayList<>(Arrays.asList("id", "direction_name")));
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        if (this.facultyId == -1)
            this.facultyId = facultyId;
        else System.out.println("Невозможно изменить ID");
    }

    public int getHeadOfDirectionId() {
        return headOfDirectionId;
    }

    public void setHeadOfDirectionId(int headOfDirectionId) {
        if (this.headOfDirectionId == -1)
            this.headOfDirectionId = headOfDirectionId;
        else System.out.println("Невозможно изменить ID");
    }

    @Override
    public String toString() {
        return "Direction: {}";
    }

}
