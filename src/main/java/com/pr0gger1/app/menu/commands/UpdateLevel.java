package com.pr0gger1.app.menu.commands;

import com.pr0gger1.app.menu.Menu;
import com.pr0gger1.app.menu.commands.update.*;

public class UpdateLevel extends Command {
    Command[] commands = {
        new UpdateStudent(1, "Обновление данных студента"),
        new UpdateFaculty(2, "Обновление факультета"),
        new UpdateDirection(3, "Обновление данных направления подготовки"),
        new UpdateEmployee(4, "Обновление данных сотрудника/преподавателя"),
        new UpdateStudentGroup(5, "Обновление студенческой группы"),
        new UpdateSubject(6, "Обновление данных предмета"),
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
