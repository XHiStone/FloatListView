package com.example.floatlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MineView extends androidx.appcompat.widget.AppCompatTextView {

    public MineView(@NonNull Context context) {
        super(context);
    }

    public MineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MineView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
