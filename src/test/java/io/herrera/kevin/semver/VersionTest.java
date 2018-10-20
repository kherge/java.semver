package io.herrera.kevin.semver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that the <code>Version</code> class functions as intended.
 */
public class VersionTest {

    /**
     * The version under test.
     */
    private static Version version;

    /**
     * Verify that the build metadata is cleared in a new instance.
     */
    @Test
    public void clearBuildTest() {
        Version changed = version.clearBuild();

        assertEquals(version.getMajor(), changed.getMajor());
        assertEquals(version.getMinor(), changed.getMinor());
        assertEquals(version.getPatch(), changed.getPatch());
        assertEquals(version.getPreRelease(), changed.getPreRelease());
    }

    /**
     * Verify that the pre-release metadata is cleared in a new instance.
     */
    @Test
    public void clearPreReleaseTest() {
        Version changed = version.clearPreRelease();

        assertEquals(version.getMajor(), changed.getMajor());
        assertEquals(version.getMinor(), changed.getMinor());
        assertEquals(version.getPatch(), changed.getPatch());
        assertEquals(version.getBuild(), changed.getBuild());
    }

    /**
     * Verifies that the default instance can be used as a starting point.
     */
    @Test
    public void defaultTest() {
        Version built = Version
            .DEFAULT
            .incrementMajor()
            .incrementMinor()
            .incrementPatch()
            .setPreRelease("beta")
            .setBuild("abc");

        assertEquals(1, built.getMajor());
        assertEquals(1, built.getMinor());
        assertEquals(1, built.getPatch());
        assertEquals(Collections.singletonList("beta"), built.getPreRelease());
        assertEquals(Collections.singletonList("abc"), built.getBuild());
    }

    /**
     * Tests the remaining conditions in <code>equals()</code>.
     */
    @Test
    public void equalsNotTest() {
        assertFalse(version.equals(null));
        assertFalse(version.equals(this));

        Version another = new Version(0, 0, 0);

        assertFalse(version.equals(another));
    }

    /**
     * Verify that two versions have equal precedence.
     */
    @MethodSource("getEqualVersions")
    @ParameterizedTest(name = "equalsTest() [{index}] {arguments}")
    public void equalsTest(Version left, Version right) {
        assertEquals(left, right);
    }

    /**
     * Verify that hash codes are generated properly.
     */
    @Test
    public void hashCodeTest() {
        Version version = new Version(1, 2, 3, Arrays.asList("alpha", "4"), Arrays.asList("xyz", "5"));
        Version another = new Version(
            version.getMajor(),
            version.getMinor(),
            version.getPatch(),
            version.getPreRelease(),
            version.getBuild()
        );

        assertEquals(version.hashCode(), another.hashCode());
    }

    /**
     * Verify that the major version number is incremented.
     */
    @Test
    public void incrementMajorTest() {
        Version changed = version.incrementMajor();

        assertEquals(version.getMajor() + 1, changed.getMajor());
        assertEquals(version.getMinor(), changed.getMinor());
        assertEquals(version.getPatch(), changed.getPatch());
        assertEquals(version.getPreRelease(), changed.getPreRelease());
        assertEquals(version.getBuild(), changed.getBuild());
    }

    /**
     * Verify that the minor version number is incremented.
     */
    @Test
    public void incrementMinorTest() {
        Version changed = version.incrementMinor();

        assertEquals(version.getMajor(), changed.getMajor());
        assertEquals(version.getMinor() + 1, changed.getMinor());
        assertEquals(version.getPatch(), changed.getPatch());
        assertEquals(version.getPreRelease(), changed.getPreRelease());
        assertEquals(version.getBuild(), changed.getBuild());
    }

    /**
     * Verify that the patch version number is incremented.
     */
    @Test
    public void incrementPatchTest() {
        Version changed = version.incrementPatch();

        assertEquals(version.getMajor(), changed.getMajor());
        assertEquals(version.getMinor(), changed.getMinor());
        assertEquals(version.getPatch() + 1, changed.getPatch());
        assertEquals(version.getPreRelease(), changed.getPreRelease());
        assertEquals(version.getBuild(), changed.getBuild());
    }

    /**
     * Verify that one version has greater precedence.
     */
    @MethodSource("getGreaterVersions")
    @ParameterizedTest(name = "isGreaterThanTest() [{index}] {arguments}")
    public void isGreaterThanTest(Version left, Version right) {
        assertFalse(right.isGreaterThan(left));
        assertTrue(left.isGreaterThan(right));
    }

    /**
     * Verify that one version has lesser precedence.
     */
    @MethodSource("getLesserVersions")
    @ParameterizedTest(name = "isLessThanTest() [{index}] {arguments}")
    public void isLessThanTest(Version left, Version right) {
        assertFalse(right.isLessThan(left));
        assertTrue(left.isLessThan(right));
    }

    /**
     * Verify that a version number is checked for stability.
     */
    @Test
    public void isStableTest() {
        Version unstableA = new Version(0, 0, 1);

        assertFalse(unstableA.isStable());

        Version unstableB = new Version(1, 0, 0, Collections.singletonList("alpha"));

        assertFalse(unstableB.isStable());

        Version stable = new Version(1, 0, 0);

        assertTrue(stable.isStable());
    }

