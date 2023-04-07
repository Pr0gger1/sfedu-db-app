package com.pr0gger1.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
    @Test
    public void shouldConnectToDb() {
        Assertions.assertNotNull(Database.connect());
    }
}
