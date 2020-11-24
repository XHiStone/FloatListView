package com.example.floatlistview.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.floatlistview.slide.base.BaseSlideListview;
import com.example.floatlistview.slide.base.BaseSlideView;

/**
 * Author: xie
 * Date: 2020/11/23
 * Desc: 支持listview无缝衔接滑动，需继承{@link BaseSlideListview}
 **/
public class SlideLinkView extends BaseSlideView {

    public SlideLinkView(Context context) {
        super(context);
    }

    public SlideLinkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideLinkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        super.setMaxHeight(maxHeight);
        if (getChildAt(0) instanceof BaseSlideListview) {
            ((BaseSlideListview) getChildAt(0)).setMaxHeight(maxHeight);
        }
    }

    @Override
    public void show() {
        if (isShowing()) scrollToTop();
        super.show();
    }

    @Override
    public void hide() {
        if (isShowing()) scrollToTop();
        super.hide();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean intercept = super.dispatchTouchEvent(ev);
        View child = getChildAt(0);
        if (child != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isChildView(child, ev)) {
                        setStartY(ev.getY());
                        setcOffset(0);
                    } else {
                        setStartY(-1);
                    }
//                    Log.e("ViewGroup", "dispatchTouchEvent--->ACTION_DOWN=" + ev.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (getStartY() >= 0 && !intercept) {
                        if (getScroller().getFinalY() + getDefaultHeight() + getStartY() - ev.getY() <= getMaxHeight()) {
                            if (Math.abs(getStartY() - ev.getY()) > 0) {
                                getScroller().startScroll(0, getScroller().getFinalY(), 0, (int) (getStartY() - ev.getY()), 0);
                                postInvalidate();
                            }
                            if (getStartY() - ev.getY() > 0) {
                                if (getScroller().getFinalY() < (getOffset() - getDefaultHeight())) {
                                    setcOffset(-getScroller().getFinalY() - getDefaultHeight());
                                } else {
                                    if (getStartY() - ev.getY() > getSKIP_BOUND())
                                        setcOffset(getMaxHeight() - getScroller().getFinalY() - getDefaultHeight());
                                    else
                                        setcOffset(-getScroller().getFinalY() + getOffset() - getDefaultHeight());
                                }
                            } else if (getStartY() - ev.getY() < 0) {
                                if (getScroller().getFinalY() < (getOffset() - getDefaultHeight())) {
                                    setcOffset(-getScroller().getFinalY() - getDefaultHeight());
                                } else {
                                    if (Math.abs(getStartY() - ev.getY()) > getSKIP_BOUND())
                                        setcOffset(-getScroller().getFinalY() + getOffset() - getDefaultHeight());
                                    else
                                        setcOffset(getMaxHeight() - getScroller().getFinalY() - getDefaultHeight());
                                }
                            }
                        } else {
                            setcOffset(0);
                            if (Math.abs(getMaxHeight() - getDefaultHeight() - getScroller().getFinalY()) > 0) {
                                getScroller().startScroll(0, getScroller().getFinalY(), 0, getMaxHeight() - getDefaultHeight() - getScroller().getFinalY(), 0);
                                postInvalidate();
                            }
                        }
                    }
                    setStartY(ev.getY());
//                    Log.e("ViewGroup", "dispatchTouchEvent--->ACTION_MOVE=" + ev.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    if (getStartY() >= 0) {
                        if (Math.abs(getcOffset()) > 0) slideView(getcOffset());
                    }
//                    Log.e("ViewGroup", "dispatchTouchEvent--->ACTION_UP=" + ev.getY());
                    break;
                default:
                    setStartY(-1);
                    break;
            }
        }
        return intercept;
    }

    private void scrollToTop() {
        if (getChildAt(0) instanceof BaseSlideListview && ((BaseSlideListview) getChildAt(0)).getCount() > 0) {
            BaseSlideListview listview = (BaseSlideListview) getChildAt(0);
            listview.setScrollTop();
        }
    }

}
