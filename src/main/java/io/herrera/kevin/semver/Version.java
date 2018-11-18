package io.herrera.kevin.semver;

import java.util.Objects;
import java.util.regex.Pattern;
import lombok.SneakyThrows;

/**
 * An immutable representation of a semantic version number.
 */
public final class Version {

    /**
     * A default version number (0.0.0) that can be used as a starting point.
     */
    public static final Version DEFAULT;

    /**
     * The version of the specification that is implemented.
     */
    public static final Version SPEC;

    /**
     * Indicates that a version has equivalent precedence.
     */
    private static final int EQUAL = 0;

    /**
     * Indicates that a version has greater precedence.
     */
    private static final int GREATER = 1;

    /**
     * Indicates that a version has lesser precedence.
     */
    private static final int LESSER = -1;

    /**
     * Validates a build metadata identifier.
     */
    private static final Pattern BUILD_VALIDATOR = Pattern.compile("^[0-9A-Za-z-]+$");

    /**
     * Validates a pre-release metadata identifier.
     */
    private static final Pattern PRE_RELEASE_VALIDATOR = Pattern.compile("^(0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)$");

    /**
     * Validates a string as a semantic version number.
     */
    private static final Pattern STRING_VALIDATOR = Pattern.compile("^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(-(0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(\\.(0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*)?(\\+[0-9a-zA-Z-]+(\\.[0-9a-zA-Z-]+)*)?$");

    /**
     * The build metadata.
     */
    private final String[] build;

    /**
     * The major version number.
     */
    private final int major;

    /**
     * The minor version number.
     */
    private final int minor;

    /**
     * The patch version number.
     */
    private final int patch;

    /**
     * The pre-release metadata.
     */
    private final String[] preRelease;

