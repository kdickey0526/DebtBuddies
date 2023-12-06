package com.example.debtbuddies;

import com.example.debtbuddies.LoginScreenActivity;

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

public class ProfileIconTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule   // needed to launch the activity
    public ActivityTestRule<ProfileIcons> activityRule = new ActivityTestRule<>(ProfileIcons.class);

    @Test
    public void profile() {
        onView(withId(R.id.b_icon0)).perform(click());
        onView(withId(R.id.b_icon1)).perform(click());
        onView(withId(R.id.b_icon2)).perform(click());
        onView(withId(R.id.b_icon3)).perform(click());
        onView(withId(R.id.b_icon4)).perform(click());
        onView(withId(R.id.b_icon5)).perform(click());
        onView(withId(R.id.b_icon6)).perform(click());
        onView(withId(R.id.b_icon7)).perform(click());
        onView(withId(R.id.b_icon8)).perform(click());
    }


}
