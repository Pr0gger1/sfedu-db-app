package com.pr0gger1.app.menu.commands;

import com.pr0gger1.app.menu.Menu;
import com.pr0gger1.app.menu.commands.delete.*;

public class DeleteLevel extends Command {
    private final Command[] secondLevel = {
        new DeleteStudent(1, "Удалить студента"),
        new DeleteEmployee(2, "Удалить преподавателя"),
        new DeleteFaculty(3, "Удалить факультет"),
        new DeleteFacultyGroup(4, "Удалить группу"),
        new DeleteDirection(5, "Удалить направление подготовки"),
        new BackCommand()
    };
    public DeleteLevel(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Menu menu = new Menu();
        Menu.levelUp();
        menu.showMenu(secondLevel, "Удаление данных");
    }
}
