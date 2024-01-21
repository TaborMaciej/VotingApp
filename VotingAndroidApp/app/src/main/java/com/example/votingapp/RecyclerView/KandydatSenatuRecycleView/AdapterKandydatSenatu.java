package com.example.votingapp.RecyclerView.KandydatSenatuRecycleView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.RecyclerView.KandydatSenatuRecycleView.MyViewHolderKandydatSenatu;
import com.example.votingapp.Models.KandydatModel;
import com.example.votingapp.Voter.Sejm.PotwierdzenieWyboru;
import com.example.votingapp.Voter.Senat.PotwierdzenieWyboruSenatora;

import java.util.List;

public class AdapterKandydatSenatu extends RecyclerView.Adapter<MyViewHolderKandydatSenatu>{
    Context context;
    List<KandydatModel> items;
    Bundle extras;


    public AdapterKandydatSenatu(Context context, List<KandydatModel> items, Bundle extras_) {
        this.context = context;
        this.items = items;
        this.extras = extras_;
    }
    @NonNull
    @Override
    public MyViewHolderKandydatSenatu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderKandydatSenatu(LayoutInflater.from(context).inflate(R.layout.kandydat_senatu_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderKandydatSenatu holder, int position) {
        holder.descriptionView.setText(items.get(position).Opis);
        holder.nameView.setText(String.format("%s %s", items.get(position).Imie, items.get(position).Nazwisko));
        holder.imageView1.setImageResource(items.get(position).Zdjecie);
        holder.wyborBtn.setOnClickListener(v ->{
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, PotwierdzenieWyboruSenatora.class);
            extras.putString("ID_KandydatSenat", items.get(position).ID);
            extras.putString("OpisKandydataSenatu", items.get(position).Opis);
            extras.putString("ImieKandydataSenatu", items.get(position).Imie);
            extras.putString("NazwiskoKandydataSenatu", items.get(position).Nazwisko);
            extras.putInt("ZdjecieKandydataSenatu", items.get(position).Zdjecie);
            intent.putExtras(extras);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
