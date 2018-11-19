package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>Stable</code> class functions as intended.
 */
public class StableTest {

    /**
     * Verify that the version is stable.
     */
    @Test
    public void applyTest() throws Exception {
        Stable constraint = new Stable();

        assertTrue(constraint.apply("1.0.0"));
        assertFalse(constraint.apply("1.0.0-beta"));
    }
}
