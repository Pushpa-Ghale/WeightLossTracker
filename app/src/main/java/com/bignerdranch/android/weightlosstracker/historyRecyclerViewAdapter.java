package com.bignerdranch.android.weightlosstracker;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class historyRecyclerViewAdapter extends RecyclerView.Adapter<historyRecyclerViewAdapter.ViewHolder> {
    private List<history> mHistoryList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private  DBHelper dbHelper;
    private Context context;

    historyRecyclerViewAdapter(Context context, List<history> data){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mHistoryList = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.history_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        dbHelper = new DBHelper(context);
        history historyData = mHistoryList.get(position);
        holder.myTextViewDate.setText(historyData.getDate().toString());
        holder.myTextViewWeight.setText(historyData.getWeight());
        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int  a = position;

                Integer dataId = (mHistoryList.get(position)).getid();
                dbHelper.deleteRow(dataId);




                FragmentManager fragmentManager =  ((Activity)context).getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new history_fragment()).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextViewWeight;
        TextView myTextViewDate;
        Button delButton;

        ViewHolder(View itemView) {
            super(itemView);
            myTextViewWeight = itemView.findViewById(R.id.history_list_item_weight_text_view);
            myTextViewDate = itemView.findViewById(R.id.history_list_item_date_text_view);
            delButton = itemView.findViewById(R.id.btn_delete);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    history getItem(int id) {
        return mHistoryList.get(id);
    }


    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
