# Testing Workshop App
Sample application using Espresso framework.

## Custom Matcher
When you want to check widget's property which is not suported by default, then create your custom Matcher for that.

~~~
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
~~~
