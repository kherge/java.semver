package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>And</code> class functions as intended.
 */
public class AndTest {

    /**
     * Verify that all constraints are passed.
     */
    @Test
    public void applyTest() throws Exception {
        And and = new And().add(v -> v.getMajor() > 0).add(v -> v.getMajor() < 2);

        assertTrue(and.apply("1.0.0"));
        assertFalse(and.apply("2.0.0"));
    }
}
