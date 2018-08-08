
package com.bignerdranch.android.weightlosstracker;


import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class history_fragment extends Fragment {
    private RecyclerView historyListRecyclerView;
    private historyRecyclerViewAdapter mAdapter;
    View myView;
    private  DBHelper dbHelper;
    Button buttonBack;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dbHelper = new DBHelper(getActivity());

        myView = inflater.inflate(R.layout.fragment_history, container, false);

        List<history> historydata = new ArrayList<history>();
        Cursor cursor = dbHelper.getAllRows();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String weight = cursor.getString(cursor.getColumnIndex("weight"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                Integer id = cursor.getInt(cursor.getColumnIndex("id")); //new

                history data = new history();
                data.setWeight(weight);
                data.setDate(date);
                data.setId(id);

                historydata.add(data);

                cursor.moveToNext();
            }
        }
        cursor.close();


        historyListRecyclerView = (RecyclerView) myView.findViewById(R.id.historylist_recycler_view);
        historyListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new historyRecyclerViewAdapter(getActivity(), historydata);
        historyListRecyclerView.setAdapter(mAdapter);


        buttonBack = (Button) myView.findViewById(R.id.btn_History_back);
        buttonBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new summary_fragment()).commit();
            }
        });





        return myView;


    }



}



