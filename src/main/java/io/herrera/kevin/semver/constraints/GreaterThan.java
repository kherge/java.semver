package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.InvalidVersionException;
import io.herrera.kevin.semver.Version;
import java.util.Objects;

/**
 * Requires a version number to be greater than another.
 */
public class GreaterThan implements Constraint {

    /**
     * The version number to be greater than.
     */
    private Version version;

    /**
     * Sets the version number to be greater than.
     *
     * @param version The version number to be greater than.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public GreaterThan(String version) throws InvalidVersionException {
        this(Version.parse(version));
    }

    /**
     * Sets the version number to be greater than.
     *
     * @param version The version number to be greater than.
     */
    public GreaterThan(Version version) {
        Objects.requireNonNull(version, "The version number to be greater than is required.");

        this.version = version;
    }

    @Override
    public boolean apply(Version version) {
        Objects.requireNonNull(version, "The version number to constrain is required.");

        return version.isGreaterThan(this.version);
    }
}
