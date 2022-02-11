package com.yoti.test.model;

import java.util.Arrays;

/**
 * The {@code Direction} class represents a course for a robotic hoover based on compass main directions.
 *
 */

public enum Direction {

    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W");

    private final String value;

    Direction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Direction of(String value) {
        return Arrays.stream(Direction.values())
            .filter(direct -> direct.getValue().equals(value))
            .findFirst()
            .orElse(null);
    }

    public static boolean isValue(String value) {
        return Arrays.stream(Direction.values())
            .anyMatch(direct -> direct.getValue().equals(value));
    }
}
