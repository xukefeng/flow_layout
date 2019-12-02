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
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("xkff", "onMeasure=============");

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
            Log.e("xkff", "onLayout============="+l+"======="+t);
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
                childAt.layout(left-l, top - t, childRight - l, childBottom - t);
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
            this.addView(textView);
        }
        requestLayout();
    }
}
