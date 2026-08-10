package com.automationnexus.utils;

/**
 * AnsiColors — Holds ANSI escape code constants used to colorize
 * console output across the framework.
 *
 * This class holds ONLY color data — no logic, no on/off decisions.
 * Whether colors are actually applied is decided in ConsoleUtils.java.
 *
 */
public class AnsiColors {

    // Reset — MUST be printed after any colored text,
    // otherwise ALL following console output stays colored
    public static final String RESET = "\u001b[0m";

    // Text styles
    public static final String BOLD = "\u001b[1m";

    // Standard colors
    public static final String BLACK  = "\u001b[30m";
    public static final String RED    = "\u001b[31m";
    public static final String GREEN  = "\u001b[32m";
    public static final String YELLOW = "\u001b[33m";
    public static final String BLUE   = "\u001b[34m";
    public static final String PURPLE = "\u001b[35m";
    public static final String CYAN   = "\u001b[36m";
    public static final String WHITE  = "\u001b[37m";

    // Bright variants
    public static final String BRIGHT_RED    = "\u001b[91m";
    public static final String BRIGHT_GREEN  = "\u001b[92m";
    public static final String BRIGHT_YELLOW = "\u001b[93m";
    public static final String BRIGHT_BLUE   = "\u001b[94m";
    public static final String BRIGHT_CYAN   = "\u001b[96m";

    // Semantic aliases — use these in code, not raw colors above.
    // This way, if we want to change what "error" looks like later,
    // we change it in ONE place here, not everywhere it's used.
    public static final String ERROR     = BRIGHT_RED;
    public static final String SUCCESS   = GREEN;
    public static final String WARNING   = YELLOW;
    public static final String INFO      = BRIGHT_BLUE;
    public static final String HIGHLIGHT = BRIGHT_CYAN;   // e.g. the AI option in menu
    public static final String MUTED     = "\u001b[90m";  // dim/secondary text

    // Background colors (rarely used, but handy to have ready)
    public static final String YELLOW_BACKGROUND = "\u001b[43m";
    public static final String BLUE_BACKGROUND   = "\u001b[44m";

    // Prevent instantiation — this class is constants-only
    private AnsiColors() {
    }
}