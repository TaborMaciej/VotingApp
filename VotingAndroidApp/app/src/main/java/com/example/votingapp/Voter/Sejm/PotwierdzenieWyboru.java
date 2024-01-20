package com.example.votingapp.Voter.Sejm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.votingapp.R;

public class PotwierdzenieWyboru extends AppCompatActivity {

    Button PowrotKandydaciBtn;
    Button PotwierdzenieBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potwierdzenie_wyboru);

        Bundle data = this.getIntent().getExtras();
        PowrotKandydaciBtn = findViewById(R.id.PowrotKandydaciBtn);
        PowrotKandydaciBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PotwierdzenieWyboru.this, WyborKandydata.class);
            intent.putExtras(data);
            startActivity(intent);
        });

        PotwierdzenieBtn = findViewById(R.id.PotwierdzenieBtn);
        PotwierdzenieBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PotwierdzenieWyboru.this, PonownePotwierdzenieKodu.class);
            intent.putExtras(data);
            startActivity(intent);
        });

        ((TextView) findViewById(R.id.DescriptionTextView)).setText(data.getString("OpisKandydataSejmu"));
        ((TextView) findViewById(R.id.NameTextView)).setText(data.getString("ImieKandydataSejmu"));
        ((TextView) findViewById(R.id.SurnameTextView)).setText(data.getString("NazwiskoKandydataSejmu"));
        ((ImageView) findViewById(R.id.imageView)).setImageResource(data.getInt("ZdjecieKandydataSejmu"));
    }
}