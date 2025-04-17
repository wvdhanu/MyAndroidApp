package com.example.myapp;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;
import androidx.test.uiautomator.UiObject2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import android.app.Instrumentation;

@RunWith(AndroidJUnit4.class)

public class UiAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final int SPLASH_TIMEOUT = 4000;

    private UiDevice device;
    private Context context;
    private String packageName;

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        context = instrumentation.getTargetContext();
        packageName = context.getPackageName();
        device = UiDevice.getInstance(instrumentation);

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);

        if (intent == null) {
            throw new RuntimeException("Launch intent is null. Make sure your manifest defines a MAIN/LAUNCHER activity.");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear previous app state
        context.startActivity(intent);

        // Wait for splash screen and main activity to show
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), LAUNCH_TIMEOUT);
        try {
            Thread.sleep(SPLASH_TIMEOUT); // Wait for splash to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExplicitButtonLaunchesSecondActivity() {
        // Click the "Start Activity Explicitly" button
        UiObject2 explicitButton = device.findObject(By.text("START ACTIVITY EXPLICITLY"));
        assertNotNull("Explicit button not found", explicitButton);
        explicitButton.click();

        // Wait for second activity to appear and check for a challenge text
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), LAUNCH_TIMEOUT);

        // Replace with one of your actual challenge texts
        UiObject2 challengeText = device.findObject(By.textContains("Security and Privacy Issues"));
        assertNotNull("Challenge text not found in second activity", challengeText);
    }
}