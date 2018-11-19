package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.Version;

/**
 * Requires a version number to be stable.
 */
public class Stable implements Constraint {

    @Override
    public boolean apply(Version version) {
        return version.isStable();
    }
}
