package com.example.votingapp.RecyclerView.KandydatSejmuRecycleView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.Voter.Sejm.PotwierdzenieWyboru;
import com.example.votingapp.Voter.Sejm.WyborKandydata;

public class MyViewHolderKandydat extends RecyclerView.ViewHolder {

    ImageView imageView1;
    TextView nameView,descriptionView;
    Button wyborBtn;

    public MyViewHolderKandydat(@NonNull View itemView) {
        super(itemView);
        imageView1 = itemView.findViewById(R.id.ZdjecieKandydata);
        nameView = itemView.findViewById(R.id.ImieTextView);
        descriptionView = itemView.findViewById(R.id.OpisTextView);
        wyborBtn = itemView.findViewById(R.id.WyborBtn);
    }
}
