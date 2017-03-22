package com.albert.viewapplication.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/3/22.
 */

public class HorizontalView extends FrameLayout {
    private Scroller mScroller;
    private int mInitX;
    private int mInitY;
    private int mLastX;
    private int mLastY;

    public HorizontalView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public HorizontalView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HorizontalView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        mScroller = new Scroller(context);
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
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView == null && childView.getVisibility() == View.GONE) {
                continue;
            }

            int childRight = childLeft + childView.getMeasuredWidth();
            childView.layout(childLeft, 0, childRight, childView.getMeasuredHeight());
            childLeft = childRight;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = true;

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                mInitX = (int) event.getX();
                mInitY = (int) event.getY();
                mLastX = mInitX;
                mLastY = mInitY;

                break;
            case MotionEvent.ACTION_MOVE:
                scrollBy(mLastX - x, 0);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        mLastX = x;
        mLastY = y;

        return handled;
    }

    private void smoothScrollTo() {

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }
}
