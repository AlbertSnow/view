package com.albert.viewapplication.drawmodel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.albert.viewapplication.util.SystemUtils;

public class CountDrawView extends View {
    protected long countDraw = 0;
    private String Tag = "TestHW";
    protected Paint mTextPaint;

    public CountDrawView(Context context) {
        super(context);
        init(context);
    }

    public CountDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mTextPaint = new Paint();
        mTextPaint.setTextSize(SystemUtils.sp2px(context, 15));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (countDraw > (Long.MAX_VALUE - 1)) {
            Log.e(Tag, "count max,reset");
            countDraw = 0;
        }
        countDraw++;

        canvas.translate(50, 50);
        canvas.drawText("Times: " + countDraw, 0, 0, mTextPaint);
    }
}
