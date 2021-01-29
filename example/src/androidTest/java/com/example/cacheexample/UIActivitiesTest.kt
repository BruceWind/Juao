package com.example.cacheexample

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This class is used to test all feature from activities.
 */
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class UIActivitiesTest {

    companion object {
        private const val PACKAGE = "com.example.hugestfastestmemorycache" //it is appliaction ID.
        private const val LAUNCH_TIMEOUT: Long = 2000
        private const val IDLE_TIMEOUT: Long = 500 //waitForIdle wait it about double.
    }

    var device: UiDevice? = null

    @Before
    fun startMainActivityFromHomeScreen() {


        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device!!.pressHome()

        // Wait for launcher
        val launcherPackage = device!!.launcherPackageName
        ViewMatchers.assertThat(launcherPackage, Matchers.notNullValue())
        device!!.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT)

        // Launch the app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager
                .getLaunchIntentForPackage(PACKAGE)


        // Clear out any previous instances
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        // Wait for the app to appear
        device!!.wait(Until.hasObject(By.pkg(PACKAGE).depth(0)),
                LAUNCH_TIMEOUT)
    }

    @Test
    fun changeText_sameActivity() {


        // Launch a simple calculator app
        val context = InstrumentationRegistry.getInstrumentation().context
        val intent = context.packageManager
                .getLaunchIntentForPackage(PACKAGE)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        // Clear out any previous instances
        context.startActivity(intent)
        device!!.wait(Until.hasObject(By.pkg(PACKAGE).depth(0)), LAUNCH_TIMEOUT)
        testBitmapCache()
        device!!.waitForIdle(IDLE_TIMEOUT)
        testParcelableCache()
        device!!.waitForIdle(IDLE_TIMEOUT)
        testSerializableCache()
        device!!.waitForIdle(IDLE_TIMEOUT)
        //exit all activities.
        device!!.pressBack()
    }

    private fun testBitmapCache() {
        Espresso.onView(ViewMatchers.withId(R.id.txt_test_bitmap)).perform(ViewActions.click())
        //if scrollTo() got error, "Error performing 'scroll to' on view 'Animations or transitions are enabled on the target device!!."
        //look at: https://developer.android.com/training/testing/espresso/setup#set-up-environment
        device!!.waitForIdle(IDLE_TIMEOUT)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        device!!.waitForIdle(IDLE_TIMEOUT)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        device!!.waitForIdle(IDLE_TIMEOUT)
        Espresso.onView(ViewMatchers.withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(17))
        device!!.waitForIdle(IDLE_TIMEOUT)
        device!!.pressBack()
    }

    private fun testParcelableCache() {
        Espresso.onView(ViewMatchers.withId(R.id.txt_test_pacelable)).perform(ViewActions.click())
        clickPutPopButtonsAct()
        clickPutPopButtonsAct()
        device!!.pressBack()
    }

    private fun testSerializableCache() {
        Espresso.onView(ViewMatchers.withId(R.id.txt_test_serilizable)).perform(ViewActions.click())
        device!!.waitForIdle(IDLE_TIMEOUT)
        clickPutPopButtonsAct()
        clickPutPopButtonsAct()
        device!!.pressBack()
    }

    fun clickPutPopButtonsAct() {
        // Type text and then press the button.
        for (i in 0..9) {
            Espresso.onView(ViewMatchers.withId(R.id.btn_put)).perform(ViewActions.click())
            Espresso.onView(ViewMatchers.withId(R.id.btn_pop)).perform(ViewActions.click())

            //TODO check whether text is correct.
        }
    }

}