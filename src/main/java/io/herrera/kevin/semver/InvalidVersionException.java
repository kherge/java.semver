package io.herrera.kevin.semver;

/**
 * An exception that is thrown when a version number is not a valid semantic version number.
 */
public class InvalidVersionException extends VersionException {
    public InvalidVersionException(String message) {
        super(message);
    }

    public InvalidVersionException(String message, Throwable cause) {
        super(message, cause);
    }
}
