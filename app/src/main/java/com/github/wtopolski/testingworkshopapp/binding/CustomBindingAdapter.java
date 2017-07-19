package com.github.wtopolski.testingworkshopapp.binding;

import android.view.View;
import android.widget.TextView;

public class CustomBindingAdapter {

    @android.databinding.BindingAdapter({"textColor"})
    public static void setTextColor(TextView view, Integer color) {
        if (color != null) {
            view.setTextColor(color);
        }
    }

    @android.databinding.BindingAdapter({"backgroundColor"})
    public static void setBackgroundColor(View view, Integer color) {
        if (color != null) {
            view.setBackgroundColor(color);
        }
    }
}
