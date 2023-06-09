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
import java.util.List;
import java.util.Scanner;

public class Mark extends Entity {
    private final Scanner scanner = new Scanner(System.in);
    private int studentId;
    private int subjectId;
    private short mark = -1;
    private short year;

    public Mark() {
        super(
            DataTables.MARKS,
            new ArrayList<>(List.of("*"))
        );
        setLocalizedColumns(new ArrayList<>(Arrays.asList("Балл", "Год семестра")));
    }

    public Mark(int studentId) {
        super(
            DataTables.MARKS,
            String.format(
                "SELECT m.id, subj.subject_name, mark, year FROM %s m " +
                "JOIN %s subj on m.subject_id = subj.id " +
                "WHERE student_id = %d",
                DataTables.MARKS.getTable(), DataTables.SUBJECTS.getTable(),
                studentId
            )
        );
        setLocalizedColumns(new ArrayList<>(Arrays.asList("Балл", "Год семестра")));
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
    public void fillEntity() {
        try {
            if (id == 0) throw new InvalidIDException("Неверный ID");
            String query = String.format(
                "SELECT * FROM %s WHERE id = %d",
                DataTables.MARKS.getTable(), id
            );

            ResultSet data = Database.getData(query);

            while (data.next()) {
                if (studentId == 0)
                    studentId = data.getInt("student_id");
                if (subjectId == 0)
                    subjectId = data.getInt("subject_id");
                if (mark == -1)
                    mark = data.getShort("mark");
                if (year == 0)
                    year = data.getShort("year");
            }

        }
        catch (SQLException |InvalidIDException error) {
            error.printStackTrace();
        }
    }

    public void updateData() throws CancelInputException {
        ArrayList<Runnable> setters = new ArrayList<>(Arrays.asList(
            () -> {
                System.out.print("Введите балл: ");
                mark = scanner.nextShort();
                scanner.nextLine();
            },
            () -> {
                System.out.print("Введите год семестра: ");
                year = scanner.nextShort();
                scanner.nextLine();
            }
        ));

        super.updateData(setters);
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
