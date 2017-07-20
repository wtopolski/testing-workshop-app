package com.github.wtopolski.testingworkshopapp.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
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
    @BindingAdapter({"fixedSize", "itemAnimator", "layoutManager", "itemDecoration"})
    public static void configure(RecyclerView view, boolean fixedSize, RecyclerView.ItemAnimator itemAnimator, RecyclerView.LayoutManager layoutManager, RecyclerView.ItemDecoration itemDecoration) {
        view.setHasFixedSize(fixedSize);

        if (itemAnimator != null) {
            view.setItemAnimator(itemAnimator);
        }

        if (layoutManager != null) {
            view.setLayoutManager(layoutManager);
        }

        if (itemDecoration != null) {
            view.addItemDecoration(itemDecoration);
        }
    }

    @BindingAdapter({"adapter"})
    public static void adapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }
}