    static {
        try {
            DEFAULT = new Version(0, 0, 0);
            SPEC = new Version(2, 0, 0);
        } catch (InvalidVersionException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Sets the information for the new representation, leaving pre-release and build metadata empty.
     *
     * @param major The major version number.
     * @param minor The minor version number.
     * @param patch The patch version number.
     *
     * @throws InvalidVersionException If the given version information is not valid.
     */
    public Version(int major, int minor, int patch) throws InvalidVersionException {
        this(major, minor, patch, new String[0]);
    }

    /**
     * Sets the information for the new representation, leaving build metadata empty.
     *
     * @param major      The major version number.
     * @param minor      The minor version number.
     * @param patch      The patch version number.
     * @param preRelease The pre-release metadata.
     *
     * @throws InvalidVersionException If the given version information is not valid.
     */
    public Version(int major, int minor, int patch, String[] preRelease) throws InvalidVersionException {
        this(major, minor, patch, preRelease, new String[0]);
    }

    /**
     * Sets the information for the new representation.
     *
     * @param major      The major version number.
     * @param minor      The minor version number.
     * @param patch      The patch version number.
     * @param preRelease The pre-release metadata.
     * @param build      The build metadata.
     *
     * @throws InvalidVersionException If the given version information is not valid.
     */
    public Version(int major, int minor, int patch, String[] preRelease, String[] build)
        throws InvalidVersionException {
        Objects.requireNonNull(build, "The build metadata is required (even if empty).");
        Objects.requireNonNull(preRelease, "The pre-release metadata is required (even if empty).");

        this.build = isValidIdentifiers("build", BUILD_VALIDATOR, build);
        this.major = atLeastZero("major", major);
        this.minor = atLeastZero("minor", minor);
        this.patch = atLeastZero("patch", patch);
        this.preRelease = isValidIdentifiers("pre-release", PRE_RELEASE_VALIDATOR, preRelease);
    }

    /**
     * Sets the information for the new representation using the string representation.s
     *
     * @param string The string representation.
     *
     * @throws InvalidVersionException If the given version information is not valid.
     */
    public Version(String string) throws InvalidVersionException {
        Objects.requireNonNull(string, "The string representation is required.");

        if (!STRING_VALIDATOR.matcher(string).matches()) {
            throw new InvalidVersionException(
                String.format(
                    "The string \"%s\" is not a valid semantic version number.",
                    string
                )
            );
        }

        String[] metadata;

        if (string.contains("+")) {
            metadata = string.split("\\+", 2);
            string = metadata[0];

            build = metadata[1].split("\\.");
        } else {
            build = new String[0];
        }

        if (string.contains("-")) {
            metadata = string.split("-", 2);
            string = metadata[0];

            preRelease = metadata[1].split("\\.");
        } else {
            preRelease = new String[0];
        }

        String[] numbers = string.split("\\.");

        major = parseInt("major", numbers[0]);
        minor = parseInt("minor", numbers[1]);
        patch = parseInt("patch", numbers[2]);
    }

    /**
     * Creates a new instance without the build metadata.
     *
     * @return The new instance.
     */
    @SneakyThrows(InvalidVersionException.class)
    public Version clearBuild() {
        return new Version(major, minor, patch, preRelease);
    }

    /**
     * Creates a new instance without the pre-release metadata.
     *
     * @return The new instance.
     */
    @SneakyThrows(InvalidVersionException.class)
    public Version clearPreRelease() {
        return new Version(major, minor, patch, new String[0], build);
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

        if (this.getClass() != object.getClass()) {
            return false;
        }

        return isEqualTo((Version) object);
    }

    /**
     * Returns the build metadata.
     *
     * @return The build metadata.
     */
    public String[] getBuild() {
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
    public String[] getPreRelease() {
        return preRelease;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch, preRelease);
    }

    /**
     * Increments the major version number by 1 (one), clearing all other values, and returns a new instance.
     *
     * <p>
     * When the major version number is incremented, the minor and patch version numbers are reset to 0 (zero), and
     * the pre-release and build metadata are cleared.
     * </p>
     *
     * @return The new instance.
     */
    @SneakyThrows(InvalidVersionException.class)
    public Version incrementMajor() {
        return incrementMajor(1);
    }

    /**
     * Increments the major version number by a given amount, clearing all other values,s and returns a new instance.
     *
     * <p>
     * When the major version number is incremented, the minor and patch version numbers are reset to 0 (zero), and
     * the pre-release and build metadata are cleared.
     * </p>
     *
     * @return The new instance.
     *
     * @throws InvalidVersionException If the resulting version number is not valid.
     */
    public Version incrementMajor(int amount) throws InvalidVersionException {
        return new Version(major + amount, 0, 0, new String[0], new String[0]);
    }

    /**
     * Increments the minor version number by 1 (one), clearing lesser values, and returns a new instance.
     *
     * <p>
     * When the minor version number is incremented, the patch version number is reset to 0 (zero), and the pre-release
     * and build metadata are cleared.
     * </p>
     *
     * @return The new instance.
     */
    @SneakyThrows(InvalidVersionException.class)
    public Version incrementMinor() {
        return incrementMinor(1);
    }

    /**
     * Increments the minor version number by a given amount, clearing lesser values, and returns a new instance.
     *
     * <p>
     * When the minor version number is incremented, the patch version number is reset to 0 (zero), and the pre-release
     * and build metadata are cleared.
     * </p>
     *
     * @return The new instance.
     *
     * @throws InvalidVersionException If the resulting version number is not valid.
     */
    public Version incrementMinor(int amount) throws InvalidVersionException {
        return new Version(major, minor + amount, 0, new String[0], new String[0]);
    }

    /**
     * Increments the patch version number by 1 (one), clearing all metadata, and returns a new instance.
     *
     * <p>
     * When the patch version number is incremented, the pre-release and build metadata are cleared.
     * </p>
     *
     * @return The new instance.
     */
    @SneakyThrows(InvalidVersionException.class)
    public Version incrementPatch() {
        return incrementPatch(1);
    }

    /**
     * Increments the patch version number by a given amount, clearing all metadata, and returns a new instance.
     *
     * <p>
     * When the patch version number is incremented, the pre-release and build metadata are cleared.
     * </p>
     *
     * @return The new instance.
     *
     * @throws InvalidVersionException If the resulting version number is not valid.
     */
    public Version incrementPatch(int amount) throws InvalidVersionException {
        return new Version(major, minor, patch + amount, new String[0], new String[0]);
    }

    /**
     * Checks if this version is equal in precedence to the one provided.
     *
     * @param other The other version to compare.
     *
     * @return Returns <code>true</code> if this version has equal precedence, or <code>false</code> if otherwise.
     */
    public boolean isEqualTo(Version other) {
        Objects.requireNonNull(other, "The other version is required.");

        return compare(this, other) == EQUAL;
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
     * Checks if the version number is a pre-release version number.
     *
     * @return Returns <code>true</code> if it is, or <code>false</code> if not.
     */
    public boolean isPreRelease() {
        return (major == 0) || (preRelease.length > 0);
    }

    /**
     * Checks if the version number is a stable version number.
     *
     * @return Returns <code>true</code> if it is, or <code>false</code> if not.
     */
    public boolean isStable() {
        return (major > 0) && (preRelease.length == 0);
    }

    /**
     * Sets the build metadata and returns a new instance.
     *
     * @param metadata,... The metadata.
     *
     * @return The new instance.
     *
     * @throws InvalidVersionException If the metadata is not valid.
     */
    public Version setBuild(String... metadata) throws InvalidVersionException {
        Objects.requireNonNull(metadata, "The build metadata is required (even if empty).");

        return new Version(major, minor, patch, preRelease, metadata);
    }

    /**
     * Sets the major version number and returns a new instance.
     *
     * @param number The number.
     *
     * @return The new instance.
     *
     * @throws InvalidVersionException If the version number is not valid.
     */
    public Version setMajor(int number) throws InvalidVersionException {
        return new Version(number, minor, patch, preRelease, build);
    }

    /**
     * Sets the minor version number and returns a new instance.
     *
     * @param number The number.
     *
     * @return The new instance.
     *
     * @throws InvalidVersionException If the version number is not valid.
     */
    public Version setMinor(int number) throws InvalidVersionException {
        return new Version(major, number, patch, preRelease, build);
    }

    /**
     * Sets the patch version number and returns a new instance.
     *
     * @param number The number.
     *
     * @return The new instance.
     *
     * @throws InvalidVersionException If the version number is not valid.
     */
    public Version setPatch(int number) throws InvalidVersionException {
        return new Version(major, minor, number, preRelease, build);
    }

    /**
     * Sets the pre-release metadata and returns a new instance.
     *
     * @param metadata,... The metadata.
     *
     * @return The new instance.
     *
     * @throws InvalidVersionException If the metadata is not valid.
     */
    public Version setPreRelease(String... metadata) throws InvalidVersionException {
        Objects.requireNonNull(metadata, "The pre-release metadata is required (even if empty).");

        return new Version(major, minor, patch, metadata, build);
    }

    /**
     * Creates a string representation of the version number.
     *
     * @return The string representation.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(major).append(".").append(minor).append(".").append(patch);

        if (preRelease.length > 0) {
            builder.append("-").append(toStringMetadata(preRelease));
        }

        if (build.length > 0) {
            builder.append("+").append(toStringMetadata(build));
        }

        return builder.toString();
    }

    /**
     * Requires that a version number be greater than or equal to 0 (zero).
     *
     * @param position The position of the integer in the version number.
     * @param number   The version number.
     *
     * @throws InvalidVersionException If the number is not at least 0 (zero).
     */
    private int atLeastZero(String position, int number) throws InvalidVersionException {
        if (number < 0) {
            throw new InvalidVersionException(
                String.format("The %s version number must be at least 0 (zero).", position)
            );
        }

        return number;
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

        try {
            result = Integer.parseInt(left) - Integer.parseInt(right);
        } catch (NumberFormatException exception) {
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
    private int comparePreRelease(String[] left, String[] right) {
        if (left.length ==0 ) {
            return (right.length == 0) ? EQUAL : GREATER;
        } else if (right.length == 0) {
            return LESSER;
        }

        for (int i = 0; i < left.length; i++) {
            if (i > (right.length - 1)) {
                return GREATER;
            }

            int result = compareMetadata(left[i], right[i]);

            if (result != EQUAL) {
                return (result > 0) ? GREATER : LESSER;
            }
        }

        if (left.length < right.length) {
            return LESSER;
        }

        return EQUAL;
    }

    /**
     * Checks if a metadata identifier is valid.
     *
     * @param group      The name of the metadata group.
     * @param pattern    The identifier pattern to validate against.
     * @param identifier The metadata identifier to validate.
     *
     * @return The valid identifier.
     *
     * @throws InvalidVersionException If the identifier is not valid.
     */
    private String isValidIdentifier(String group, Pattern pattern, String identifier) throws InvalidVersionException {
        if (!pattern.matcher(identifier).matches()) {
            throw new InvalidVersionException(
                String.format("The %s metadata identifier \"%s\" is not valid.", group, identifier)
            );
        }

        return identifier;
    }

    /**
     * Checks if an array of metadata identifiers are valid.
     *
     * @param group       The name of the metadata group.
     * @param pattern     The identifier pattern to validate against.
     * @param identifiers The metadata identifiers to validate.
     *
     * @return The valid identifiers.
     *
     * @throws InvalidVersionException If the identifiers are not valid.
     */
    private String[] isValidIdentifiers(String group, Pattern pattern, String[] identifiers)
        throws InvalidVersionException {
        for (String identifier : identifiers) {
            isValidIdentifier(group, pattern, identifier);
        }

        return identifiers;
    }

    /**
     * Parses a string integer into a <code>Integer</code>.
     *
     * @param position  The position of the integer in the version number.
     * @param stringInt The string integer to parse.
     *
     * @return The integer.
     *
     * @throws InvalidVersionException If the string integer could not be parsed.
     */
    private Integer parseInt(String position, String stringInt) throws InvalidVersionException {
        try {
            return Integer.parseInt(stringInt);
        } catch (NumberFormatException cause) {
            throw new InvalidVersionException(
                String.format("The %s version number \"%s\" could not be parsed as an integer.", position, stringInt),
                cause
            );
        }
    }

    /**
     * Creates a metadata string for a semantic version number.
     *
     * @param metadata The metadata.
     *
     * @return The string.
     */
    private String toStringMetadata(String[] metadata) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < metadata.length; i++) {
            if (i > 0) {
                builder.append(".");
            }

            builder.append(metadata[i]);
        }

        return builder.toString();
    }
}
