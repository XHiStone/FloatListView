package com.example.floatlistview.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floatlistview.slide.base.BaseSlideView;

/**
 * Author: xie
 * Date: 2020/11/23
 * Desc: 不支持ScrollView
 **/
public class SlideNormalView extends BaseSlideView {
    public SlideNormalView(Context context) {
        super(context);
    }

    public SlideNormalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideNormalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void show() {
        if (getOffset() == 0 && getChildCount() > 0) {
            View child = getChildAt(0);
            measureChild(child, View.MeasureSpec.makeMeasureSpec(getParrentWidth(), MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            setOffset(child.getMeasuredHeight());
        }
        if (getMaxHeight() == 0) setMaxHeight(getOffset());
        super.show();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        View child = getChildAt(0);
        if (child != null)
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
//                Log.e("ViewGroup", "onInterceptTouchEvent--->ACTION_DOWN=" + ev.getY());
                    if (isChildView(child, ev)) {
                        setcOffset(0);
                        setStartY(ev.getY());
                    } else {
                        setStartY(-1);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
//                Log.e("ViewGroup", "onInterceptTouchEvent--->ACTION_MOVE=" + ev.getY());
                    if (getStartY() - ev.getY() > 0 && getScroller().getFinalY() < getMaxHeight()) {
                        intercept = true;
                    } else if (getStartY() - ev.getY() < 0) {
                        if (child instanceof ListView) {
                            intercept = isContentViewScrollTop((ListView) child);
                        } else if (child instanceof RecyclerView) {
                            intercept = isRecyclerViewScrollTop((RecyclerView) child);
                        } else if (getScroller().getFinalY() == getMaxHeight()) {
                            intercept = true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
//                Log.e("ViewGroup", "onInterceptTouchEvent--->ACTION_UP=" + ev.getY());
                    break;
                default:
                    break;
            }
//        Log.e("ViewGroup", "onInterceptTouchEvent--->intercept=" + intercept);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        View child = getChildAt(0);
        if (child != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
//                    Log.e("ViewGroup", "onTouchEvent--->ACTION_DOWN=" + ev.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (getStartY() >= 0) {
                        if (getScroller().getFinalY()  + getStartY() - ev.getY() <= getMaxHeight()) {
                            if (Math.abs(getStartY() - ev.getY()) > 0) {
                                getScroller().startScroll(0, getScroller().getFinalY(), 0, (int) (getStartY() - ev.getY()), getDEFAULT_DURATION());
                                postInvalidate();
                            }
                            if (getStartY() - ev.getY() > 0) {
                                if (getScroller().getFinalY() < (getOffset() )) {
                                    setcOffset(-getScroller().getFinalY() );
                                } else {
                                    if (getStartY() - ev.getY() > getSKIP_BOUND())
                                        setcOffset(getMaxHeight() - getScroller().getFinalY() );
                                    else
                                        setcOffset(-getScroller().getFinalY() + getOffset() );
                                }
                            } else if (getStartY() - ev.getY() < 0) {
                                if (getScroller().getFinalY() < (getOffset())) {
                                    setcOffset(-getScroller().getFinalY());
                                } else {
                                    if (Math.abs(getStartY() - ev.getY()) > getSKIP_BOUND())
                                        setcOffset(-getScroller().getFinalY() + getOffset());
                                    else
                                        setcOffset(getMaxHeight() - getScroller().getFinalY() );
                                }
                            }
                        } else {
                            setcOffset(0);
                            if (Math.abs(getMaxHeight()- getScroller().getFinalY()) > 0) {
                                getScroller().startScroll(0, getScroller().getFinalY(), 0, getMaxHeight()  - getScroller().getFinalY(), getDEFAULT_DURATION());
                                postInvalidate();
                            }
                        }
                    }
                    setStartY(ev.getY());
//                    Log.e("ViewGroup", "onTouchEvent--->ACTION_MOVE=" + ev.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    if (getStartY() >= 0) {
                        if (Math.abs(getcOffset()) > 0) slideView(getcOffset());
                    }
//                    Log.e("ViewGroup", "onTouchEvent--->ACTION_UP=" + ev.getY());
                    break;
                default:
                    setStartY(-1);
                    break;
            }
        }
        boolean touch = super.onTouchEvent(ev);
//        Log.e("ViewGroup", "onTouchEvent--->intercept=" + touch);
        return touch;
    }

    private boolean isContentViewScrollTop(ListView mContentView) {
        if (mContentView != null && mContentView.getFirstVisiblePosition() == 0) {
            if (mContentView.getChildCount() > 0) {
                View firstView = mContentView.getChildAt(0);
                //listview滚动到顶部了
                return firstView != null && firstView.getTop() == 0;
            } else {
                return true;
            }
        }
        return false;
    }


    private boolean isRecyclerViewScrollTop(RecyclerView child) {
        final int offset = child.computeVerticalScrollOffset();
        final int range = child.computeVerticalScrollRange() - child.computeVerticalScrollExtent();
        if (range == 0) return false;
        return offset > 0;
    }
}
