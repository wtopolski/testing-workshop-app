package com.github.wtopolski.testingworkshopapp;

import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.internal.util.Checks;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkVisibilityOfColorLabel() {
        onView(withId(R.id.color_label)).check(matches(isDisplayed()));
    }

    @Test
    public void initialStateOfColorLabel() {
        onView(withId(R.id.color_label)).check(matches(withText("#FFFFFF")));
        onView(withId(R.id.color_label)).check(matches(withBackgroundColor("#FFFFFF")));
        onView(withId(R.id.color_label)).check(matches(withTextColor("#000000")));
    }

    @Test
    public void initialStateOfClear() {
        onView(withId(R.id.clear)).check(matches(withText("Clear")));
    }

    @Test
    public void clearExecution() {
        onView(withId(R.id.clear)).perform(click());

        onView(withId(R.id.color_label)).check(matches(withText("#FFFFFF")));
        onView(withId(R.id.color_label)).check(matches(withBackgroundColor("#FFFFFF")));
        onView(withId(R.id.color_label)).check(matches(withTextColor("#000000")));
    }

    public static Matcher<View> withBackgroundColor(final String expectedHexColor) {
        Checks.checkNotNull(expectedHexColor);

        return new BoundedMatcher<View, TextView>(TextView.class) {
            private String msg = "";

            @Override
            public boolean matchesSafely(TextView widget) {
                ColorDrawable colorDrawable = (ColorDrawable) widget.getBackground();
                String hexCurrentColor = String.format("#%06X", (0xFFFFFF & colorDrawable.getColor()));
                msg = " " + expectedHexColor + " Current: " + hexCurrentColor;
                return expectedHexColor.equals(hexCurrentColor);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(msg);
            }
        };
    }

    public static Matcher<View> withTextColor(final String expectedHexColor) {
        Checks.checkNotNull(expectedHexColor);

        return new BoundedMatcher<View, TextView>(TextView.class) {
            private String msg = "";

            @Override
            public boolean matchesSafely(TextView widget) {
                String hexCurrentColor = String.format("#%06X", (0xFFFFFF & widget.getCurrentTextColor()));
                msg = " " + expectedHexColor + " Current: " + hexCurrentColor;
                return expectedHexColor.equals(hexCurrentColor);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(msg);
            }
        };
    }
}
