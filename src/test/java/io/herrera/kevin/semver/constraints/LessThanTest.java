package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>LessThan</code> class functions as intended.
 */
public class LessThanTest {

    /**
     * Verify that one version is less than another.
     */
    @Test
    public void applyTest() throws Exception {
        LessThan constraint = new LessThan("2.0.0");

        assertFalse(constraint.apply("2.0.0"));
        assertTrue(constraint.apply("1.0.0"));
    }
}
