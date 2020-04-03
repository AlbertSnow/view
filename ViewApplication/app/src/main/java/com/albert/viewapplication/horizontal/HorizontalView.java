package com.albert.viewapplication.horizontal;

import android.content.Context;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * This custom view is implement Scroll and Fling operate.
 * Created by AlbertSnow on 2017/3/22.
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
            if (childView == null || childView.getVisibility() == View.GONE) {
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

        return true;
    }

    private void scrollToChildIndex() {

    }

    private void computeSelectChildIndex(float xVelocity) {
        Log.d(TAG, "xVelocity: " + xVelocity);

        mLastIndex = mCurrentIndex;
        int deltaX = mLastX - mInitX;

        int childWidth = getMeasuredWidth();

        //the absolute value of
        float absIndexXPercent = Math.abs(deltaX * 1.0f % childWidth) / childWidth;
        int absLeftOffset;

        if (Math.abs(xVelocity) > 50) { // It's trigger fling operate
            mCurrentIndex += xVelocity > 0 ? -1 : 1;
            absLeftOffset = (int) (Math.abs(deltaX * 1.0f % childWidth));
            if (mCurrentIndex < 0 || getScrollX() < 0) {
                //index out of rang but velocity make reverse accelerate
            } else {
                absLeftOffset = childWidth - absLeftOffset;
            }
        } else {
            if (deltaX > 0) {
                mCurrentIndex -= absIndexXPercent > 0.5 ? 1 : 0;
            } else {
                mCurrentIndex += absIndexXPercent > 0.5 ? 1 : 0;
            }
            absLeftOffset = (int) (Math.abs(deltaX * 1.0f % childWidth));

            if (mCurrentIndex < 0) {

            } else {
                if (absIndexXPercent > 0.5) {
                    absLeftOffset = childWidth - absLeftOffset;
                }
            }

        }

        int scrollDeltaX = 0;

        if (mCurrentIndex < 0) {
            scrollDeltaX = absLeftOffset;
        } else {
            scrollDeltaX = mCurrentIndex > mLastIndex ? absLeftOffset : -absLeftOffset;
        }
        mCurrentIndex = mCurrentIndex > 0 ? mCurrentIndex : 0;


        smoothScrollBy(scrollDeltaX, 0);
    }

    private void smoothScrollBy(int deltaX, int deltaY) {
        mScroller.startScroll(getScrollX(), 0, deltaX, deltaY);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

}
