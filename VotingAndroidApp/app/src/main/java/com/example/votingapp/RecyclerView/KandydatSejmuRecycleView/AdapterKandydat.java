package com.example.votingapp.RecyclerView.KandydatSejmuRecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.Models.KandydatModel;

import java.util.List;

public class AdapterKandydat extends RecyclerView.Adapter<MyViewHolderKandydat>{
    Context context;
    List<KandydatModel> items;

    public AdapterKandydat(Context context, List<KandydatModel> items) {
        this.context = context;
        this.items = items;
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
