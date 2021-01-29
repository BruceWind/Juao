package com.example.cacheexample;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.notNullValue;

/**
 * This class is used to test all feature from activities.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UIActivitiesTest {

    private static final String PACKAGE = "com.example.hugestfastestmemorycache";//it is appliaction ID.
    private static final long LAUNCH_TIMEOUT = 2000;
    private static final long IDLE_TIMEOUT = 500;//waitForIdle wait it about double.

    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen() {


        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE);


        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);
    }

    @Test
    public void changeText_sameActivity() {


        // Launch a simple calculator app
        Context context = getInstrumentation().getContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Clear out any previous instances
        context.startActivity(intent);

        device.wait(Until.hasObject(By.pkg(PACKAGE).depth(0)), LAUNCH_TIMEOUT);
        testBitmapCache();
        device.waitForIdle(IDLE_TIMEOUT);
        testParcelableCache();
        device.waitForIdle(IDLE_TIMEOUT);
        testSerializableCache();
        device.waitForIdle(IDLE_TIMEOUT);
        //exit all activities.
        device.pressBack();
    }

    private void testBitmapCache() {
        onView(withId(R.id.txt_test_bitmap)).perform(click());
        //if scrollTo() got error, "Error performing 'scroll to' on view 'Animations or transitions are enabled on the target device."
        //look at: https://developer.android.com/training/testing/espresso/setup#set-up-environment

        device.waitForIdle(IDLE_TIMEOUT);
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition(2));
        device.waitForIdle(IDLE_TIMEOUT);
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition(10));
        device.waitForIdle(IDLE_TIMEOUT);
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition(17));
        device.waitForIdle(IDLE_TIMEOUT);
        device.pressBack();
    }

    private void testParcelableCache() {
        onView(withId(R.id.txt_test_pacelable)).perform(click());
        clickPutPopButtonsAct();
        clickPutPopButtonsAct();
        device.pressBack();
    }

    private void testSerializableCache() {

        onView(withId(R.id.txt_test_serilizable)).perform(click());
        device.waitForIdle(IDLE_TIMEOUT);
        clickPutPopButtonsAct();
        clickPutPopButtonsAct();
        device.pressBack();
    }

    public void clickPutPopButtonsAct() {
        // Type text and then press the button.
        for (int i = 0; i < 10; i++) {
            onView(withId(R.id.btn_put)).perform(click());
            onView(withId(R.id.btn_pop)).perform(click());

            //TODO check whether text is correct.
        }
    }
}