    /**
     * Verify that an exception is thrown if an invalid semantic version number is parsed.
     */
    @Test
    public void ofExceptionTest() {
        assertThrows(InvalidVersionException.class, () -> Version.parse("x.y.z"));
    }

    /**
     * Verify that the build metadata is set.
     */
    @Test
    public void setBuildTest() {
        String[] metadata = {"a", "b", "c"};

        Version changed = version.setBuild(metadata);

        assertEquals(version.getMajor(), changed.getMajor());
        assertEquals(version.getMinor(), changed.getMinor());
        assertEquals(version.getPatch(), changed.getPatch());
        assertEquals(version.getPreRelease(), changed.getPreRelease());
        assertEquals(Arrays.asList(metadata), changed.getBuild());
    }

    /**
     * Verify that the pre-release metadata is set.
     */
    @Test
    public void setPreReleaseTest() {
        String[] metadata = {"a", "b", "c"};

        Version changed = version.setPreRelease(metadata);

        assertEquals(version.getMajor(), changed.getMajor());
        assertEquals(version.getMinor(), changed.getMinor());
        assertEquals(version.getPatch(), changed.getPatch());
        assertEquals(Arrays.asList(metadata), changed.getPreRelease());
        assertEquals(version.getBuild(), changed.getBuild());
    }

    /**
     * Generates version instances for <code>equalsTest()</code> arguments.
     *
     * @return The arguments.
     */
    private static Object[][] getEqualVersions() throws Exception {
        return new Object[][] {

            { Version.parse("0.0.0"), Version.parse("0.0.0") },
            { Version.parse("0.0.1"), Version.parse("0.0.1") },
            { Version.parse("0.1.0"), Version.parse("0.1.0") },
            { Version.parse("1.0.0"), Version.parse("1.0.0") },
            { Version.parse("1.1.1"), Version.parse("1.1.1") },

            { Version.parse("0.0.0-0"), Version.parse("0.0.0-0") },
            { Version.parse("0.0.0+0"), Version.parse("0.0.0+1") },
        };
    }

    /**
     * Generates version instances for <code>isGreaterThanTest()</code> arguments.
     *
     * @return The arguments.
     */
    private static Object[][] getGreaterVersions() throws Exception {
        return new Object[][] {

            { Version.parse("0.0.2"), Version.parse("0.0.1") },
            { Version.parse("0.2.0"), Version.parse("0.1.0") },
            { Version.parse("2.0.0"), Version.parse("1.0.0") },

            { Version.parse("0.0.0"), Version.parse("0.0.0-0") },
            { Version.parse("0.0.0-2"), Version.parse("0.0.0-1") },
            { Version.parse("0.0.0-a"), Version.parse("0.0.0-3") },
            { Version.parse("0.0.0-b"), Version.parse("0.0.0-a") },

            { Version.parse("0.0.0-a.b.c"), Version.parse("0.0.0-a.1") },
            { Version.parse("0.0.0-1.2.b"), Version.parse("0.0.0-1.2") },

            { Version.parse("0.0.0-rc"), Version.parse("0.0.0-beta") },
            { Version.parse("0.0.0-beta"), Version.parse("0.0.0-alpha") },

            { Version.parse("1.0.0"), Version.parse("1.0.0-rc.1") },
            { Version.parse("1.0.0-rc.1"), Version.parse("1.0.0-beta.11") },
            { Version.parse("1.0.0-beta.11"), Version.parse("1.0.0-beta.2") },
            { Version.parse("1.0.0-beta.2"), Version.parse("1.0.0-alpha.beta") },
            { Version.parse("1.0.0-alpha.beta"), Version.parse("1.0.0-alpha.1") },
        };
    }

    /**
     * Generates version instances for <code>isLessThanTest()</code> arguments.
     *
     * @return The arguments.
     */
    private static Object[][] getLesserVersions() throws Exception {
        return new Object[][] {

            { Version.parse("0.0.1"), Version.parse("0.0.2") },
            { Version.parse("0.1.0"), Version.parse("0.2.0") },
            { Version.parse("1.0.0"), Version.parse("2.0.0") },

            { Version.parse("0.0.0-0"), Version.parse("0.0.0") },
            { Version.parse("0.0.0-1"), Version.parse("0.0.0-2") },
            { Version.parse("0.0.0-3"), Version.parse("0.0.0-a") },
            { Version.parse("0.0.0-a"), Version.parse("0.0.0-b") },

            { Version.parse("0.0.0-a.1"), Version.parse("0.0.0-a.b.c") },
            { Version.parse("0.0.0-1.2"), Version.parse("0.0.0-1.2.b") },

            { Version.parse("0.0.0-beta"), Version.parse("0.0.0-rc") },
            { Version.parse("0.0.0-alpha"), Version.parse("0.0.0-beta") },

            { Version.parse("1.0.0-rc.1"), Version.parse("1.0.0") },
            { Version.parse("1.0.0-beta.11"), Version.parse("1.0.0-rc.1") },
            { Version.parse("1.0.0-beta.2"), Version.parse("1.0.0-beta.11") },
            { Version.parse("1.0.0-alpha.beta"), Version.parse("1.0.0-beta.2") },
            { Version.parse("1.0.0-alpha.1"), Version.parse("1.0.0-alpha.beta") },
        };
    }

    @BeforeAll
    private static void setUp() {
        version = new Version(1, 2, 3, Arrays.asList("alpha", "123"), Arrays.asList("xyz", "456"));
    }
}
