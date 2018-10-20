package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>NotEqualTo</code> class functions as intended.
 */
public class NotEqualToTest {

    /**
     * Verify that a version number is not equal.
     */
    @Test
    public void applyTest() throws Exception {
        NotEqualTo constraint = new NotEqualTo("1.0.0");

        assertFalse(constraint.apply("1.0.0"));
        assertTrue(constraint.apply("2.0.0"));
    }
}
