package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.InvalidVersionException;
import io.herrera.kevin.semver.Version;

/**
 * Provides static aliases to version constraints.
 */
public final class Constraints {

    /**
     * Requires that a version match all constraints in a set.
     *
     * @param constraint The constraint to add.
     *
     * @return The version constraint.
     */
    public static And and(Constraint... constraint) {
        return new And().add(constraint);
    }

    /**
     * Requires a version number to be equal to another.
     *
     * @param version The version number to be equal to.
     *
     * @return The version constraint.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public static EqualTo eq(String version) throws InvalidVersionException {
        return new EqualTo(version);
    }

    /**
     * Requires a version number to be equal to another.
     *
     * @param version The version number to be equal to.
     *
     * @return The version constraint.
     */
    public static EqualTo eq(Version version) {
        return new EqualTo(version);
    }

    /**
     * Requires a version number to be greater than another.
     *
     * @param version The version number to be greater than.
     *
     * @return The version constraint.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public static GreaterThan gt(String version) throws InvalidVersionException {
        return new GreaterThan(version);
    }

    /**
     * Requires a version number to be greater than another.
     *
     * @param version The version number to be greater than.
     *
     * @return The version constraint.
     */
    public static GreaterThan gt(Version version) {
        return new GreaterThan(version);
    }

    /**
     * Requires a version number to be greater than or equal to another.
     *
     * @param version The version number to be greater than or equal to.
     *
     * @return The version constraint.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public static GreaterThanOrEqualTo gte(String version) throws InvalidVersionException {
        return new GreaterThanOrEqualTo(version);
    }

    /**
     * Requires a version number to be greater than or equal to another.
     *
     * @param version The version number to be greater than or equal to.
     *
     * @return The version constraint.
     */
    public static GreaterThanOrEqualTo gte(Version version) {
        return new GreaterThanOrEqualTo(version);
    }

    /**
     * Requires a version number to be less than another.
     *
     * @param version The version number to be less than.
     *
     * @return The version constraint.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public static LessThan lt(String version) throws InvalidVersionException {
        return new LessThan(version);
    }

    /**
     * Requires a version number to be less than another.
     *
     * @param version The version number to be less than.
     *
     * @return The version constraint.
     */
    public static LessThan lt(Version version) {
        return new LessThan(version);
    }

    /**
     * Requires a version number to be less than or equal to another.
     *
     * @param version The version number to be less than or equal to.
     *
     * @return The version constraint.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public static LessThanOrEqualTo lte(String version) throws InvalidVersionException {
        return new LessThanOrEqualTo(version);
    }

    /**
     * Requires a version number to be less than or equal to another.
     *
     * @param version The version number to be less than or equal to.
     *
     * @return The version constraint.
     */
    public static LessThanOrEqualTo lte(Version version) {
        return new LessThanOrEqualTo(version);
    }

    /**
     * Requires a version to not be equal to another.
     *
     * @param version The version number to not equal to.
     *
     * @return The version constraint.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public static NotEqualTo ne(String version) throws InvalidVersionException {
        return new NotEqualTo(version);
    }

    /**
     * Requires a version to not be equal to another.
     *
     * @param version The version number to not equal to.
     *
     * @return The version constraint.
     */
    public static NotEqualTo ne(Version version) {
        return new NotEqualTo(version);
    }

    /**
     * Requires that a version match any constraint in a set.
     *
     * @param constraint The constraint to add.
     *
     * @return The version constraint.
     */
    public static Or or(Constraint... constraint) {
        return new Or().add(constraint);
    }

    private Constraints() {
        // Should not be instantiated.
    }
}
