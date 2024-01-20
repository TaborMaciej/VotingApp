package com.example.votingapp.Voter.Sejm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.votingapp.R;
//import com.example.votingapp.Voter.Senat.WyborSenatu;

public class PonownePotwierdzenieKodu extends AppCompatActivity {

    public Button Dalej;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponowne_potwierdzenie_kodu);

        Dalej =findViewById(R.id.AgainAcceptCodeBtn);
        NextSenat();
    }
    public void NextSenat()
    {
        Dalej.setOnClickListener(v -> {
            // Intent intent = new Intent(PonownePotwierdzenieKodu.this, WyborSenatu.class);
            // startActivity(intent);
        });
    }
}