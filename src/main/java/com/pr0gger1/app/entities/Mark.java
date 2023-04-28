package com.pr0gger1.app.entities;

import com.pr0gger1.app.entities.abstractEntities.Entity;
import com.pr0gger1.database.DataTables;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mark extends Entity {
    private final Scanner scanner = new Scanner(System.in);
    private int studentId;
    private int subjectId;
    private short mark;
    private short year;

    public Mark() {
        super(
            DataTables.MARKS,
            new ArrayList<>(List.of("*"))
        );
    }

    public Mark(int studentId) {
        super(
            DataTables.MARKS,
            String.format(
                "SELECT m.id, subj.subject_name, mark, year FROM marks m" +
                " WHERE student_id = %d" +
                " JOIN subjects subj on m.subject_id = subj.id",
                    studentId
            )
        );
    }

    public int getStudentId() {
        return studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public short getMark() {
        return mark;
    }

    public short getYear() {
        return year;
    }

    public void setMark(short mark) {
        if (mark >= 0 && mark <= 100)
            this.mark = mark;
        else if (mark < 0) throw new IllegalArgumentException("Балл не может быть меньше 0");
        else throw new IllegalArgumentException("Балл не может быть больше 100");
    }

    public void setMarkFromConsole() {
        System.out.print("Введите балл за экзамен: ");
        setMark(scanner.nextShort());
        scanner.nextLine();
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public void setYearFromConsole() {
        System.out.print("Введите год семестра: ");
        setYear(scanner.nextShort());
        scanner.nextLine();
    }

    @Override
    public String toString() {
        return "Mark{" +
            "studentId=" + studentId +
            ", subjectId=" + subjectId +
            ", mark=" + mark +
            ", year=" + year +
            '}';
    }
}
