package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.Version;

/**
 * Requires a version number to be a pre-release number.
 */
public class PreRelease implements Constraint {

    @Override
    public boolean apply(Version version) {
        return version.isPreRelease();
    }
}
