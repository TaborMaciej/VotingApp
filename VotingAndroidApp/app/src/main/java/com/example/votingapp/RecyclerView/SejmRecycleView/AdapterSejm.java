package com.example.votingapp.RecyclerView.SejmRecycleView;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.Models.KomitetModel;
import com.example.votingapp.RecyclerView.RecyclerViewInterface;

import java.util.List;

public class AdapterSejm extends RecyclerView.Adapter<MyViewHolderSejm> {

    private  final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<KomitetModel> komitetList;

    public AdapterSejm(Context context, List<KomitetModel> komitetList_, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        komitetList = komitetList_;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolderSejm onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("AdapterSejm", "onCreateViewHolder called"); // Add this line for logging
        View itemView = LayoutInflater.from(context).inflate(R.layout.partia_view, parent, false);
        final MyViewHolderSejm viewHolder = new MyViewHolderSejm(itemView);
        itemView.setOnClickListener(v -> {
            int pos = viewHolder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                recyclerViewInterface.OnItemCLick(pos);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderSejm holder, int position) {
        Log.d("KomitetName" + position, komitetList.get(position).Nazwa);
        holder.nameView.setText(komitetList.get(position).Nazwa);
        holder.imageView1.setImageResource(komitetList.get(position).LogoNazwa);
        holder.imageView2.setImageResource(komitetList.get(position).LogoNazwa);
    }

    @Override
    public int getItemCount() {
        return komitetList.size();
    }
}
