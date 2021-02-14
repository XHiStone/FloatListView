package com.example.floatlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.floatlistview.slide.base.BaseSlideView;

import java.util.ArrayList;

public class SlideLinkActivity extends AppCompatActivity {
    private BaseSlideView slideFloatView;
    private ListView lvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_link);
        slideFloatView = findViewById(R.id.slide_float_view);
        lvContent = findViewById(R.id.lv_content);
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            list.add("Android");
        }
        lvContent.setAdapter(new TextAdapter<String>(this, list));
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("MainActivity", "onItemClick--->" + position);
            }
        });
    }

    public void onClickStart(View view) {
        slideFloatView.setOffset(slideFloatView.getParrentHeight() / 2);
        slideFloatView.setMaxHeight(slideFloatView.getParrentHeight() / 5 * 4);
        if (!slideFloatView.isShowing())
            slideFloatView.show();
        else slideFloatView.hide();
    }
}