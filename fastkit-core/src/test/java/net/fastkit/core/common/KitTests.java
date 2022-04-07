package net.fastkit.core.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sunnymix
 */
public class KitTests {

    @Test
    void loadResource() {
        assertEquals("resource", Kit.Resources.load("data/resource.txt"));
    }

}
