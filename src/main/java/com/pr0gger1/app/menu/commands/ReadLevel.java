package com.pr0gger1.app.menu.commands;

import com.pr0gger1.app.menu.Menu;
import com.pr0gger1.app.menu.commands.read.*;

public class ReadLevel extends Command {
    private final Command[] secondLevel = {
        new GetFacultyStudents(1, "Список студентов факультета"),
        new GetFacultyTeachers(2, "Список преподавателей факультета"),
        new GetFacultyGroups(3,"Список групп факультета"),
        new GetAllDirections(4,"Список всех направлений обучения"),
        new BackCommand()
    };

    public ReadLevel(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Menu.levelUp();
        Menu.outMenu(secondLevel, "_".repeat(40));
    }
}

