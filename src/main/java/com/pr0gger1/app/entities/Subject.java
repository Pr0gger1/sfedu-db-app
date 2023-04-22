package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;

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
    }

    public Subject(int facultyId) {
        super(
            DataTables.SUBJECTS,
            "SELECT subj.id, subj.subject_name, f.faculty_name, t.full_name" +
                    " FROM subjects subj join faculties f on subj.faculty_id = f.id" +
                    " join teachers t on subj.teacher_id = t.id"
        );
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
    public String toString() {
        return "";
    }
}
