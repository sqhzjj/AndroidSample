package com.example.lenovo.myapplication;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/11/17.
 */

public class SearchHistoryHelper {

    public static final String SEARCH_HISTORY_KEY = "search_history_key";
    public static final int MAX_HISTORY_COUNT = 10;

    private Context mContext;

    public SearchHistoryHelper(Context context) {
        this.mContext = context;
    }

    public void saveHistoryData(List<String> historyList,String text) {
        if (historyList == null) {
            return;
        }
        int index = -1;
        for (int i = 0; i < historyList.size(); i++) {
            String s = historyList.get(i);
            if (s.equals(text)){
                index = i;
                break;
            }
        }
        if (index != -1){
            historyList.remove(index);
        }
        historyList.add(0,text);
        if (historyList.size() > MAX_HISTORY_COUNT) {
            historyList = historyList.subList(0,10);
        }
        StringBuffer buffer = new StringBuffer();
        for (int i=0; i<historyList.size(); i++) {
            String data = historyList.get(i);
            if (i == historyList.size() - 1) {
                buffer.append(data);
            } else {
                buffer.append(data).append("\\|");
            }
        }
        SharedPreferenceHelper.setSharedPreferenceString(mContext, SEARCH_HISTORY_KEY, buffer.toString());
    }

    public List<String> getHistoryData() {
        List<String> historyList = new ArrayList<>();
        String stringData = SharedPreferenceHelper.getSharedPreferenceString(mContext, SEARCH_HISTORY_KEY, null);
        if (!TextUtils.isEmpty(stringData)) {
            String[] split = stringData.split("\\|");
            if (split != null && split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    historyList.add(split[i]);
                }
            }
        }
        return historyList;
    }

//    public void addHistorData(List<String> historyList) {
//        if (historyList == null) return;
//
//    }
//    public void addHistorData(String data, List<String> historyList) {
//        if (historyList == null || TextUtils.isEmpty(data)) return;
//        historyList.add(0, data);
//
//    }
}
