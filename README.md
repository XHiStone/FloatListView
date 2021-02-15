# FloatListView
先看效果图<br>
<img src="https://img-blog.csdnimg.cn/20201124175500815.gif" width="360" height="640" alt="微信小程序"/><br>
最近项目中用到了可拖拽滑动底部浮层，于是乎就写了这个控件，代码不多很简单用到的都是一些基础知识并开源出来供大家参考，感兴趣的可以看看。<br>
基础知识用到了View的生命周期和View事件分发机制完全可以实现底部滑动浮层控件。<br>
### xml中使用：<br>
```
    <com.example.floatlistview.slide.SlideNormalView
        android:id="@+id/slide_float_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="bottom"
        android:orientation="vertical">
 
        <ListView
            android:id="@+id/lv_content"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/purple_200"
            android:scrollbars="none" />
 
    </com.example.floatlistview.slide.SlideNormalView>
```
### Activity使用：
```
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
 
public class SlideNormalActivity extends AppCompatActivity {
    private BaseSlideView slideFloatView;
    private ListView lvContent;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_normal);
        slideFloatView = findViewById(R.id.slide_float_view);
        lvContent = findViewById(R.id.lv_content);
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            list.add("Android");
        }
        lvContent.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
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
```
