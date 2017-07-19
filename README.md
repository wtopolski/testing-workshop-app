# Testing Workshop App
Sample application using Espresso framework.

## Check state of views
Simple check of view state can look like this: `onView(withId(R.id.clear)).check(matches(withText("Clear")));`

When want to check state after some action, it requires additional lines. First line executes `click` action on Button widget, second check `colorText` parameter of TextView widget.
```
onView(withId(R.id.clear)).perform(click());
onView(withId(R.id.color_label)).check(matches(withText("#FFFFFF")));
```

## Custom Matcher
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
