package com.example.flowlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FlowView extends ViewGroup {

    /**
     * 子控件之间的宽度
     */

    private int childPadding = 15;
    private float mHorizontalSpace=childPadding;
    private float mVerticalSpace=childPadding;
    private OnFlowItemClick onFlowItemClick;

    public void setOnFlowItemClick(OnFlowItemClick onFlowItemClick) {
        this.onFlowItemClick = onFlowItemClick;
    }

    public FlowView(Context context) {
        this(context, null);
    }

    public FlowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("xkff", "FlowView=============");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //measureChildBeforeLayout(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int wResult = wSpecSize;

        if (wSpecMode==MeasureSpec.AT_MOST)
        {
            wResult = Math.min(wResult, getChildTotalWidth());
        }

        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int hResult = hSpecSize;

        if (hSpecMode==MeasureSpec.AT_MOST)
        {
            hResult  = Math.min(wResult, getChildTotalHeight(wResult));
        }
        setMeasuredDimension(wResult, hResult);

//        measureChildren(widthMeasureSpec, heightMeasureSpec);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("xkff", "onMeasure=============");

    }

    private int getChildTotalWidth(){
        int totalWidth = getPaddingLeft() + getPaddingRight();
        for (int i=0;i<getChildCount();i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            if (View.GONE == child.getVisibility()) continue;

            totalWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
        }
        /*horizontal方向的间距为控件个数-1*/
        totalWidth += mHorizontalSpace * (getChildCount() - 1);
        return totalWidth;
    }

    private int getChildTotalHeight(int maxWidth) {

        int totalHeight = getTop() + getBottom();
        int colIndex = 0;
        int rowIndex = 0;
        int rowWidth = getPaddingLeft() + getPaddingRight();
        int rowHeight = 0;

        for (int i=0;i<getChildCount();i++)
        {
            View child = getChildAt(i);
            if (View.GONE == child.getVisibility()) continue;

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (colIndex == 0) {
                if (rowWidth + childWidth > maxWidth) {
                    totalHeight += childHeight;
                    if (rowIndex != 0) {
                        totalHeight += mVerticalSpace;
                    }
                    rowWidth = getPaddingLeft() + getPaddingRight();
                    rowHeight = 0;
                    rowIndex++;
                } else {
                    colIndex++;
                    rowWidth += childWidth;
                    if (rowIndex == 0) {
                        rowHeight = Math.max(rowHeight, childHeight);
                    } else {
                        rowHeight = (int) Math.max(rowHeight, childHeight + mVerticalSpace);
                    }
                }
            } else {
                if (rowWidth + childWidth + mHorizontalSpace > maxWidth) {
                    totalHeight += rowHeight;
                    rowWidth = getPaddingLeft() + getPaddingRight() + childWidth;
                    rowHeight = (int) (childHeight + mVerticalSpace);
                    colIndex = 1;
                    rowIndex++;
                } else {
                    colIndex++;
                    rowWidth += childWidth + mHorizontalSpace;
                    if (rowIndex == 0) {
                        rowHeight = Math.max(rowHeight, childHeight);
                    } else {
                        rowHeight = (int) Math.max(rowHeight, childHeight + mVerticalSpace);
                    }
                }
            }
        }

        totalHeight += rowHeight;
        return totalHeight;
    }

    private void measureChildBeforeLayout(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (View.GONE == child.getVisibility()) continue;
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = getLeft() + getPaddingLeft();
        int top = getTop() + getPaddingTop();
        int right = getRight() - getPaddingRight();
        int bottom = getBottom() - getPaddingBottom();

        int lines = 0;//布局到第几行
        for (int i = 0; i < childCount; i++) {
            Log.e("xkff", "onLayout=============" + l + "=======" + t);
            View childAt = getChildAt(i);
            int childRight = left + childAt.getMeasuredWidth();
            int childBottom = top + childAt.getMeasuredHeight();
            //如果view大于边界值则换行
            if (childRight > right) {
                lines++;
                left = getLeft() + getPaddingLeft();
                childRight = left + childAt.getMeasuredWidth();

                top = getTop() + getPaddingTop() + lines * childAt.getMeasuredHeight() + childPadding * lines;
                childBottom = top + childAt.getMeasuredHeight();
            }

            if (childBottom <= bottom) {
                //减去父控件的起始坐标
                childAt.layout(left - l, top - t, childRight - l, childBottom - t);
                left += childAt.getMeasuredWidth() + childPadding;

            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("xkff", "onSizeChanged=============");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("xkff", "onDraw=============");
    }

    public void setDatas(List<String> texts) {
        if (texts == null)
            return;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < texts.size(); i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_flow, this, false);
            textView.setText(texts.get(i));
            final int position = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFlowItemClick != null) {
                        onFlowItemClick.onItemClick(position);
                    }
                }
            });
            this.addView(textView);
        }
        requestLayout();
    }

    public interface OnFlowItemClick {
        void onItemClick(int position);
    }
}
