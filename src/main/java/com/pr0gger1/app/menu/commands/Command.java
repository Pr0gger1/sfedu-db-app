package com.pr0gger1.app.menu.commands;

public abstract class Command {
    private final String commandTitle;
    protected int commandId;

    protected Command(int id, String title) {
        this.commandTitle = title;
        this.commandId = id;
    }

    public void execute() {}
    public String getTitle() {
        return this.commandTitle;
    }
    public int getId() {
        return this.commandId;
    }
}
