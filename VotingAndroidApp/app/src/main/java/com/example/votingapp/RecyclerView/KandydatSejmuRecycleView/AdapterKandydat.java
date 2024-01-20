package com.example.votingapp.RecyclerView.KandydatSejmuRecycleView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.Models.KandydatModel;
import com.example.votingapp.Voter.Sejm.PotwierdzenieWyboru;

import java.util.List;

public class AdapterKandydat extends RecyclerView.Adapter<MyViewHolderKandydat>{
    Context context;
    List<KandydatModel> items;
    Bundle extras;

    public AdapterKandydat(Context context, List<KandydatModel> items, Bundle extras_) {
        this.context = context;
        this.items = items;
        this.extras = extras_;
    }

    @NonNull
    @Override
    public MyViewHolderKandydat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderKandydat(LayoutInflater.from(context).inflate(R.layout.kandydat_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderKandydat holder, int position) {
        holder.descriptionView.setText(items.get(position).Opis);
        holder.nameView.setText(items.get(position).Imie);
        holder.surnameView.setText(items.get(position).Nazwisko);
        holder.imageView1.setImageResource(items.get(position).Zdjecie);
        holder.wyborBtn.setOnClickListener(v ->{
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, PotwierdzenieWyboru.class);
            extras.putString("OpisKandydataSejmu", items.get(position).Opis);
            extras.putString("ImieKandydataSejmu", items.get(position).Imie);
            extras.putString("NazwiskoKandydataSejmu", items.get(position).Nazwisko);
            extras.putInt("ZdjecieKandydataSejmu", items.get(position).Zdjecie);
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
