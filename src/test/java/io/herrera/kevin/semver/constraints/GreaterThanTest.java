package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>GreaterThan</code> class functions as intended.
 */
public class GreaterThanTest {

    /**
     * Verify that a version is greater than another.
     */
    @Test
    public void applyTest() throws Exception {
        GreaterThan constraint = new GreaterThan("1.0.0");

        assertFalse(constraint.apply("1.0.0"));
        assertTrue(constraint.apply("2.0.0"));
    }
}
