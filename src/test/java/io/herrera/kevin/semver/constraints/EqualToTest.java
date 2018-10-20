package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>EqualTo</code> class functions as intended.
 */
public class EqualToTest {

    /**
     * Verify that a version is equal to another.
     */
    @Test
    public void applyTest() throws Exception {
        EqualTo constraint = new EqualTo("1.0.0");

        assertFalse(constraint.apply("2.0.0"));
        assertTrue(constraint.apply("1.0.0"));
    }
}
