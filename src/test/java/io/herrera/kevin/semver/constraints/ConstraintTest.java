package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import io.herrera.kevin.semver.Version;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the default methods for the <code>Constraint</code> interface function as intended.
 */
public class ConstraintTest {

    /**
     * Verify that a string representation can be used.
     */
    @Test
    public void applyStringTest() throws Exception {
        Constraint constraint = spy(Constraint.class);

        when(constraint.apply(any(Version.class))).thenReturn(true);

        assertTrue(constraint.apply("1.2.3"));
    }
}
