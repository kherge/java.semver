package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>GreaterThanOrEqualTo</code> class functions as intended.
 */
public class GreaterThanOrEqualToTest {

    /**
     * Verify that a version is greater than or equal to.
     */
    @Test
    public void applyTest() throws Exception {
        GreaterThanOrEqualTo constraint = new GreaterThanOrEqualTo("1.0.0");

        assertFalse(constraint.apply("0.9.9"));
        assertTrue(constraint.apply("1.0.0"));
        assertTrue(constraint.apply("2.0.0"));
    }
}
