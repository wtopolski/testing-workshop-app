# Testing Workshop App
Sample application using Espresso framework.
https://developer.android.com/training/testing/espresso/setup.html

## API components

*ViewMatchers* – A collection of objects that implement the Matcher<? super View> interface. You can pass one or more of these to the onView() method to locate a view within the current view hierarchy.

*ViewActions* – A collection of ViewAction objects that can be passed to the ViewInteraction.perform() method, such as click().

*ViewAssertions* – A collection of ViewAssertion objects that can be passed the ViewInteraction.check() method. Most of the time, you will use the matches assertion, which uses a View matcher to assert the state of the currently selected view.

https://google.github.io/android-testing-support-library/downloads/espresso-cheat-sheet-2.1.0.pdf

![alt tag](https://github.com/wtopolski/testing-workshop-app/blob/master/docs/screen1.png)

### Examples

#### Check state of single view
Simple check of view state can look like this: `onView(withId(R.id.clear)).check(matches(withText("Clear")));`

#### Operation on more views
When want to check state after some action, it requires more lines. First line executes `click` action on Button widget, second check `colorText` parameter of TextView widget.
```
onView(withId(R.id.clear)).perform(click());
onView(withId(R.id.color_label)).check(matches(withText("#FFFFFF")));
```

## Matchers

### Combining matchers
We can combine severals matchers using methods `allOf()`, `anyOf()`, `not()`.

`onView(allOf(isDisplayed(), not(instanceOf(Button.class)), not(instanceOf(ViewGroup.class)), withParent(withId(R.id.root)))).check(matches(withText("#FF0000")));`

Or it could look simpler: 

`onView(allOf(isDisplayed(), withClassName(endsWith("TextView")), withParent(withId(R.id.root)))).check(matches(withText("#FF0000")));`

It looks for any `View` under `ViewGroup` with id `R.id.root`, which is not `Button` and `ViewGroup`. It will work until another `View` appears which satisfies these conditions. In that case we will see something similar to:
~~~
android.support.test.espresso.AmbiguousViewMatcherException: '(is displayed on the screen to the user and not an instance of android.widget.Button and not an instance of android.view.ViewGroup and has parent matching: with id: com.github.wtopolski.testingworkshopapp:id/root)' matches multiple views in the hierarchy.
Problem views are marked with '****MATCHES****' below.
~~~

### Custom matcher
When you want to check widget's property which is not suported by default, then create your custom Matcher for that.

```
@Test
public void initialStateOfBackgroundColorLabel() {
  onView(withId(R.id.color_label)).check(matches(withBackgroundColor("#FFFFFF")));
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
```

## Lists

### onData()
*"Instead of using the onView() method, start your search with onData() and provide a matcher against the data that is backing the view you’d like to match. Espresso will do all the work of finding the row in the Adapter object and making the item visible in the viewport."*

*"Note that Espresso scrolls through the list automatically as needed."*

Known Indirect Subclasses of AdapterView: AdapterViewFlipper, AppCompatSpinner, ExpandableListView, Gallery, GridView, ListView, Spinner, StackView

### RecycleView
*"RecyclerView objects work differently than AdapterView objects, so onData() cannot be used to interact with them. To interact with RecyclerViews using Espresso, you can use the `espresso-contrib` package, which has a collection of RecyclerViewActions that can be used to scroll to positions or to perform actions on items."*
