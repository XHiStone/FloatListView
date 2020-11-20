package com.example.floatlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    private SlideFloatView slideFloatView;
    private ListView lvContent;
    private AtomicBoolean show = new AtomicBoolean(true);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slideFloatView = findViewById(R.id.slide_float_view);
        lvContent = findViewById(R.id.lv_content);
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            list.add("Android");
        }
        ;
        lvContent.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
//        slideFloatView.setOffsetHeight(400);
    }

    public void onStartClick(View v) {
        slideFloatView.setOffset(slideFloatView.getParrentHeight() / 2);
        slideFloatView.setMaxHeight(slideFloatView.getParrentHeight() / 5 * 4);
        if (!slideFloatView.isShowing()) slideFloatView.show();
        else slideFloatView.hide();
//        slideFloatView.setVisibility(slideFloatView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.e("MainActivity", "dispatchTouchEvent--->MotionEvent=" + ev.getAction());
//        boolean intercept = super.dispatchTouchEvent(ev);
//        Log.e("MainActivity", "dispatchTouchEvent--->intercept=" + intercept);
//        return intercept;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.e("MainActivity", "onTouchEvent--->MotionEvent=" + event.getAction());
//        boolean intercept = super.onTouchEvent(event);
//        Log.e("MainActivity", "onTouchEvent--->intercept=" + intercept);
//        return intercept;
//    }
}