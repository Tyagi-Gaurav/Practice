package org.gt.pc;

import java.util.Arrays;

public enum ParentalControlLevel {
    U("U"),
    PG("PG"),
    TWELVE("12"),
    FIFTEEN("15"),
    EIGHTEEN("18");

    private String level;

    ParentalControlLevel(String level) {
        this.level = level;
    }

    public boolean isHigherThan(ParentalControlLevel input) {
        return this.ordinal() >= input.ordinal();
    }


    public static ParentalControlLevel getFrom(String input) {
        return Arrays.stream(ParentalControlLevel.values())
                .filter(x -> x.level.equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
