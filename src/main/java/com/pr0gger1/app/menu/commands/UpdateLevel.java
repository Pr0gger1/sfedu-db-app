package com.pr0gger1.app.menu.commands;

import com.pr0gger1.app.menu.Menu;
import com.pr0gger1.app.menu.commands.update.UpdateFaculty;

public class UpdateLevel extends Command {
    Command[] commands = {
        new UpdateFaculty(1, "Обновление факультета"),
        new BackCommand()
    };

    public UpdateLevel(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Menu menu = new Menu();
        Menu.levelUp();
        menu.showMenu(commands, "Обновление данных");
    }
}
