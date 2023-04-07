package com.pr0gger1.app.menu.commands.read;

import com.pr0gger1.app.menu.commands.Command;

public class GetFacultyStudents extends Command {

    public GetFacultyStudents(int id, String title) {
        super(id, title);
    }

    @Override
    public void execute() {
        System.out.println("Get faculty students");
    }
}
