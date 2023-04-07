package com.pr0gger1.app;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit-level testing for {@link SfeduDBApp} object.
 */
public class SfeduDBAppTest {

    @Test
    public void shouldCreateJavaRepositoryTemplateMain() {
        SfeduDBApp main = new SfeduDBApp();
        Assertions.assertNotNull(main);
    }

}
