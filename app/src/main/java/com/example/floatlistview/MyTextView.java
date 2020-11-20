package com.example.floatlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class MyTextView extends AppCompatTextView {
    public MyTextView(@NonNull Context context) {
        super(context);
    }

    public MyTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("childView", "dispatchTouchEvent--->MotionEvent=" + event.getAction());
        boolean intercept = super.dispatchTouchEvent(event);
        Log.e("childView", "dispatchTouchEvent--->intercept=" + intercept);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("childView", "onTouchEvent--->MotionEvent=" + event.getAction());
        boolean intercept = super.onTouchEvent(event);
        Log.e("childView", "onTouchEvent--->intercept=" + intercept);
        return intercept;
    }
}
