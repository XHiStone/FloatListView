package com.example.floatlistview.slide.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class BaseSlideListview extends ListView {
    private int cHeight;
    private int maxHeight;

    private float startY = -1;


    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public BaseSlideListview(Context context) {
        super(context);
    }

    public BaseSlideListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseSlideListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.e("View", "onTouchEvent--->ACTION_DOWN=" + ev.getY());
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e("View", "onTouchEvent--->ACTION_MOVE=" + ev.getY());
                if (startY - ev.getY() > 0 && cHeight < maxHeight) {
//                    Log.e("View", "onTouchEvent--->intercept=" + false);
                    return false;
                } else if (startY - ev.getY() < 0 && isContentViewScrollTop()) {
//                    Log.e("View", "onTouchEvent--->intercept=" + false);
                    return false;
                }
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
//                Log.e("View", "onTouchEvent--->ACTION_UP=" + ev.getY());
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);

    }

    private boolean isContentViewScrollTop() {
        if (getFirstVisiblePosition() == 0) {
            if (getChildCount() > 0) {
                View firstView = getChildAt(0);
                //listview滚动到顶部了
                return firstView != null && firstView.getTop() == 0;
            } else {
                return true;
            }
        }
        return false;
    }
}
