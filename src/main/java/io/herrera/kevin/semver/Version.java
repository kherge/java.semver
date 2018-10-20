package io.herrera.kevin.semver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * An immutable representation of a semantic version number.
 */
final public class Version {
    /**
     * A default version number (0.0.0) that can be used as a starting point.
     */
    public final static Version DEFAULT = new Version(0, 0, 0);

    /**
     * Validates a string as an integer.
     */
    private final static Pattern INTEGER = Pattern.compile("^\\d+$");

    /**
     * Indicates that a version has equivalent precedence.
     */
    private final static int EQUAL = 0;

    /**
     * Indicates that a version has greater precedence.
     */
    private final static int GREATER = 1;

    /**
     * Indicates that a version has lesser precedence.
     */
    private final static int LESSER = -1;

    /**
     * Validates a string as a semantic version number.
     */
    private final static Pattern VALIDATOR = Pattern.compile("^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-([0-9A-Za-z-]+(?:\\.[0-9A-Za-z-]+)*))?(?:\\+([0-9A-Za-z-]+(?:\\.[0-9A-Za-z-]+)*))?$");

    /**
     * The build metadata.
     */
    private List<String> build;

    /**
     * The major version number.
     */
    private int major;

    /**
     * The minor version number.
     */
    private int minor;

    /**
     * The patch version number.
     */
    private int patch;

    /**
     * The pre-release metadata.
     */
    private List<String> preRelease;

    /**
     * Sets the information for the new representation, leaving pre-release and build metadata empty.
     *
     * @param major The major version number.
     * @param minor The minor version number.
     * @param patch The patch version number.
     */
    public Version(int major, int minor, int patch) {
        this(major, minor, patch, Collections.emptyList());
    }

    /**
     * Sets the information for the new representation, leaving build metadata empty.
     *
     * @param major      The major version number.
     * @param minor      The minor version number.
     * @param patch      The patch version number.
     * @param preRelease The pre-release metadata.
     */
    public Version(int major, int minor, int patch, List<String> preRelease) {
        this(major, minor, patch, preRelease, Collections.emptyList());
    }

    /**
     * Sets the information for the new representation.
     *
     * @param major      The major version number.
     * @param minor      The minor version number.
     * @param patch      The patch version number.
     * @param preRelease The pre-release metadata.
     * @param build      The build metadata.
     */
    public Version(int major, int minor, int patch, List<String> preRelease, List<String> build) {
        Objects.requireNonNull(build, "The build metadata list is required (even if empty).");
        Objects.requireNonNull(preRelease, "The pre-release metadata list is required (even if empty).");

        this.build = Collections.unmodifiableList(build);
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.preRelease = Collections.unmodifiableList(preRelease);
    }

    /**
     * Creates a new instance without the build metadata.
     *
     * @return The new instance.
     */
    public Version clearBuild() {
        return new Version(major, minor, patch, preRelease);
    }

    /**
     * Creates a new instance without the pre-release metadata.
     *
     * @return The new instance.
     */
    public Version clearPreRelease() {
        return new Version(major, minor, patch, Collections.emptyList(), build);
    }

    /**
     * Checks if two version numbers have equal precedence.
     *
     * @param object The other version to compare.
     *
     * @return Returns <code>true</code> if the versions are equivalent, or <code>false</code> if otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (!Version.class.isAssignableFrom(object.getClass())) {
            return false;
        }

        return (compare(this, (Version) object) == EQUAL);
    }

    /**
     * Returns the build metadata.
     *
     * @return The build metadata.
     */
    public List<String> getBuild() {
        return build;
    }

    /**
     * Returns the major version number.
     *
     * @return The major version number.
     */
    public int getMajor() {
        return major;
    }

    /**
     * Returns the minor version number.
     *
     * @return The minor version number.
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Returns the patch version number.
     *
     * @return The patch version number.
     */
    public int getPatch() {
        return patch;
    }

