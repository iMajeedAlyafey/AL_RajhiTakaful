package com.example.majid_fit5.al_rajhitakaful;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import  org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.action.ViewActions.*;
/**
 * Created by Eng. Abdulmajid Alyafey on 1/17/2018.
 */

@RunWith(AndroidJUnit4.class)
public class ActivityTest {

    @Rule
    public ActivityTestRule<Activity> activityActivityTestRule = new ActivityTestRule<>(Activity.class);

    /**
     * This test case is only for practicing and testing Espresso.
     */
    @Test
    public void changeText(){

        // check if the filed has "test" written
        onView(withId(R.id.editText_test)).check(matches(withText("test")));

        //type the number inside the phone number filed
        onView(withId(R.id.edt_mobile_input_test)).perform(typeText("0541909490"));

        // perform click on the button that has test "LOGIN"
        onView(withId(R.id.btn_login_test)).perform(click());

        // delete the text and write new one.
        onView(withId(R.id.editText_test)).perform(replaceText("Now Loading")); //replaceText to delete the old text.

        // check if the edit text value has been changed or not.
        onView(withId(R.id.editText_test)).check(matches(withText("Now Loading")));

    }

}
