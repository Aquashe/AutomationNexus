package com.automationnexus.utils;

import java.util.Scanner;

/**
 * ScannerSingleton — Provides one shared Scanner instance for reading
 * console input, reused across ObjectResolutionHandler,
 * MaintenanceModeHandler, and anywhere else that needs interactive
 * input.
 *
 * Using multiple separate Scanner(System.in) instances can cause
 * input-buffering conflicts, so everything routes through this one
 * shared instance instead.
 *
 * @author AutomationNexus
 */
public class ScannerSingleton {

    private static Scanner instance;

    public static synchronized Scanner getInstance() {
        if (instance == null) {
            instance = new Scanner(System.in);
        }
        return instance;
    }

    private ScannerSingleton() {
    }
}