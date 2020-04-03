package com.albert.viewapplication.custom.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by linchen on 19-2-28.
 * mail: linchen@sogou-inc.com
 * 自适应换行ViewGroup
 */
public class SelfAdaptionViewGroup extends ViewGroup {

    public static final String TAG_ATTACH_RIGHT = "right";
    public boolean needAnotherLine = false;

    public SelfAdaptionViewGroup(Context context) {
        super(context);
        init(context, null);
    }

    public SelfAdaptionViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SelfAdaptionViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {

    }

    public void setNeedAnotherLine(boolean needAnotherLine) {
        this.needAnotherLine = needAnotherLine;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int size = getChildCount();
        if (size == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int groupWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int residueWidth = groupWidth - getPaddingLeft() + getPaddingRight();

        int maxHeight = 0;
        int totalHeight = getPaddingBottom() + getPaddingTop();
        int line = 1;

        for (int i = 0; i < size; ++i) {
            final View child = getChildAt(i);

            if (child.getVisibility() != GONE) {

                String tag = getChildTag(child);
                if (tag.equals(TAG_ATTACH_RIGHT)) { // tag right view == (i == size - 1 )
                    if (i != size - 1) {
                        throw new IllegalStateException("please set right on last View");
                    }
                }


                ChildParams childParams = (ChildParams) child.getLayoutParams();
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                childWidth += childParams.leftMargin + childParams.rightMargin;
                boolean isResidueWidth = residueWidth - childWidth > 0;


                if (!isResidueWidth) {

                    line++;

                    residueWidth = groupWidth;
                    totalHeight += maxHeight;
                    maxHeight = childHeight + childParams.topMargin + childParams.bottomMargin;

                    if (i == size - 1) {
                        totalHeight += maxHeight;
                    }

                } else if (tag.equals(TAG_ATTACH_RIGHT)) {
                    residueWidth = groupWidth;
                    totalHeight += maxHeight;
                    maxHeight = childHeight + childParams.topMargin + childParams.bottomMargin;

                    if (line > 1 || needAnotherLine) {
                        totalHeight += maxHeight;
                    }
                } else {
                    residueWidth -= childWidth;
                    maxHeight = Math.max(maxHeight, childHeight + childParams.topMargin + childParams.bottomMargin);
                    if (i == size - 1) {
                        totalHeight += maxHeight;
                    }
                }

            } else {
                if (i == size - 1) {
                    totalHeight += maxHeight;
                }
            }
        }
        setMeasuredDimension(groupWidth, totalHeight);
    }

    private String getChildTag(View child) {
        String tag;
        if (child.getTag() != null) {
            tag = (String) child.getTag();
        } else {
            tag = "";
        }
        return tag;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        int childMeasureWidth;
        int childMeasureHeight;

        int width = getMeasuredWidth();
        int residueWidth = width - getPaddingLeft() - getPaddingTop();

        int newTop = getPaddingTop();
        int newLeft = getPaddingLeft();
        int maxHeight = 0;
        int line = 1;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }

            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();
            ChildParams childParams = (ChildParams) child.getLayoutParams();
            int childWidthWithMargin = childMeasureWidth + childParams.rightMargin + childParams.leftMargin;

            String tag = getChildTag(child);

            if (residueWidth - childWidthWithMargin > 0) {
                //有剩余空间，直接填充
                residueWidth -= childWidthWithMargin;
                if (tag.equals(TAG_ATTACH_RIGHT)) {
                    if (line > 1 || needAnotherLine) {//换一行展示
                        newLeft = getPaddingLeft();//重置
                        newTop = maxHeight;//指向最高的
                        child.layout(width - getPaddingRight() - childParams.rightMargin - childMeasureWidth, newTop + childParams.topMargin,
                                width - getPaddingRight() - childParams.rightMargin,
                                newTop + childParams.topMargin + childMeasureHeight);
                    } else {//一行展示
                        child.layout(width - getPaddingRight() - childParams.rightMargin - childMeasureWidth, newTop + childParams.topMargin,
                                width - getPaddingRight() - childParams.rightMargin,
                                newTop + childParams.topMargin + childMeasureHeight);
                    }
                } else {
                    child.layout(newLeft + childParams.leftMargin, newTop + childParams.topMargin,
                            newLeft + childParams.leftMargin + childMeasureWidth,
                            newTop + childParams.topMargin + childMeasureHeight);
                    newLeft = child.getRight() + childParams.rightMargin;
                    maxHeight = Math.max(maxHeight, child.getBottom() + childParams.bottomMargin);
                }

            } else {
                //没有剩余空间了，换下一行
                newLeft = getPaddingLeft();//重置
                line++;
                newTop = maxHeight;//指向最高的
                if (tag.equals(TAG_ATTACH_RIGHT)) {
                    child.layout(width - getPaddingRight() - childParams.rightMargin - childMeasureWidth, newTop + childParams.topMargin,
                            width - getPaddingRight() - childParams.rightMargin,
                            newTop + childParams.topMargin + childMeasureHeight);
                } else {
                    residueWidth = width - getPaddingLeft() - getPaddingTop();//重置
                    residueWidth -= childWidthWithMargin;//减去当前高度
                    child.layout(newLeft + childParams.leftMargin, newTop + childParams.topMargin,
                            newLeft + childParams.leftMargin + childMeasureWidth,
                            newTop + childParams.topMargin + childMeasureHeight);
                    newLeft = child.getRight() + childParams.rightMargin;
                    maxHeight = Math.max(maxHeight, child.getBottom() + childParams.bottomMargin);
                }

            }
        }

    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new ChildParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new ChildParams(p);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof ChildParams;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ChildParams(getContext(), attrs);
    }


    private static class ChildParams extends MarginLayoutParams {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public int layPos = LEFT;
        boolean needNewLine = false;

        public ChildParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public ChildParams(int width, int height) {
            super(width, height);
        }

        public ChildParams(MarginLayoutParams source) {
            super(source);
        }

        public ChildParams(LayoutParams source) {
            super(source);
        }
    }


}
