package com.example.tiget.databasetest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {
    View itemView;
    TextView textView;
    Context context;

    public ViewHolder(View itemView) {
        super(itemView);
        this.context = context;

        textView = itemView.findViewById(R.id.textView);
        this.itemView = itemView;





    }

}
