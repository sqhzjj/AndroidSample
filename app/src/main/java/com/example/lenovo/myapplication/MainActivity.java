package com.example.lenovo.myapplication;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SearchHistoryHelper mHelper;
    private EditText put;
    private ListView lv;
    private Button tv_add;
    private List<String> historyData = new ArrayList<>();
    private MyAdapter adapter;
    private Button btn_pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initData();

    }

    private void initView() {
        tv_add = (Button) findViewById(R.id.tv_add);
        put = (EditText) findViewById(R.id.et_put);
        lv = (ListView) findViewById(R.id.lv);
        btn_pop = (Button) findViewById(R.id.btn_pop);
        tv_add.setOnClickListener(this);
        btn_pop.setOnClickListener(this);
    }

    private void initEvent() {
        mHelper = new SearchHistoryHelper(this);
        adapter = new MyAdapter(historyData);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                startSearch();
                break;
            case R.id.btn_pop:
                View view = LayoutInflater.from(this).inflate(R.layout.pop_item, null);
                PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setAnimationStyle(R.style.popupAnimation);
                popupWindow.showAtLocation(view, Gravity.RIGHT,0,0);
                break;
        }
    }

    private void startSearch() {
        String trim = put.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            return;
        }
        mHelper.saveHistoryData(historyData,trim);
        List<String> historyList = mHelper.getHistoryData();
        historyData.clear();
        historyData.addAll(historyList);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        List<String> historyList = mHelper.getHistoryData();
        historyData.addAll(historyList);
        adapter.notifyDataSetChanged();
    }

    public class MyAdapter extends BaseAdapter {
        private List<String> historyData;

        public MyAdapter(List<String> historyData) {
            this.historyData = historyData;
        }

        @Override
        public int getCount() {
            return historyData.size() == 0 ? 0 : historyData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, null);
                viewHolder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_text.setText(historyData.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView tv_text;
        }
    }
}
