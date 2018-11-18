package io.herrera.kevin.semver;

/**
 * An exception that is thrown when a version related error is encountered.
 */
public class VersionException extends Exception {
    public VersionException(String message) {
        super(message);
    }

    public VersionException(String message, Throwable cause) {
        super(message, cause);
    }
}
