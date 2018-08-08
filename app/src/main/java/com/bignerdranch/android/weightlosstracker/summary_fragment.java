package com.bignerdranch.android.weightlosstracker;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class summary_fragment extends Fragment{
    private  DBHelper dbHelper;
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dbHelper = new DBHelper(getActivity());
        myView = inflater.inflate(R.layout.fragment_summary, container, false);


        int dbDataCount = dbHelper.getAllRows().getCount();
        Cursor cursor = dbHelper.getAllRows();
        if (cursor.moveToLast()){
                String weight = cursor.getString(cursor.getColumnIndex("weight"));
                String date = cursor.getString(cursor.getColumnIndex("date"));

                TextView tvLatestWeight = (TextView) myView.findViewById(R.id.tvLatestWeight);
                tvLatestWeight.setText(weight + " lbs");


                TextView tvLatestDate = (TextView) myView.findViewById(R.id.tvLatestDate);
                tvLatestDate.setText(date);

                cursor.moveToNext();
        }
        cursor.close();


        TextView tvLatestWeightLoss = (TextView) myView.findViewById(R.id.tvWeightLoss);
        TextView tvLatestWeightLossText = (TextView) myView.findViewById(R.id.tvWeigtLossText);

        if (Double.parseDouble(dbHelper.getLatestWeightDiff()) > 0){
            tvLatestWeightLossText.setText("Latest Weight Gained");
        }
        else
        {
            tvLatestWeightLossText.setText("Latest Weight Lost");
        }
        tvLatestWeightLoss.setText(dbHelper.getLatestWeightDiff());

        return myView;
    }
}
