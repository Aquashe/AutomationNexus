package com.automationnexus.listeners;

import org.testng.ITestListener;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestListener implements ITestListener {
    public void onTestFailure(org.testng.ITestResult result) {
        System.out.println("Test failed: " + result.getName());
        if (result.getThrowable() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            result.getThrowable().printStackTrace(pw);
            String stackTrace = sw.toString();
            System.out.println("Stack trace: " + stackTrace);
        }
    }

}
