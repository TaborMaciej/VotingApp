package com.example.votingapp.Voter.Senat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.votingapp.R;

public class PotwierdzenieWyboruSenatora extends AppCompatActivity {
    Button PowrotKandydaciBtn;
    Button PotwierdzenieBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potwierdzenie_wyboru_senatora);

        Bundle data = this.getIntent().getExtras();
        PowrotKandydaciBtn = findViewById(R.id.PowrotKandydaciSenatuBtn);
        PowrotKandydaciBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PotwierdzenieWyboruSenatora.this, WyborKandydatSenat.class);
            intent.putExtras(data);
            startActivity(intent);
        });
        PotwierdzenieBtn = findViewById(R.id.PotwierdzenieSenatoraBtn);
        PotwierdzenieBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PotwierdzenieWyboruSenatora.this, PonownePotwierdzenieKodu2.class);
            intent.putExtras(data);
            startActivity(intent);
        });

        ((TextView) findViewById(R.id.DescriptionTextView)).setText(data.getString("OpisKandydataSenatu"));
        ((TextView) findViewById(R.id.NameTextView))
                .setText(String.format("%s %s", data.getString("ImieKandydataSenatu"), data.getString("NazwiskoKandydataSenatu")));
        ((ImageView) findViewById(R.id.imageView)).setImageResource(data.getInt("ZdjecieKandydataSenatu"));
    }
}