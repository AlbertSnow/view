package com.albert.viewapplication.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/3/22.
 */

public class HorizontalView extends FrameLayout {
    private static final String TAG = "HorizontalView";
    private int mInitX;
    private int mInitY;

    private int mLastX;
    private int mLastY;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracer;
    private int MIN_FLING_VELOCITY;
    private int mCurrentIndex;
    private int mLastIndex;


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
        MIN_FLING_VELOCITY = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
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
                if (mVelocityTracer == null) {
                    mVelocityTracer = VelocityTracker.obtain();
                } else {
                    mVelocityTracer.clear();
                }

                mInitX = (int) event.getX();
                mInitY = (int) event.getY();
                mLastX = mInitX;
                mLastY = mInitY;
                mVelocityTracer.addMovement(event);
                break;

            case MotionEvent.ACTION_MOVE:
                scrollBy(mLastX - x, 0);
                invalidate();
                mVelocityTracer.addMovement(event);
                break;

            case MotionEvent.ACTION_UP:
                mVelocityTracer.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracer.getXVelocity();

                computeSelectChildIndex(xVelocity);
                scrollToChildIndex();

                mVelocityTracer.clear();
                break;

            case MotionEvent.ACTION_CANCEL:
                mVelocityTracer.clear();
                break;

        }
        mLastX = x;
        mLastY = y;

        return handled;
    }

    private void scrollToChildIndex() {

    }

    private void computeSelectChildIndex(float xVelocity) {
        Log.d(TAG, "xVelocity: " + xVelocity);

        mLastIndex = mCurrentIndex;
        int deltaX = mLastX - mInitX;
        int indexOffset = 0;

        int childWidth = getMeasuredWidth();

        //the absolute value of
        float absIndexXPercent = Math.abs(deltaX * 1.0f % childWidth) / childWidth;

        if (Math.abs(xVelocity) > MIN_FLING_VELOCITY) { // It's trigger fling operate
            indexOffset += xVelocity > 0 ? 1 : -1;
        } else {
            if (deltaX > 0) {// slide right
                indexOffset -= absIndexXPercent > 0.5 ? 1 : 0;
            } else {
                indexOffset += absIndexXPercent > 0.5 ? 1 : 0;
            }
        }

//        getScrollX() / childWidth
        indexOffset = indexOffset > 0 ? indexOffset : 0;

//        mCurrentIndex =

        int indexChildOffset = mCurrentIndex * childWidth;
        int leftOffset = indexChildOffset - Math.abs(getScrollX());
        smoothScrollTo(mCurrentIndex > mLastIndex ? leftOffset : -leftOffset, 0);
    }

    private void smoothScrollTo(int deltaX, int deltaY) {
        mScroller.startScroll(getScrollX(), 0, deltaX, deltaY);
        postInvalidate();
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
