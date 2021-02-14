package com.example.floatlistview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class TextAdapter<T> extends BaseAdapter {
    private LayoutInflater mInflater;

    private List<T> mObjects = new ArrayList<>();

    public TextAdapter(Context context, List<T> objects) {
        this.mInflater = LayoutInflater.from(context);
        mObjects = objects;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        ViewHoulder viewHoulder;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_text_layout, parent, false);
            viewHoulder = new ViewHoulder(view);
            view.setTag(viewHoulder);
        } else {
            view = convertView;
            viewHoulder = (ViewHoulder) view.getTag();
        }
        if (getItem(position) instanceof String) viewHoulder.textView.setText((String) getItem(position));

        return view;
    }

    static class ViewHoulder {
        MineView textView;

        public ViewHoulder(View view) {
            this.textView = view.findViewById(R.id.tv_value);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("tag","MineView");
                }
            });
        }
    }
}

