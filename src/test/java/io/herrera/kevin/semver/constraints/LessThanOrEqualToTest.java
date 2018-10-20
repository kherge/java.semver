package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>LessThanOrEqualTo</code> class functions as intended.
 */
public class LessThanOrEqualToTest {

    /**
     * Verify that a version is less than or equal to.
     */
    @Test
    public void applyTest() throws Exception {
        LessThanOrEqualTo constraint = new LessThanOrEqualTo("2.0.0");

        assertFalse(constraint.apply("3.0.0"));
        assertTrue(constraint.apply("2.0.0"));
        assertTrue(constraint.apply("1.0.0"));
    }
}
