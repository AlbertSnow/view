package com.albert.viewapplication.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/3/22.
 */

public class HorizontalView extends FrameLayout {

    public HorizontalView(@NonNull Context context) {
        super(context);
        init();
    }

    public HorizontalView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!changed) {
            return;
        }

        int childCount = getChildCount();
        int childLeft = 0;
        for(int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView == null && childView.getVisibility() == View.GONE) {
                continue;
            }

            int childRight = childLeft + childView.getMeasuredWidth();
            childView.layout(childLeft, 0, childRight, childView.getMeasuredHeight());
            childLeft = childRight;
        }
    }
}
