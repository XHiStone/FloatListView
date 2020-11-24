package com.example.floatlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.floatlistview.slide.SlideLinkView;
import com.example.floatlistview.slide.SlideNormalView;
import com.example.floatlistview.slide.base.BaseSlideView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    private BaseSlideView slideFloatView;
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
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("MainActivity", "onItemClick--->" + position);
            }
        });
    }

    public void onStartClick(View v) {
        slideFloatView.setOffset(slideFloatView.getParrentHeight() / 2);
        slideFloatView.setMaxHeight(slideFloatView.getParrentHeight() / 5 * 4);
        if (!slideFloatView.isShowing())
            slideFloatView.show();
        else slideFloatView.hide();
//        slideFloatView.setVisibility(slideFloatView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

}