    /**
     * Returns the pre-release metadata.
     *
     * @return The pre-release metadata.
     */
    public List<String> getPreRelease() {
        return preRelease;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch, preRelease);
    }

    /**
     * Increments the major version number by 1 (one) and returns a new instance.
     *
     * @return The new instance.
     */
    public Version incrementMajor() {
        return incrementMajor(1);
    }

    /**
     * Increments the major version number by a given amount and returns a new instance.
     *
     * @return The new instance.
     */
    public Version incrementMajor(int amount) {
        return setMajor(major + amount);
    }

    /**
     * Increments the minor version number by 1 (one) and returns a new instance.
     *
     * @return The new instance.
     */
    public Version incrementMinor() {
        return incrementMinor(1);
    }

    /**
     * Increments the minor version number by a given amount and returns a new instance.
     *
     * @return The new instance.
     */
    public Version incrementMinor(int amount) {
        return setMinor(minor + amount);
    }

    /**
     * Increments the patch version number by 1 (one) and returns a new instance.
     *
     * @return The new instance.
     */
    public Version incrementPatch() {
        return incrementPatch(1);
    }

    /**
     * Increments the patch version number by a given amount and returns a new instance.
     *
     * @return The new instance.
     */
    public Version incrementPatch(int amount) {
        return setPatch(patch + amount);
    }

    /**
     * Checks if this version has a greater precedence than the one provided.
     *
     * @param other The other version to compare.
     *
     * @return Returns <code>true</code> if this version has a greater precedence, or <code>false</code> if otherwise.
     */
    public boolean isGreaterThan(Version other) {
        Objects.requireNonNull(other, "The other version is required.");

        return compare(this, other) == GREATER;
    }

    /**
     * Checks if this version has a lesser precedence than the one provided.
     *
     * @param other The other version to compare.
     *
     * @return Returns <code>true</code> if this version has a lesser precedence, or <code>false</code> if otherwise.
     */
    public boolean isLessThan(Version other) {
        Objects.requireNonNull(other, "The other version is required.");

        return compare(this, other) == LESSER;
    }

    /**
     * Checks if the version number is a stable version number.
     *
     * @return Returns <code>true</code> if it is, or <code>false</code> if not.
     */
    public boolean isStable() {
        return (major > 0) && preRelease.isEmpty();
    }

    /**
     * Parses a valid semantic version number into a <code>Version</code> instance.
     *
     * @param string The string representation.
     *
     * @return The new representation.
     *
     * @throws InvalidVersionException If the string is not a valid semantic version number.
     */
    public static Version parse(String string) throws InvalidVersionException {
        Objects.requireNonNull(string, "The string representation is required.");

        if (!VALIDATOR.matcher(string).matches()) {
            throw new InvalidVersionException(
                String.format(
                    "The string \"%s\" is not a valid semantic version number.",
                    string
                )
            );
        }

        List<String> build = new ArrayList<>();
        String metadata[];

        if (string.contains("+")) {
            metadata = string.split("\\+", 2);
            string = metadata[0];

            build = Arrays.asList(metadata[1].split("\\."));
        }

        List<String> preRelease = new ArrayList<>();

        if (string.contains("-")) {
            metadata = string.split("-", 2);
            string = metadata[0];

            preRelease = Arrays.asList(metadata[1].split("\\."));
        }

        String[] numbers = string.split("\\.");

        return new Version(
            Integer.parseInt(numbers[0]),
            Integer.parseInt(numbers[1]),
            Integer.parseInt(numbers[2]),
            preRelease,
            build
        );
    }

    /**
     * Sets the build metadata and returns a new instance.
     *
     * @param metadata The metadata.
     *
     * @return The new instance.
     */
    public Version setBuild(List<String> metadata) {
        Objects.requireNonNull(metadata, "The build metadata list is required (even if empty).");

        return new Version(major, minor, patch, preRelease, metadata);
    }

    /**
     * Sets the build metadata and returns a new instance.
     *
     * @param metadata,... The metadata.
     *
     * @return The new instance.
     */
    public Version setBuild(String... metadata) {
        return setBuild(Arrays.asList(metadata));
    }

    /**
     * Sets the major version number and returns a new instance.
     *
     * @param number The number.
     *
     * @return The new instance.
     */
    public Version setMajor(int number) {
        return new Version(number, minor, patch, preRelease, build);
    }

    /**
     * Sets the minor version number and returns a new instance.
     *
     * @param number The number.
     *
     * @return The new instance.
     */
    public Version setMinor(int number) {
        return new Version(major, number, patch, preRelease, build);
    }

    /**
     * Sets the patch version number and returns a new instance.
     *
     * @param number The number.
     *
     * @return The new instance.
     */
    public Version setPatch(int number) {
        return new Version(major, minor, number, preRelease, build);
    }

    /**
     * Sets the pre-release metadata and returns a new instance.
     *
     * @param metadata The metadata.
     *
     * @return The new instance.
     */
    public Version setPreRelease(List<String> metadata) {
        Objects.requireNonNull(metadata, "The pre-release metadata list is required (even if empty).");

        return new Version(major, minor, patch, metadata, build);
    }

    /**
     * Sets the pre-release metadata and returns a new instance.
     *
     * @param metadata,... The metadata.
     *
     * @return The new instance.
     */
    public Version setPreRelease(String... metadata) {
        return setPreRelease(Arrays.asList(metadata));
    }

    /**
     * Creates a string representation of the version number.
     *
     * @return The string representation.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(major).append(".").append(minor).append(".").append(patch);

        if (!preRelease.isEmpty()) {
            builder.append("-").append(toStringMetadata(preRelease));
        }

        if (!build.isEmpty()) {
            builder.append("+").append(toStringMetadata(build));
        }

        return builder.toString();
    }

    /**
     * Compares two version numbers to determine their precedence.
     *
     * @param left  The left hand side.
     * @param right The right hand side.
     *
     * @return If the left hand side has a greater precedence than the right, <code>1</code> (one) is returned.
     *         If both sides have equal precedence, <code>0</code> (zero) is returned. If the right hand side has
     *         a greater precedence than the left, <code>-1</code> is returned.
     */
    private int compare(Version left, Version right) {
        if (left.major > right.major) {
            return GREATER;
        } else if (left.major < right.major) {
            return LESSER;
        } else if (left.minor > right.minor) {
            return GREATER;
        } else if (left.minor < right.minor) {
            return LESSER;
        } else if (left.patch > right.patch) {
            return GREATER;
        } else if (left.patch < right.patch) {
            return LESSER;
        }

        return comparePreRelease(left.preRelease, right.preRelease);
    }

    /**
     * Compares the pre-release metadata identifier of two version numbers to determine their precedence.
     *
     * @param left  The left hand side.
     * @param right The right hand side.
     *
     * @return If the left hand side has a greater precedence than the right, <code>1</code> (one) is returned.
     *         If both sides have equal precedence, <code>0</code> (zero) is returned. If the right hand side has
     *         a greater precedence than the left, <code>-1</code> is returned.
     */
    private int compareMetadata(String left, String right) {
        int result;

        if (INTEGER.matcher(left).matches() && INTEGER.matcher(right).matches()) {
            result = Integer.parseInt(left) - Integer.parseInt(right);
        } else {
            result = left.compareTo(right);
        }

        if (result == 0) {
            return EQUAL;
        }

        return (result > 0) ? GREATER : LESSER;
    }

    /**
     * Compares the pre-release metadata of two version numbers to determine their precedence.
     *
     * @param left  The left hand side.
     * @param right The right hand side.
     *
     * @return If the left hand side has a greater precedence than the right, <code>1</code> (one) is returned.
     *         If both sides have equal precedence, <code>0</code> (zero) is returned. If the right hand side has
     *         a greater precedence than the left, <code>-1</code> is returned.
     */
    private int comparePreRelease(List<String> left, List<String> right) {
        if (left.isEmpty()) {
            return right.isEmpty() ? EQUAL : GREATER;
        } else if (right.isEmpty()) {
            return LESSER;
        }

        for (int i = 0; i < left.size(); i++) {
            if (i > (right.size() - 1)) {
                return GREATER;
            }

            int result = compareMetadata(left.get(i), right.get(i));

            if (result != EQUAL) {
                return (result > 0) ? GREATER : LESSER;
            }
        }

        if (left.size() < right.size()) {
            return LESSER;
        }

        return EQUAL;
    }

    /**
     * Creates a metadata string for a semantic version number.
     *
     * @param metadata The metadata.
     *
     * @return The string.
     */
    private String toStringMetadata(List<String> metadata) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < metadata.size(); i++) {
            if (i > 0) {
                builder.append(".");
            }

            builder.append(metadata.get(i));
        }

        return builder.toString();
    }
}
