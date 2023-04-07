package com.pr0gger1.app.menu.commands;
import com.pr0gger1.app.menu.Menu;
import com.pr0gger1.app.menu.commands.write.SetDirection;
import com.pr0gger1.app.menu.commands.write.SetFacultyGroup;
import com.pr0gger1.app.menu.commands.write.SetFacultyStudent;
import com.pr0gger1.app.menu.commands.write.SetFacultyTeacher;

public class WriteLevel extends Command {
    private final Command[] secondLevel = {
        new SetFacultyStudent(1, "Добавить студента"),
        new SetFacultyTeacher(2, "Добавить преподавателя"),
        new SetFacultyGroup(3, "Добавить группу"),
        new SetDirection(4, "Добавить направление подготовки"),
        new BackCommand()
    };
    public WriteLevel(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        Menu.levelUp();
        Menu.outMenu(secondLevel, "_".repeat(40));
    }
}
