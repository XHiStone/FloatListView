package com.example.floatlistview.slide.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class BaseSlideView extends LinearLayout {
    private final AtomicBoolean show = new AtomicBoolean(false);
    private final int DEFAULT_DURATION = 250;
    private final int SKIP_BOUND = 10;
    private Scroller scroller;

    private int maxHeight;
    private int parrentHeight;
    private int parrentWidth;
    private int defaultHeight;
    private int offset;
    private int cOffset;

    private float startY = -1;

    public void show() {
        if (!isShowing()) {
            this.cOffset = offset;
            slideView(cOffset);
        }
    }

    public void hide() {
        if (isShowing()) {
            this.cOffset = -scroller.getFinalY() - defaultHeight;
            slideView(cOffset);
        }
    }

    public void slideView(int offset) {
        this.scroller.startScroll(0, scroller.getFinalY(), 0, offset, DEFAULT_DURATION);
        postInvalidate();
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public boolean isShowing() {
        return show.get();
    }

    public void setShow(boolean show) {
        this.show.set(show);
    }

    public int getDEFAULT_DURATION() {
        return DEFAULT_DURATION;
    }

    public int getSKIP_BOUND() {
        return SKIP_BOUND;
    }

    public Scroller getScroller() {
        return scroller;
    }

    public void setScroller(Scroller scroller) {
        this.scroller = scroller;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getParrentHeight() {
        return parrentHeight;
    }

    public void setParrentHeight(int parrentHeight) {
        this.parrentHeight = parrentHeight;
    }

    public int getParrentWidth() {
        return parrentWidth;
    }

    public void setParrentWidth(int parrentWidth) {
        this.parrentWidth = parrentWidth;
    }

    public int getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(int defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getcOffset() {
        return cOffset;
    }

    public void setcOffset(int cOffset) {
        this.cOffset = cOffset;
    }

    public BaseSlideView(Context context) {
        super(context);
        init();
    }

    public BaseSlideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseSlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View v = getChildAt(0);
        if (v != null) {
            measureChild(v, View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            this.defaultHeight = v.getMeasuredHeight();
            setDefaultScroll();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.parrentWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.parrentHeight = MeasureSpec.getSize(heightMeasureSpec);
//        Log.e("ViewGroup", "onMeasure--->height=" + parrentHeight);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
//            Log.e("ViewGroup", "computeScroll()\n\tcomputeScroll---->" + "\n\tscroller.getCurrX()=" + scroller.getCurrX()
//                    + "\n\tscroller.getCurrY()=" + scroller.getCurrY() + "\n\t<----computeScroll");
//            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            View v = getChildAt(0);
            if (v != null) {
                measureChild(v, View.MeasureSpec.makeMeasureSpec(parrentWidth, MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(scroller.getFinalY(), MeasureSpec.EXACTLY));
                v.layout(0, getParrentHeight() - scroller.getCurrY(), v.getMeasuredWidth(), getParrentHeight());
                postInvalidate();
            }
            this.show.set(scroller.getFinalY() > 0);
        }
    }

    protected boolean isChildView(View child, MotionEvent ev) {
        if (child != null) {
            int[] parentLocation = new int[2];
            int[] childLocation = new int[2];
            getLocationInWindow(parentLocation);
            child.getLocationInWindow(childLocation);
            int left = childLocation[0], top = childLocation[1], right = left + child.getWidth(), bottom = top + child.getHeight();
            float offsetX = ev.getX() + parentLocation[0];
            float offsetY = ev.getY() + parentLocation[1];
            return offsetX > left && offsetX < right && offsetY > top && offsetY < bottom;
        }
        return false;
    }

    private void init() {
        this.scroller = new Scroller(getContext(), new DecelerateInterpolator());
    }

    private void setDefaultScroll() {
        this.scroller.startScroll(0, 0, 0, -defaultHeight, 0);
        postInvalidate();
    }
}
