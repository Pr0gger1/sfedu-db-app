package com.pr0gger1.app.menu.commands;

import com.pr0gger1.app.menu.Menu;
import com.pr0gger1.app.menu.commands.grade.GetMarks;

public class GradeLevel extends Command {
    Command[] commands = {
        new GetMarks(1, "Успеваемость студента за семестры"),
        new BackCommand()
    };
    public GradeLevel(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Menu menu = new Menu();
        Menu.levelUp();
        menu.showMenu(commands, "Успеваемость");
    }
}
