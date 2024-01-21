package com.example.votingapp.Voter.Senat;

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
import com.example.votingapp.R;
import com.example.votingapp.RecyclerView.KandydatSenatuRecycleView.AdapterKandydatSenatu;
import com.example.votingapp.Models.KandydatModel;

import java.util.ArrayList;
import java.util.List;

public class WyborKandydatSenat extends AppCompatActivity {

    RecyclerView recyclerView;
    Button PowrotBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor_kandydat_senat);
        setupKomitetsView();
        Bundle extras = this.getIntent().getExtras();
        recyclerView = findViewById(R.id.ListaKandydatow);
        PowrotBtn = findViewById(R.id.PowrotBtn);
        PowrotBtn.setOnClickListener(v -> {
            Intent intent = new Intent(WyborKandydatSenat.this, WyborSenatu.class);
            intent.putExtras(extras);
            startActivity(intent);
        });
        List<KandydatModel> items = DBController.getInstance(this).getKandydaciSenat(extras.getString("ID_komitetuSenat"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterKandydatSenatu(getApplicationContext(), items, extras));


    }
    private void setupKomitetsView(){
        Bundle data = this.getIntent().getExtras();
        ((ImageView) findViewById(R.id.ZdjeciePartiA)).setImageResource(data.getInt("ID_zdjecia_komitetuSenat"));
        ((TextView) findViewById(R.id.NazwaPartiiA)).setText(data.getString("Nazwa_komitetuSenat"));
        ((ImageView) findViewById(R.id.ZdjeciePartiA1)).setImageResource(data.getInt("ID_zdjecia_komitetuSenat"));
        ((TextView) findViewById(R.id.NazwaPartiiA1)).setText(data.getString("Nazwa_komitetuSenat"));
    }
}