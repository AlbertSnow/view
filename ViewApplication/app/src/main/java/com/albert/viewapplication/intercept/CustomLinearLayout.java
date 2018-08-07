package com.albert.viewapplication.intercept;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class CustomLinearLayout extends android.support.v7.widget.LinearLayoutCompat{

    private int timeCount;

    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flag = super.onInterceptTouchEvent(ev);
        Log.i("TestView", "linearlayout onInterceptTouchEvent flag: " + flag + " action: " + ev.getAction());
        timeCount++;
        if (timeCount > 50) {
            return true;
        }

        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = super.onTouchEvent(event);
        Log.i("TestView", "linearlayout onTouch flag: " + flag + " action: " + event.getAction());
        return flag;
    }
    
}
