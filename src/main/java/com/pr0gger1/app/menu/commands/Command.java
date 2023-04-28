package com.pr0gger1.app.menu.commands;

import java.util.Scanner;

public abstract class Command {
    private final String title;
    private final int id;

    protected Command(int id, String title) {
        this.title = title;
        this.id = id;
    }

    public void execute() {}
    public String getTitle() {
        return this.title;
    }
    public int getId() {
        return this.id;
    }

    protected boolean continueCommand() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Продолжить?");
            System.out.println("0. Нет");
            System.out.println("1. Да");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) return true;
            else if (choice == 0) return false;
        }
    }

}
