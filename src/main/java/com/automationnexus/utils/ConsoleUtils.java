package com.automationnexus.utils;

import com.automationnexus.config.ConfigReader;
/**
 * ConsoleUtils — Centralized console printing with optional ANSI coloring.
 *
 * This is the ONLY class that decides whether colors are actually applied.
 * All other classes should call these methods instead of System.out
 * directly, so color behavior stays consistent framework-wide.
 *
 */
public class ConsoleUtils {

    // Read once — no need to check config.properties on every single print
    private static final boolean COLORS_ENABLED =
            Boolean.parseBoolean(
                    ConfigReader.get("enable.console.colors"));

    /**
     * Prints a plain message with a newline. No color.
     */
    public static void printLine(String message) {
        System.out.println(message);
    }

    /**
     * Prints a colored message with a newline.
     * Falls back to plain text if colors are disabled in config.
     */
    public static void printLine(String message, String color) {
        System.out.println(colorize(message, color));
    }

    /**
     * Prints a colored message WITHOUT a newline.
     * Useful for prompts like "Enter your selection: "
     */
    public static void print(String message, String color) {
        System.out.print(colorize(message, color));
    }

    /**
     * Wraps a message in the given color code + reset,
     * or returns it unchanged if colors are disabled.
     */
    private static String colorize(String message, String color) {
        if (!COLORS_ENABLED || color == null) {
            return message;
        }
        return color + message + AnsiColors.RESET;
    }

    private ConsoleUtils() {
    }
}