package com.github.wtopolski.testingworkshopapp;

import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.internal.util.Checks;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;
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

    @Test
    public void redExecution() {
        onView(withId(R.id.red)).perform(click());

        onView(withId(R.id.color_label)).check(matches(withText("#FF0000")));
        onView(withId(R.id.color_label)).check(matches(withBackgroundColor("#FF0000")));
        onView(withId(R.id.color_label)).check(matches(withTextColor("#00FFFF")));
    }

    @Test
    public void greenExecution() {
        onView(withId(R.id.green)).perform(click());

        onView(withId(R.id.color_label)).check(matches(withText("#00FF00")));
        onView(withId(R.id.color_label)).check(matches(withBackgroundColor("#00FF00")));
        onView(withId(R.id.color_label)).check(matches(withTextColor("#FF00FF")));
    }

    @Test
    public void blueExecution() {
        onView(withText("Blue")).perform(click());

        onView(withId(R.id.color_label)).check(matches(withText("#0000FF")));
        onView(withId(R.id.color_label)).check(matches(withBackgroundColor("#0000FF")));
        onView(withId(R.id.color_label)).check(matches(withTextColor("#FFFF00")));
    }

    @Test
    public void recycleViewScroll() {
        onView(withId(R.id.recycleView)).perform(RecyclerViewActions.scrollToPosition(12));
        onView(withText("#f5f5dc")).check(matches(isDisplayed()));
    }

    @Test
    public void recycleViewScrollAndClick() {
        onView(withId(R.id.recycleView)).perform(RecyclerViewActions.scrollToPosition(12));
        onView(withText("#f5f5dc")).check(matches(isDisplayed()));

        onView(withId(R.id.clear)).perform(click());
        onView(withId(R.id.recycleView)).perform(RecyclerViewActions.actionOnItemAtPosition(12, click()));
        onView(withId(R.id.color_label)).check(matches(withText("#FAFAED")));

        onView(withId(R.id.red)).perform(click());
        onView(withId(R.id.recycleView)).perform(RecyclerViewActions.actionOnItemAtPosition(12, click()));
        onView(withId(R.id.color_label)).check(matches(withText("#FA7A6E")));

        onView(withId(R.id.green)).perform(click());
        onView(withId(R.id.recycleView)).perform(RecyclerViewActions.actionOnItemAtPosition(12, click()));
        onView(withId(R.id.color_label)).check(matches(withText("#7AFA6E")));

        onView(withId(R.id.blue)).perform(click());
        onView(withId(R.id.recycleView)).perform(RecyclerViewActions.actionOnItemAtPosition(11, click()));
        onView(withId(R.id.color_label)).check(matches(withText("#4145C5")));
    }

    @Test
    public void recycleViewAndCustomViewAction() {
        onView(withId(R.id.blue)).perform(click());

        // Now we wait
        IdlingResource idlingResource1 = new ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 2);
        Espresso.registerIdlingResources(idlingResource1);

        // Now we do
        ClickChildViewAction viewAction = new ClickChildViewAction();
        onView(withId(R.id.recycleView)).perform(RecyclerViewActions.actionOnItemAtPosition(11, viewAction.clickChildViewWithId(R.id.element)));

        Espresso.unregisterIdlingResources(idlingResource1);

        // Now we wait again
        IdlingResource idlingResource2 = new ElapsedTimeIdlingResource(DateUtils.SECOND_IN_MILLIS * 2);
        Espresso.registerIdlingResources(idlingResource2);

        // Do again
        onView(withId(R.id.color_label)).check(matches(withText("#4145C5")));

        Espresso.unregisterIdlingResources(idlingResource2);
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

    public class ClickChildViewAction {
        ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }
    }
}
