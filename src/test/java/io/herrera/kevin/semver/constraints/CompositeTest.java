package io.herrera.kevin.semver.constraints;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.herrera.kevin.semver.Version;
import java.util.function.Supplier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that the <code>Composite</code> class functions as intended.
 */
public class CompositeTest {

    /**
     * Verifies that the composite constraint functions consistently and predictably.
     */
    @MethodSource("getCompositeCases")
    @ParameterizedTest
    public void applyTest(Composite composite, Constraint constraint, Version version, boolean expected) {
        assertSame(composite, composite.add(constraint));

        if (expected) {
            assertTrue(composite.apply(version));
        } else {
            assertFalse(composite.apply(version));
        }
    }

    /**
     * Generates test cases for the <code>Composite</code> constraint.
     */
    private static Object[][] getCompositeCases() {
        Supplier<Composite> ultimateFalse = () -> new Composite(false) {
            @Override
            protected Result constrain(Version version, Constraint constraint) {
                return constraint.apply(version) ? Result.PASS : Result.CONTINUE;
            }
        };

        Supplier<Composite> ultimateTrue = () -> new Composite(true) {
            @Override
            protected Result constrain(Version version, Constraint constraint) {
                return constraint.apply(version) ? Result.CONTINUE : Result.FAIL;
            }
        };

        Version version = mock(Version.class);

        when(version.toString()).thenReturn("1.2.3");

        Constraint v123 = v -> v.toString().equals("1.2.3");
        Constraint v456 = v -> v.toString().equals("4.5.6");

        return new Object[][] {
            { ultimateFalse.get(), v123, version, true },
            { ultimateFalse.get(), v456, version, false },
            { ultimateTrue.get(), v123, version, true },
            { ultimateTrue.get(), v456, version, false },
        };
    }
}
