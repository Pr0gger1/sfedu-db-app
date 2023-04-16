package com.pr0gger1.app;
import com.pr0gger1.app.menu.Menu;
import com.pr0gger1.database.Database;

public class SfeduDBApp {
    public static void main(String[] args) {
        Database.connect();
        Menu menu = new Menu();
        menu.open();
    }
}
