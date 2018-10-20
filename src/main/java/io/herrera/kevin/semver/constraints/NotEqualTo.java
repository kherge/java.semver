package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.InvalidVersionException;
import io.herrera.kevin.semver.Version;
import java.util.Objects;

/**
 * Requires a version to not be equal to another.
 */
public class NotEqualTo implements Constraint {

    /**
     * The version number to not equal to.
     */
    private Version version;

    /**
     * Sets the version number to not equal to.
     *
     * @param version The version number to not equal to.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public NotEqualTo(String version) throws InvalidVersionException {
        this(Version.of(version));
    }

    /**
     * Sets the version number to not equal to.
     *
     * @param version The version number to not equal to.
     */
    public NotEqualTo(Version version) {
        Objects.requireNonNull(version, "The version number to not equal to is required.");

        this.version = version;
    }

    @Override
    public boolean apply(Version version) {
        Objects.requireNonNull(version, "The version number to constrain is required.");

        return !this.version.equals(version);
    }
}
