package io.herrera.kevin.semver.constraints;

import static io.herrera.kevin.semver.constraints.Constraints.and;
import static io.herrera.kevin.semver.constraints.Constraints.eq;
import static io.herrera.kevin.semver.constraints.Constraints.gt;
import static io.herrera.kevin.semver.constraints.Constraints.gte;
import static io.herrera.kevin.semver.constraints.Constraints.lt;
import static io.herrera.kevin.semver.constraints.Constraints.lte;
import static io.herrera.kevin.semver.constraints.Constraints.ne;
import static io.herrera.kevin.semver.constraints.Constraints.or;
import static io.herrera.kevin.semver.constraints.Constraints.pre;
import static io.herrera.kevin.semver.constraints.Constraints.stable;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
            and(gte("7.0.0"), lt("8.0.0"), stable()),
            and(gte("8.0.0"), lt("9.0.0"), pre()),
            eq("9.9.9")
        );

        assertFalse(constraint.apply("1.2.3"));
        assertTrue(constraint.apply("1.5.0"));
        assertTrue(constraint.apply("2.0.10"));
        assertTrue(constraint.apply("7.1.0"));
        assertTrue(constraint.apply("8.1.0-beta"));
        assertTrue(constraint.apply("9.9.9"));
    }

    /**
     * Verifies that the static aliases work with <code>Version</code> instances.
     */
    @Test
    public void aliasTest() throws Exception {
        Constraint constraint = or(
            and(gte(new Version("1.0.0")), lt(new Version("2.0.0")), ne(new Version("1.2.3"))),
            and(gt(new Version("2.0.0")), lte(new Version("2.1.0"))),
            and(gte(new Version("7.0.0")), lt(new Version("8.0.0")), stable()),
            and(gte(new Version("8.0.0")), lt(new Version("9.0.0")), pre()),
            eq(new Version("9.9.9"))
        );

        assertFalse(constraint.apply(new Version("1.2.3")));
        assertTrue(constraint.apply(new Version("1.5.0")));
        assertTrue(constraint.apply(new Version("2.0.10")));
        assertTrue(constraint.apply("7.1.0"));
        assertTrue(constraint.apply("8.1.0-beta"));
        assertTrue(constraint.apply(new Version("9.9.9")));
    }
}
