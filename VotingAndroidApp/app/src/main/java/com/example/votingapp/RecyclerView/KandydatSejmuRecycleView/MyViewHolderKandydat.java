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
    TextView nameView,surnameView,descriptionView;
    Button wyborBtn;

    public MyViewHolderKandydat(@NonNull View itemView) {
        super(itemView);
        imageView1 = itemView.findViewById(R.id.ZdjecieKandydata);
        nameView = itemView.findViewById(R.id.ImieTextView);
        surnameView = itemView.findViewById(R.id.NazwiskoTextView);
        descriptionView = itemView.findViewById(R.id.OpisTextView);
        wyborBtn = itemView.findViewById(R.id.WyborBtn);
/*
        itemView.findViewById(R.id.WyborBtn).setOnClickListener(v -> {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, PotwierdzenieWyboru.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
*/
    }
}
