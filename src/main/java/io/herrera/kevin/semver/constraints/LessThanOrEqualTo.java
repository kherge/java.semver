package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.InvalidVersionException;
import io.herrera.kevin.semver.Version;
import java.util.Objects;

/**
 * Requires a version number to be less than or equal to another.
 */
public class LessThanOrEqualTo implements Constraint {

    /**
     * The version number to be less than or equal to.
     */
    private Version version;

    /**
     * Sets the version number to be less than or equal to.
     *
     * @param version The version number to be less than or equal to.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public LessThanOrEqualTo(String version) throws InvalidVersionException {
        this(Version.of(version));
    }

    /**
     * Sets the version number to be less than or equal to.
     *
     * @param version The version number to be less than or equal to.
     */
    public LessThanOrEqualTo(Version version) {
        Objects.requireNonNull(version, "The version number to be less than or eqaul to is required.");

        this.version = version;
    }

    @Override
    public boolean apply(Version version) {
        Objects.requireNonNull(version, "The version number to constrain is required.");

        return this.version.equals(version) || version.isLessThan(this.version);
    }
}