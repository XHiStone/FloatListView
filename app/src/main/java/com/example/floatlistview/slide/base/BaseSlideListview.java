package com.example.floatlistview.slide.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class BaseSlideListview extends ListView {
    private int cHeight;
    private int maxHeight;

    private float startY = -1;
    private boolean scrollTop;

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public BaseSlideListview(Context context) {
        super(context);
        init();
    }

    public BaseSlideListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseSlideListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setScrollTop() {
        int scrollBy;
        if (getChildCount() > 0) {
            View v = getChildAt(0);
            int top = v.getTop();
            if (top < 0) scrollBy = top;
            else scrollBy = -v.getHeight();
            if (Math.abs(scrollBy) > 0) {
                scrollTop = true;
                smoothScrollBy(scrollBy, 0);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        cHeight = MeasureSpec.getSize(heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        cHeight = b - t;
//        if (t > 0 && t != b) smoothScrollToPosition(0);
//        Log.e("tag", "onLayout--->" + changed + " " + l + " " + t + " " + r + " " + b);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.e("View", "onTouchEvent--->ACTION_DOWN=" + ev.getY());
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e("View", "onTouchEvent--->ACTION_MOVE=" + ev.getY());
//                Log.e("View", "onTouchEvent--->cHeight=" + cHeight);
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
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
////                Log.e("View", "onTouchEvent--->ACTION_DOWN=" + ev.getY());
//                startY = ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
////                Log.e("View", "onTouchEvent--->ACTION_MOVE=" + ev.getY());
////                Log.e("View", "onTouchEvent--->cHeight=" + cHeight);
//                if (startY - ev.getY() > 0 && cHeight < maxHeight) {
////                    Log.e("View", "onTouchEvent--->intercept=" + false);
//                    return false;
//                } else if (startY - ev.getY() < 0 && isContentViewScrollTop()) {
////                    Log.e("View", "onTouchEvent--->intercept=" + false);
//                    return false;
//                }
//                startY = ev.getY();
//                break;
//            case MotionEvent.ACTION_UP:
////                Log.e("View", "onTouchEvent--->ACTION_UP=" + ev.getY());
//                break;
//            default:
//                break;
//        }
//        return super.onTouchEvent(ev);
//
//    }

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

    private void init() {
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollTop && scrollState == SCROLL_STATE_IDLE && getChildCount() > 0) {
                    int scrollBy;
                    View v = getChildAt(0);
                    int top = v.getTop();
                    if (top < 0) scrollBy = top;
                    else scrollBy = -getChildAt(0).getHeight();
                    if (Math.abs(scrollBy) > 0) smoothScrollBy(scrollBy, 0);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (scrollTop && firstVisibleItem == 0) {
                    scrollTop = false;
                }
            }
        });
    }

}
