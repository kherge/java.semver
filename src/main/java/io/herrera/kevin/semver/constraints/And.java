package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.Version;

/**
 * Requires that a version match all constraints in a set.
 */
public final class And extends Composite {

    public And() {
        super(true);
    }

    @Override
    public And add(Constraint... constraint) {
        super.add(constraint);

        return this;
    }

    @Override
    protected Result constrain(Version version, Constraint constraint) {
        return constraint.apply(version) ? Result.CONTINUE : Result.FAIL;
    }
}
