package com.dateplanner.util;

public class Ansi {

    public static int getColor(String color) {
        switch (color) {
            case "red": return 160;
            case "green": return 118;
            case "blue": return 39;
            default: return 231;
        }
    }
}