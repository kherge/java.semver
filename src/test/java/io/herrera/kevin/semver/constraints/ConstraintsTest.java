package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.herrera.kevin.semver.constraints.Constraints.*;

import io.herrera.kevin.semver.Version;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the <code>Constraints</code> class functions as intended.
 */
public class ConstraintsTest {

    /**
     * Verifies that the static aliases work with string representations.
     */
    @Test
    public void aliasStringTest() throws Exception {
        Constraint constraint = or(
            and(gte("1.0.0"), lt("2.0.0"), ne("1.2.3")),
            and(gt("2.0.0"), lte("2.1.0")),
            eq("9.9.9")
        );

        assertFalse(constraint.apply("1.2.3"));
        assertTrue(constraint.apply("1.5.0"));
        assertTrue(constraint.apply("2.0.10"));
        assertTrue(constraint.apply("9.9.9"));
    }

    /**
     * Verifies that the static aliases work with <code>Version</code> instances.
     */
    @Test
    public void aliasTest() throws Exception {
        Constraint constraint = or(
            and(gte(Version.of("1.0.0")), lt(Version.of("2.0.0")), ne(Version.of("1.2.3"))),
            and(gt(Version.of("2.0.0")), lte(Version.of("2.1.0"))),
            eq(Version.of("9.9.9"))
        );

        assertFalse(constraint.apply(Version.of("1.2.3")));
        assertTrue(constraint.apply(Version.of("1.5.0")));
        assertTrue(constraint.apply(Version.of("2.0.10")));
        assertTrue(constraint.apply(Version.of("9.9.9")));
    }
}
