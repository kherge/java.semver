[![Build Status](https://travis-ci.org/kherge/java.semver.svg?branch=master)](https://travis-ci.org/kherge/java.semver)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=kherge_java.semver&metric=alert_status)](https://sonarcloud.io/dashboard?id=kherge_java.semver)

Semantic Versioning
===================

**semver** is a library to parse, compare, and constrain semantic version numbers.

This library revolves around an immutable value object called `Version`. The class is capable of parsing valid string
representations of semantic version numbers, managing version number information (changes create new instances), and
performing basic comparisons. The library also includes a basic set of constraints that can be assembled to create
complex version constraints.

> The library does not include the ability to parse string representations of version constraints (e.g. `>=1.0.0`)
> into instances of the bundled version constraints (e.g. `new GreaterThanOrEqualTo(new Version(1, 0, 0))`). There
> is no one-size-fits-all solution since each package manager has its own grammar for version constraints.

```java
Version version = new Version ("1.2.3");

if (and(gte("1.0.0"), lt("2.0.0")).apply(version)) {
    // ...
}
```

Requirements
------------

- Java 8

Usage
-----

```java
import static io.herrera.kevin.semver.constraints.Constraints.*;

import io.herrera.kevin.semver.constraints.Constraint;
import io.herrera.kevin.semver.Version;

class Example {
    public static void main() {
        
        // Create a version number using a string representation.
        Version version = new Version("1.0.0-alpha.1+20181020.123");
        
        // Create a version number using only number.
        Version version = new Version(1, 2, 3);
        
        // Create a version number using numbers and pre-release metadata.
        Version version = new Version(1, 2, 3, new String[] {"alpha", "1"});
        
        // Create a version number using numbers, pre-release, and build metadata.
        Version version = new Version(
            1,
            2,
            3,
            new String[] {"alpha", "1"},
            new String[] {"20181020", "123"}
        );
        
        // Create a version number using the default version number (0.0.0).
        Version version = Version.DEFAULT;
        
        // Create a version number using a builder interface.
        Version version = Version
            .DEFAULT
            .setMajor(1)
            .setMinor(2)
            .setPatch(3)
            .setPreRelease("alpha", "1")
            .setBuild("20181020", "123");
        
        // Get version information.
        int major = version.getMajor();
        int minor = version.getMinor();
        int patch = version.getPatch();
        String[] preRelease = version.getPreRelease();
        String[] build = version.getBuild();
        
        // Set version information.
        Version changed = version
            .clearPreRelease()
            .clearBuild()
            .setMajor(4)
            .setMinor(5)
            .setPatch(6)
            .setPreRelease("beta", "2")
            .setBuild("20181020", "456");
        
        // Increment version information.
        Version changed = version
            .incrementMajor()
            .incrementMinor()
            .incrementPatch();
        
        Version changed = version
            .incrementMajor(123)
            .incrementMinor(456)
            .incrementPatch(789);
        
        // Create a string representation.
        String string = version.toString();
        
        // Perform basic comparisons.
        Version another = new Version("2.0.0");
        
        if (version.isEqualTo(another)) {
            // Can also use: version.equals(another)
        }
        
        if (version.isGreaterThan(another)) {
            // ...
        }
        
        if (version.isLessThan(another)) {
            // ...
        }
        
        // Perform stability checks.
        
        if (version.isPreRelease()) {
            // ...
        }
        
        if (version.isStable()) {
            // ...
        }
        
        // Can create and apply complex version constraints.
        
        // Match any of the following conditions.
        Constraint constraint = or(
            
            // Match all of these conditions.
            and(
                
                // The version must be greater than or equal to this one.
                gte("1.0.0"),
                
                // The version must be less than this one.
                lt("2.0.0"),
                
                // The version must not equal to this one.
                ne("1.2.3")
            ),
            
            // Match all of these conditions.
            and(
                
                // The version must be greater than this one.
                gt("2.0.0"),
                
                // The version must be less than or equal to this one.
                lte("2.1.0")
            ),
            
            // The version must be equal to this one.
            eq("9.9.9")
        );
        
        if (constraint.apply(version)) {
            // ...
        }
    }
}
```

License
-------

This library is made available under the MIT and Apache 2.0 licenses.
