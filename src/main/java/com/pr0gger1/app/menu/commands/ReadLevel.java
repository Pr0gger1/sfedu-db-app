package com.pr0gger1.app.menu.commands;

import com.pr0gger1.app.menu.Menu;
import com.pr0gger1.app.menu.commands.read.*;

public class ReadLevel extends Command {
    private final Command[] secondLevel = {
        new GetFaculties(1, "Список факультетов"),
        new GetFacultyStudents(2, "Список студентов факультета"),
        new GetFacultyTeachers(3, "Список преподавателей факультета"),
        new GetFacultyGroups(4,"Список групп факультета"),
        new GetAllDirections(5,"Список всех направлений обучения"),
        new GetSubjects(6, "Список всех предметов"),
        new BackCommand()
    };

    public ReadLevel(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Menu menu = new Menu();
        Menu.levelUp();
        menu.showMenu(secondLevel, "Чтение данных");
    }
}

