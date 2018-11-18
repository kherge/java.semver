package io.herrera.kevin.semver.constraints;

import io.herrera.kevin.semver.Version;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * An abstract implementation of a composite version constraint.
 *
 * <p>
 * A composite constraint is made up of one or more inner constraints. This constraint will iterate through each of
 * the inner constraints and apply them to the given version number. The subclass is responsible for implementing a
 * method that will determine if the next inner constraint should be applied, the version number did <b>not</b> pass
 * the constraint, or the version number <b>did</b> pass the constraint.
 * </p>
 */
abstract class Composite implements Constraint {

    /**
     * The version constraints.
     */
    private Set<Constraint> constraints;

    /**
     * The ultimate result for the constraint.
     */
    private boolean ultimate;

    /**
     * Creates a new composite constraint with an empty set of version constraints.
     *
     * @param ultimate The ultimate result for the constraint.
     */
    public Composite(boolean ultimate) {
        this(new HashSet<>(), ultimate);
    }

    /**
     * Creates a new composite constraint with the given set of version constraints.
     *
     * @param constraints The version constraints.
     * @param ultimate    The ultimate result for the constraint.
     */
    public Composite(Set<Constraint> constraints, boolean ultimate) {
        Objects.requireNonNull(constraints, "The set of constraints is required.");

        this.constraints = constraints;
        this.ultimate = ultimate;
    }

    /**
     * Adds a version constraint to the set.
     *
     * @param constraint The constraint to add.
     *
     * @return A fluent interface.
     */
    public Composite add(Constraint... constraint) {
        for (Constraint c : constraint) {
            Objects.requireNonNull(constraint, "The constraint is required.");

            constraints.add(c);
        }

        return this;
    }

    @Override
    public boolean apply(Version version) {
        Objects.requireNonNull(version, "The version number to constrain is required.");

        for (Constraint constraint: constraints) {
            switch (constrain(version, constraint)) {
                case CONTINUE:
                default:
                    continue;

                case FAIL:
                    return false;

                case PASS:
                    return true;
            }
        }

        return ultimate;
    }

    /**
     * Applies the constraint to the version and returns the result.
     *
     * @param version    The version to constraint.
     * @param constraint The constraint to apply.
     *
     * @return Returns what should happen next.
     */
    protected abstract Result constrain(Version version, Constraint constraint);

    /**
     * Controls what happens after a constraint is applied.
     */
    protected enum Result {

        /**
         * Continue with the next constraint or default to the ultimate result.
         */
        CONTINUE,

        /**
         * Stop at this constraint and return <code>false</code>.
         */
        FAIL,

        /**
         * Stop at this constraint and return <code>true</code>.
         */
        PASS
    }
}
