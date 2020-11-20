package com.example.floatlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class SlideFloatView extends LinearLayout {
    private AtomicBoolean show = new AtomicBoolean(false);
    private final int DEFAULT_DURATION = 250;
    private final int SKIP_BOUND = 5;

    private Scroller scroller;
    private int maxHeight;
    private int parrentHeight;
    private int parrentWidth;
    private int defaultHeight;
    private int offset;
    private int cOffset;

    public void setDefaultHeight(int defaultHeight) {
        this.defaultHeight = defaultHeight;
        setDefaultScroll();
    }

    public boolean isShowing() {
        return show.get();
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

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

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
        scroller.startScroll(0, scroller.getFinalY(), 0, offset, DEFAULT_DURATION);
        postInvalidate();
    }

    public SlideFloatView(Context context) {
        super(context);
        init();
    }

    public SlideFloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideFloatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
            defaultHeight = v.getMeasuredHeight();
            setDefaultScroll();
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parrentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parrentHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("SlideFloatView", "onMeasure--->height=" + parrentHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("SlideFloatView", "onSizeChanged");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e("SlideFloatView", "onLayout:" + "l=" + l + "\nt=" + t + "\nr=" + r + "\nb=" + b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("SlideFloatView", "onDraw");
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            Log.e("SlideFloatView", "computeScroll()\n\tcomputeScroll---->" + "\n\tscroller.getCurrX()=" + scroller.getCurrX()
                    + "\n\tscroller.getCurrY()=" + scroller.getCurrY() + "\n\t<----computeScroll");
//            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            View v = getChildAt(0);
            if (v != null) {
                measureChild(v, View.MeasureSpec.makeMeasureSpec(parrentWidth, MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(scroller.getFinalY(), MeasureSpec.EXACTLY));
                v.layout(0, getParrentHeight() - scroller.getCurrY(), v.getMeasuredWidth(), getParrentHeight());
                postInvalidate();
            }
            if (scroller.getFinalY() <= 0) this.show.set(false);
            else this.show.set(true);
        }
    }

    private float startY = -1;
    private float moveY = -1;
    private float upY = -1;
    private float iStartY = -1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.e("ViewGroup", "dispatchTouchEvent--->MotionEvent=" + ev.getAction());

//        Log.e("ViewGroup", "dispatchTouchEvent--->intercept=" + intercept);
        return super.dispatchTouchEvent(ev);
    }

    private boolean isContentViewScrollTop(ListView mContentView) {
        if (mContentView != null && mContentView.getFirstVisiblePosition() == 0) {
            if (mContentView.getChildCount() > 0) {
                View firstView = mContentView.getChildAt(0);
                if (firstView != null && firstView.getTop() == 0) {//listview滚动到顶部了
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.e("ViewGroup", "onInterceptTouchEvent--->MotionEvent=" + ev.getAction());

        View child = getChildAt(0);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isChildView(child, ev)) {
                    iStartY = ev.getY();
                } else {
                    iStartY = -1;
                }
                Log.e("SlideFloatView", "onInterceptTouchEvent--->ACTION_DOWN=" + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                if (iStartY >= 0) {
                    if (iStartY - ev.getY() > 0 && scroller.getFinalY() < maxHeight / 3 * 2) {

                        return true;
                    }

//                    else return super.onInterceptTouchEvent(ev);

//                    if (scroller.getFinalY() < maxHeight) {
//                        return true;
//                    } else {
//                        if (iStartY - ev.getY() < 0 && isContentViewScrollTop((ListView) child)) {
//                            return true;
//                        }
//                        iStartY = ev.getY();
//                    }
                }
                Log.e("SlideFloatView", "onInterceptTouchEvent--->ACTION_MOVE=" + moveY);
//                Log.e("SlideFloatView", "onInterceptTouchEvent--->ACTION_MOVE=" + (moveY - startY));
                break;
            case MotionEvent.ACTION_UP:
                upY = ev.getY();
                Log.e("SlideFloatView", "onInterceptTouchEvent--->ACTION_UP=" + upY);
                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e("SlideFloatView", "onInterceptTouchEvent--->ACTION_CANCEL=" + ev.getY());
//                break;
            default:
                break;
        }
//        Log.e("ViewGroup", "onInterceptTouchEvent--->intercept=" + intercept);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean touch = super.onTouchEvent(ev);
        View child = getChildAt(0);
        if (child != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isChildView(child, ev)) {
                        touch = true;
                        startY = ev.getY();
                        this.cOffset = 0;
                    } else {
                        startY = -1;
                    }
                    Log.e("ViewGroup", "onTouchEvent--->ACTION_DOWN=" + ev.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (startY >= 0) {
                        moveY = ev.getY();
                        if (scroller.getFinalY() + defaultHeight + startY - moveY <= maxHeight) {
                            if (Math.abs(startY - moveY) > 0) {
                                scroller.startScroll(0, scroller.getFinalY(), 0, (int) (startY - moveY), DEFAULT_DURATION);
                                postInvalidate();
                            }
                            if (startY - moveY > 0) {
                                if (scroller.getFinalY() < (offset - defaultHeight)) {
                                    this.cOffset = -scroller.getFinalY() - defaultHeight;
                                } else {
                                    if (startY - moveY > SKIP_BOUND)
                                        this.cOffset = maxHeight - scroller.getFinalY() - defaultHeight;
                                    else
                                        this.cOffset = -scroller.getFinalY() + offset - defaultHeight;
                                }
                            } else if (startY - moveY < 0) {
                                if (scroller.getFinalY() < (offset - defaultHeight)) {
                                    this.cOffset = -scroller.getFinalY() - defaultHeight;
                                } else {
                                    if (Math.abs(startY - moveY) > SKIP_BOUND)
                                        this.cOffset = -scroller.getFinalY() + offset - defaultHeight;
                                    else
                                        this.cOffset = maxHeight - scroller.getFinalY() - defaultHeight;
                                }
                            }
                        } else {
                            this.cOffset = 0;
                            if (Math.abs(maxHeight - defaultHeight - scroller.getFinalY()) > 0) {
                                scroller.startScroll(0, scroller.getFinalY(), 0, maxHeight - defaultHeight - scroller.getFinalY(), DEFAULT_DURATION);
                                postInvalidate();
                            }
                        }
                        startY = moveY;
                    }
//                    Log.e("ViewGroup", "dispatchTouchEvent--->ACTION_MOVE=" + ev.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    if (startY >= 0) {
                        upY = ev.getY();
                        if (Math.abs(cOffset) > 0) slideView(cOffset);
                    }
//                    Log.e("ViewGroup", "dispatchTouchEvent--->ACTION_UP=" + ev.getY());
                    break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e("SlideFloatView", "onInterceptTouchEvent--->ACTION_CANCEL=" + ev.getY());
//                break;
                default:
                    startY = -1;
                    moveY = -1;
                    upY = -1;
                    break;
            }
        }
        return touch;
    }


    private void init() {
        scroller = new Scroller(getContext(), new DecelerateInterpolator());
    }

    private void setDefaultScroll() {
        scroller.startScroll(0, 0, 0, -defaultHeight, 0);
        postInvalidate();
    }

    private boolean isChildView(View child, MotionEvent ev) {
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

}
