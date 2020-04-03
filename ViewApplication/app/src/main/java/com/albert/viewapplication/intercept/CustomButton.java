package com.albert.viewapplication.intercept;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = super.onTouchEvent(event);
        Log.i("TestView", "Button onTouch flag: " + flag + " action: " + event.getAction());
        return flag;
    }

}
