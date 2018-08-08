package com.bignerdranch.android.weightlosstracker;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class historyList {
    private static historyList sHistoryList;
    private List<history> historyList;

    public historyList(Context context) {
        historyList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            history historyObj = new history();
            historyObj.setDate("ee");
            historyObj.setWeight(Integer.toString(i));

            historyList.add(historyObj);
        }
    }

    public void setsHistoryList(){
        history historyObj = new history();
        historyObj.setDate("new");
        historyObj.setWeight("weight");

        historyList.add(historyObj);
    }

    public List<history> getHistoryList() {return historyList;}

    public static historyList get(Context context) {
        if (sHistoryList == null) {
            sHistoryList = new historyList(context);
        }
        return sHistoryList;
    }



}
