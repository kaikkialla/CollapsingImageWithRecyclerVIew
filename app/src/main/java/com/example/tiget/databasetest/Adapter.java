package com.example.tiget.databasetest;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder>{
    MainActivity activity;
    private List<StringConstructor> mStrings = new ArrayList<>(); // в mAlarms храним данные

    public Adapter(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.row, parent, false );
        ViewHolder vh = new ViewHolder(v);




        return vh;

    }


    //Заполняем элементы будильника
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final StringConstructor stringConstructor = mStrings.get(position);
        viewHolder.textView.setText(stringConstructor.getString() + "Id: " + stringConstructor.getId());
    }


    @Override
    public int getItemCount() {
        return mStrings.size();

    }


    /**
     * Заполняет внутренний массив будильников массивом из параметров.
     */
    public void swap(List<StringConstructor> stringConstructor) {
        if (stringConstructor != null) {
            mStrings.clear();
            mStrings.addAll(stringConstructor);

            notifyDataSetChanged();
        }
    }




}