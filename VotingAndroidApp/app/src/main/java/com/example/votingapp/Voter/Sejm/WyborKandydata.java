package com.example.votingapp.Voter.Sejm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.votingapp.DBController.DBController;
import com.example.votingapp.Models.KandydatModel;
import com.example.votingapp.R;
import com.example.votingapp.RecyclerView.KandydatSejmuRecycleView.AdapterKandydat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class WyborKandydata extends AppCompatActivity {

    public RecyclerView recyclerView;
    public Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor_kandydata);
        setupKomitetsView();

        recyclerView = findViewById(R.id.ListaKandydatow);
        backBtn = findViewById(R.id.PowrotBtn);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WyborKandydata.this, WyborListyWyborczej.class);
            intent.putExtras(Objects.requireNonNull(this.getIntent().getExtras()));
            startActivity(intent);
        });

        String idKomitet = this.getIntent().getExtras().getString("ID_komitetu");
        List<KandydatModel> items = DBController.getInstance(this).getKandydaciSejm(idKomitet);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterKandydat(getApplicationContext(),items, this.getIntent().getExtras()));
    }

    private void setupKomitetsView(){
        Bundle data = this.getIntent().getExtras();
        ((ImageView) findViewById(R.id.ZdjeciePartiA)).setImageResource(data.getInt("ID_zdjecia_komitetu"));
        ((TextView) findViewById(R.id.NazwaPartiiA)).setText(data.getString("Nazwa_komitetu"));
        ((ImageView) findViewById(R.id.ZdjeciePartiA1)).setImageResource(data.getInt("ID_zdjecia_komitetu"));
        ((TextView) findViewById(R.id.NazwaPartiiA1)).setText(data.getString("Nazwa_komitetu"));
    }
}