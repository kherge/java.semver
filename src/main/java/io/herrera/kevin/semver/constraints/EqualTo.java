package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.InvalidVersionException;
import io.herrera.kevin.semver.Version;
import java.util.Objects;

/**
 * Requires a version number to be equal to another.
 */
public class EqualTo implements Constraint {

    /**
     * The version number to be equal to.
     */
    private Version version;

    /**
     * Sets the version number to be equivalent to.
     *
     * @param version The version number to be equal to.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public EqualTo(String version) throws InvalidVersionException {
        this(new Version(version));
    }

    /**
     * Sets the version number to be equivalent to.
     *
     * @param version The version number to be equal to.
     */
    public EqualTo(Version version) {
        Objects.requireNonNull(version, "The version number to equal to is required.");

        this.version = version;
    }

    @Override
    public boolean apply(Version version) {
        Objects.requireNonNull(version, "The version number to constrain is required.");

        return this.version.equals(version);
    }
}
