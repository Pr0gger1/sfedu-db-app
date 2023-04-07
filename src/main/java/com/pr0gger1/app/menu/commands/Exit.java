package com.pr0gger1.app.menu.commands;

public class Exit extends Command {
    public Exit() {
        super(0, "Выход");
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
