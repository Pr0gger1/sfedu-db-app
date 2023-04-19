package com.pr0gger1.app.menu.commands;
import com.pr0gger1.app.menu.Menu;
import com.pr0gger1.app.menu.commands.write.*;

public class WriteLevel extends Command {
    private final Command[] secondLevel = {
        new SetFacultyStudent(1, "Добавить студента"),
        new SetFacultyTeacher(2, "Добавить преподавателя"),
        new SetFacultyGroup(3, "Добавить группу"),
        new SetDirection(4, "Добавить направление подготовки"),
        new SetFaculty(5, "Добавить факультет"),
        new SetSubject(6, "Добавить предмет"),
        new SetMark(7, "Добавить данные об экзаменах"),
        new BackCommand()
    };
    public WriteLevel(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Menu menu = new Menu();
        Menu.levelUp();
        menu.showMenu(secondLevel, "");
    }

}
