package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.Version;

/**
 * Requires that a version match any constraint in a set.
 */
final public class Or extends Composite {

    public Or() {
        super(false);
    }

    @Override
    public Or add(Constraint constraint) {
        super.add(constraint);

        return this;
    }

    @Override
    protected Result constrain(Version version, Constraint constraint) {
        return constraint.apply(version) ? Result.PASS : Result.CONTINUE;
    }
}
