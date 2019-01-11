package com.gradle

class ProjectVersion {
    Integer major
    Integer minor
    Boolean release

    ProjectVersion(Integer major, Integer minor) {
        this(major, minor, Boolean.FALSE)
    }

    ProjectVersion(Integer major, Integer minor, Boolean release) {
        this.major = major
        this.minor = minor
        this.release = release
    }


    @Override
    String toString() {
        return "$major.$minor${release ? "" : "-SNAPSHOT"}"
    }
}
