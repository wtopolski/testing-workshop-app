package com.github.wtopolski.testingworkshopapp;

import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.internal.util.Checks;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;

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
        onView(withTextColor("#000000")).check(matches(withBackgroundColor("#FFFFFF")));
    }

    @Test
    public void redExecution() {
        onView(withId(R.id.red)).perform(click());

        onView(withId(R.id.color_label)).check(matches(withText("#FF0000")));
        onView(withId(R.id.color_label)).check(matches(withBackgroundColor("#FF0000")));
        onView(withId(R.id.color_label)).check(matches(withTextColor("#00FFFF")));
        onView(withTextColor("#00FFFF")).check(matches(withBackgroundColor("#FF0000")));
    }

    @Test
    public void greenExecution() {
        onView(withId(R.id.green)).perform(click());

        onView(withId(R.id.color_label)).check(matches(withText("#00FF00")));
        onView(withId(R.id.color_label)).check(matches(withBackgroundColor("#00FF00")));
        onView(withId(R.id.color_label)).check(matches(withTextColor("#FF00FF")));
        onView(withTextColor("#FF00FF")).check(matches(withBackgroundColor("#00FF00")));
    }

    @Test
    public void blueExecution() {
        onView(withText("Blue")).perform(click());

        onView(withId(R.id.color_label)).check(matches(withText("#0000FF")));
        onView(withId(R.id.color_label)).check(matches(withBackgroundColor("#0000FF")));
        onView(withId(R.id.color_label)).check(matches(withTextColor("#FFFF00")));
        onView(withTextColor("#FFFF00")).check(matches(withBackgroundColor("#0000FF")));
    }

    @Test
    public void indirectRedExecution() {
        onView(withText("Red")).perform(click());
        onView(allOf(isDisplayed(), not(instanceOf(Button.class)), not(instanceOf(ViewGroup.class)), withParent(withId(R.id.root)))).check(matches(withText("#FF0000")));
        onView(allOf(isDisplayed(), withClassName(endsWith("TextView")), withParent(withId(R.id.root)))).check(matches(withText("#FF0000")));
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
