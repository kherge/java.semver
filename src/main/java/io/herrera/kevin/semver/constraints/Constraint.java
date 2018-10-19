package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.Version;

/**
 * Defines how a version constraint must be implemented.
 */
public interface Constraint {

    /**
     * Applies the constraint to a version number.
     *
     * @param version The version to constraint.
     *
     * @return Returns <code>true</code> if the version passes or <code>false</code> if not.
     */
    boolean apply(Version version);
}
