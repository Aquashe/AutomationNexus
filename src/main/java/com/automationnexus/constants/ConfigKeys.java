package com.automationnexus.constants;

public class ConfigKeys {
    // ─── Platform ─────────────────────────────────────────────
    public static final String PLATFORM_NAME         = "platform.name";
    public static final String APPIUM_SERVER_URL     = "appium.server.url";
    public static final String APP_COMMAND_TIMEOUT   = "app.commandTimeout";

    // ─── Android App ──────────────────────────────────────────
    public static final String ANDROID_APP_PATH      = "android.app.path";
    public static final String ANDROID_APP_PACKAGE_NAME   = "android.app.package.name";
    public static final String ANDROID_APP_ACTIVITY_NAME  = "android.app.activity.name";
    public static final String ANDROID_APP_ACTIVITY_WAIT      = "android.app.wait.activity";

    // ─── Android Device ───────────────────────────────────────
    public static final String ANDROID_DEVICE_NAME   = "android.device.name";
    public static final String ANDROID_AUTOMATION_NAME    = "android.automation.name";
    public static final String ANDROID_DEVICE_UDID   = "android.device.udid";

    // ─── Android Real Device ───────────────────────────────────────
    public static final String ANDROID_REAL_DEVICE_NAME   = "android.real.device.name";
    public static final String ANDROID_REAL_DEVICE_UDID   = "android.real.device.udid";

    // ─── Android Timeouts ─────────────────────────────────────
    public static final String ANDROID_UIA2_LAUNCH_TIMEOUT     = "android.uiautomator2ServerLaunchTimeout";
    public static final String ANDROID_UIA2_INSTALL_TIMEOUT    = "android.uiautomator2ServerInstallTimeout";
    public static final String ANDROID_DEVICE_READY_TIMEOUT    = "android.deviceReadyTimeout";

    // ─── Android Emulator ─────────────────────────────────────
    public static final String ANDROID_AVD_NAME      = "android.avd.name";
    public static final String ANDROID_AVD_LAUNCH_TIMEOUT      = "android.avdLaunchTimeout";
    public static final String ANDROID_AVD_READY_TIMEOUT       = "android.avdReadyTimeout";

    // ─── Android Other ────────────────────────────────────────
    public static final String ANDROID_ADB_PATH                = "android.adb.path";

    // ─── iOS ──────────────────────────────────────────────────
    public static final String IOS_DEVICE_NAME       = "ios.device.name";
    public static final String IOS_PLATFORM_VERSION  = "ios.platform.version";
    public static final String IOS_APP_PATH          = "ios.app.path";

    // ─── Web ──────────────────────────────────────────────────
    public static final String WEB_BROWSER_NAME = "web.browser.name";
    public static final String CHROMEDRIVER_PATH       = "chromedriver.path";

    // ─── Console ──────────────────────────────────────────────
    public static final String ENABLE_CONSOLE_COLORS = "enable.console.colors";

    // ─── Test Settings ────────────────────────────────────────
    public static final String APP_USE_EMULATOR  = "app.useEmulator";

    // ─── Maintainence Settings ─────────────────────────────────
    public static final String MAINTENANCE_TIMEOUT  = "maintaince.timeout";
}
