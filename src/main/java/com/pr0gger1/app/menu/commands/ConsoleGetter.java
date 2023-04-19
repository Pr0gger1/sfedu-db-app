package com.pr0gger1.app.menu.commands;

import com.pr0gger1.app.entities.*;

import java.util.Scanner;

public class ConsoleGetter extends Command {
    Scanner scanner = new Scanner(System.in);
    public ConsoleGetter(int id, String title) {
        super(id, title);
    }

    protected int getFacultyIdFromConsole(Faculty faculty) {
        System.out.println("Выберите факультет (выберите ID) или 0 для выхода");
        faculty.printEntityTable();
        int chosenFacultyId = scanner.nextInt();
        scanner.nextLine();

        return chosenFacultyId;
    }

    protected int getDirectionIdFromConsole(Direction direction) {
        System.out.println("Выберите направление подготовки (выберите ID)");
        direction.printEntityTable();

        int chosenDirectionId = scanner.nextInt();
        scanner.nextLine();

        return chosenDirectionId;
    }

    protected int getTeacherIdFromConsole(Teacher teacher) {
        System.out.println("Выберите преподавателя или 0 для выхода");
        int chosenTeacherId = scanner.nextInt();
        scanner.nextLine();

        return chosenTeacherId;
    }

    protected int getTeacherIdFromConsole(Teacher teacher, int facultyId) {
        System.out.println("Выберите главу направления подготовки среди преподавателей или 0 для выхода");
        teacher.setCurrentQuery(String.format("SELECT * FROM teachers WHERE faculty_id = %d", facultyId));
        teacher.printEntityTable();
        int chosenTeacherId = scanner.nextInt();
        scanner.nextLine();

        return chosenTeacherId;
    }

    protected void getDirectionNameFromConsole(Direction direction) {
        System.out.println("Введите название направления подготовки");
        direction.setDirectionName(scanner.next());
    }

    protected void getStudentGroupName(StudentsGroup studentsGroup) {
        System.out.println("Введите название/номер группы:");
        studentsGroup.setGroupName(scanner.next());
    }

    protected void setStudentDataFromConsole(Student student) {
        System.out.print("Введите ФИО студента:");
        student.setFullName(scanner.nextLine());

        System.out.print("Введите номер курса:");
        student.setCourse(scanner.nextShort());
        scanner.nextLine();

        student.setBirthdayFromConsole();

        System.out.print("Введите размер стипендии (0, если нет): ");
        student.setScholarship(scanner.nextFloat());
        scanner.nextLine();

        System.out.print("Введите номер телефона:");
        student.setPhone(scanner.nextLong());
        scanner.nextLine();
    }

    protected void setTeacherDataFromConsole(Teacher teacher) {
        System.out.println("Введите ФИО преподавателя: ");
        teacher.setFullName(scanner.nextLine());

        System.out.println("Введите зарплату: ");
        teacher.setSalary(scanner.nextFloat());
        scanner.nextLine();

        System.out.println("Введите специализацию: ");
        teacher.setSpecialization(scanner.nextLine());

        teacher.setBirthdayFromConsole();

        System.out.println("Введите номер телефона: ");
        teacher.setPhone(scanner.nextLong());
        scanner.nextLine();
    }

    protected void setFacultyDataFromConsole(Faculty faculty) {
        System.out.print("Введите адрес факультета: ");
        faculty.setAddress(scanner.nextLine());

        System.out.print("Введите адрес электронной почты: ");
        faculty.setEmail(scanner.nextLine());

        System.out.print("Введите номер телефона: (пример 7800444323)\n");
        faculty.setPhone(scanner.nextLong());
        scanner.nextLine();
    }
}
