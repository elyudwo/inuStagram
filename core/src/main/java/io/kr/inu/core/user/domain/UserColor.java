package io.kr.inu.core.user.domain;

import java.util.Random;

public enum UserColor {
    FIRST("0xFF3780ED"),
    SECOND("0xFF17D107"),
    THIRD("0xFFFF6D1B"),
    FOURTH("0xFFC6BE00"),
    FIFTH("0xFFA709CF");


    private final String color;
    private static final Random RANDOM = new Random();

    UserColor(String color) {
        this.color = color;
    }

    private String getColor() {
        return color;
    }

    public static String issueRandomColor() {
        UserColor[] VALUES = values();
        return VALUES[RANDOM.nextInt(values().length)].getColor();
    }
}
