package com.pr0gger1.app.menu.commands;

public abstract class Command {
    private final String title;
    protected int id;

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

}
