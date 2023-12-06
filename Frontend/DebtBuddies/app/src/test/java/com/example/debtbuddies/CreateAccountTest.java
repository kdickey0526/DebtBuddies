package com.example.debtbuddies;

import com.example.debtbuddies.CreateAccountActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
//import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;


import android.support.test.rule.ActivityTestRule;
import android.util.Log;
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time

public class CreateAccountTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule   // needed to launch the activity
    public ActivityTestRule<CreateAccountActivity> activityRule = new ActivityTestRule<>(CreateAccountActivity.class);

    @Test
    public void CreateAccount() {
        onView(withId(R.id.tv_username))
                .perform(typeText("Brock"), closeSoftKeyboard());
        onView(withId(R.id.tv_email))
                .perform(typeText("bdykhuis@iastate.edu"), closeSoftKeyboard());
        onView(withId(R.id.tv_password))
                .perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.tv_confirm))
                .perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.b_save)).perform(click());
        onView(withId(R.id.b_submit)).perform(click());

    }


}
