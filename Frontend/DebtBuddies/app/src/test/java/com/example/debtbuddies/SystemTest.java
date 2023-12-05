package com.example.debtbuddies;

import com.example.debtbuddies.LoginScreenActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;



//import androidx.test.rule.ActivityTestRule; //for some reason this no work :-O
import android.support.test.rule.ActivityTestRule; //use this instead idk????

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;


import android.util.Log;
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time

public class SystemTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule   // needed to launch the activity
    public ActivityTestRule<LoginScreenActivity> activityRule = new ActivityTestRule<>(LoginScreenActivity.class);

    @Test
    public void login() {
        onView(withId(R.id.usernameField)).perform(typeText("guest"), closeSoftKeyboard());
        //onView(withId(R.id.loginBtn)).perform(click());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        // Verify that volley returned the correct value
        onView(withId(R.id.usernameField)).check(matches(withText(endsWith("guest"))));
    }
    @Rule   // needed to launch the activity
    public ActivityTestRule<BlackJack> blackjackRule = new ActivityTestRule<>(BlackJack.class);

    @Test
    public void blackJack() {
        onView(withId(R.id.b_deal)).perform(click());
        onView(withId(R.id.b_deal)).perform(click());
        onView(withId(R.id.b_deal)).perform(click());
        onView(withId(R.id.b_deal)).perform(click());
        onView(withId(R.id.b_deal)).perform(click());

        onView(withId(R.id.b_stand)).perform(click());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.b_replay)).perform(click());
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
//        onView(withId(R.id.b_double)).perform(click());
//        onView(withId(R.id.b_stand)).perform(click());
//        onView(withId(R.id.b_menu)).perform(click());

        onView(withId(R.id.coinCount)).check(matches(withText(endsWith("45"))));
    }


}
