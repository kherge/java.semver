package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>Or</code> class functions as intended.
 */
public class OrTest {

    /**
     * Verify that at least one constraint is passed.
     */
    @Test
    public void applyTest() throws Exception {
        Or or = new Or().add(v -> v.getMajor() == 1).add(v -> v.getMajor() == 2);

        assertTrue(or.apply("1.0.0"));
        assertTrue(or.apply("2.0.0"));
        assertFalse(or.apply("3.0.0"));
    }
}
