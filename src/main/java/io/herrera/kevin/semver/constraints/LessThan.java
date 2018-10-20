package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.InvalidVersionException;
import io.herrera.kevin.semver.Version;
import java.util.Objects;

/**
 * Requires a version number to be less than another.
 */
public class LessThan implements Constraint {

    /**
     * The version number to be less than.
     */
    private Version version;

    /**
     * Sets the version number to be less than.
     *
     * @param version The version number to be less than.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public LessThan(String version) throws InvalidVersionException {
        this(Version.parse(version));
    }

    /**
     * Sets the version number to be less than.
     *
     * @param version The version number to be less than.
     */
    public LessThan(Version version) {
        Objects.requireNonNull(version, "The version number to be less than is required.");

        this.version = version;
    }

    @Override
    public boolean apply(Version version) {
        Objects.requireNonNull(version, "The version number to constrain is required.");

        return version.isLessThan(this.version);
    }
}
