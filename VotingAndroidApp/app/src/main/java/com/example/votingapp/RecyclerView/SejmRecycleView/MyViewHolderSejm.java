package com.example.votingapp.RecyclerView.SejmRecycleView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;

public class MyViewHolderSejm extends RecyclerView.ViewHolder {
    public ImageView imageView1,imageView2;
    public TextView nameView;
    public MyViewHolderSejm(@NonNull View itemView) {
        super(itemView);
        imageView1 = itemView.findViewById(R.id.ZdjecieParti);
        imageView2 = itemView.findViewById(R.id.ZdjecieParti2);
        nameView = itemView.findViewById(R.id.NazwaPartii);
    }
}
