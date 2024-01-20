package com.example.votingapp.Voter.Senat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.votingapp.R;
import com.example.votingapp.RecyclerView.RecyclerViewInterface;
import com.example.votingapp.DBController.DBController;
import com.example.votingapp.Models.KomitetModel;
import com.example.votingapp.RecyclerView.SejmRecycleView.AdapterSejm;
import com.example.votingapp.Voter.Sejm.WyborKandydata;
import com.example.votingapp.Voter.Sejm.WyborListyWyborczej;
import java.util.List;

public class WyborSenatu extends AppCompatActivity  implements RecyclerViewInterface {

    private List<KomitetModel> komitetLista;
    private Bundle extras;
    private RecyclerView listaPartii;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor_senatu);
        listaPartii= findViewById(R.id.WyborSenatuRecycle);
        extras = this.getIntent().getExtras();
        komitetLista = DBController.getInstance(this).getKomitety();

        listaPartii.setLayoutManager(new LinearLayoutManager(this));
        listaPartii.setAdapter(new AdapterSejm(getApplicationContext(), komitetLista,this));
    }

    @Override
    public void OnItemCLick(int position) {
        Intent intent = new Intent(this, WyborKandydatSenat.class);
        extras.putString("ID_komitetuSenat", komitetLista.get(position).ID);
        extras.putString("Nazwa_komitetuSenat", komitetLista.get(position).Nazwa);
        extras.putInt("ID_zdjecia_komitetuSenat", komitetLista.get(position).LogoNazwa);
        extras.putInt("nr_listy_komitetuSenat", komitetLista.get(position).Nrlisty);
        intent.putExtras(extras);
        startActivity(intent);
    }
}