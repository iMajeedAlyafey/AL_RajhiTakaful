package com.example.majid_fit5.al_rajhitakaful;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.majid_fit5.al_rajhitakaful.login.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
/**
 * Created by Eng. Abdulmajid Alyafey on 1/17/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MobilePhoneInsertionFragmentTest {
    private String phoneNumber; // change it when you test.

    @Rule
    public ActivityTestRule<LoginActivity> activityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void init(){
        phoneNumber = "0541909490";
    }

    /**
     * This test case tests the happy scenario of the sub-feature (Mobile Number Insertion / Getting OTP).
     * @throws InterruptedException
     */
    @Test
    public void getOTPApiServiceHappyScenarioTest() throws InterruptedException {
        //type the mobile phone number in the edit text.
        onView(withId(R.id.edt_mobile_input)).perform(typeText(phoneNumber));

        //click login button
        onView(withId(R.id.btn_login)).perform(click());

        // If the pin code edit text of the Verification Fragment is displayed, then it is the end of this test case, and 100% succeeded.
        onView(withId(R.id.edt_verification_code)).check(matches(isDisplayed()));

    }
}
