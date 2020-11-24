package com.example.floatlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickLinkView(View v) {
        startActivity(new Intent(this, SlideLinkActivity.class));
    }

    public void onClickNormalView(View v) {
        startActivity(new Intent(this, SlideNormalActivity.class));
    }

}