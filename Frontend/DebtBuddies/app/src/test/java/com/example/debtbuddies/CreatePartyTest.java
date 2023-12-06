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

public class CreatePartyTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule   // needed to launch the activity
    public ActivityTestRule<CreateParty> activityRule = new ActivityTestRule<>(CreateParty.class);

    @Test
    public void CreateParty() {

        onView(withId(R.id.member1))
                .perform(typeText("Kyle"), closeSoftKeyboard());
        onView(withId(R.id.member2))
                .perform(typeText("Kevin"), closeSoftKeyboard());
        onView(withId(R.id.member3))
                .perform(typeText("Owen"), closeSoftKeyboard());
        onView(withId(R.id.b_submit)).perform(click());
    }


}
