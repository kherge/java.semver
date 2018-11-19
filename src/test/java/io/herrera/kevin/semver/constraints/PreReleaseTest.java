package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>PreRelease</code> class functions as intended.
 */
public class PreReleaseTest {

    /**
     * Verify that the version is a pre-release version.
     */
    @Test
    public void applyTest() throws Exception {
        PreRelease constraint = new PreRelease();

        assertTrue(constraint.apply("1.0.0-beta"));
        assertFalse(constraint.apply("1.0.0"));
    }
}
