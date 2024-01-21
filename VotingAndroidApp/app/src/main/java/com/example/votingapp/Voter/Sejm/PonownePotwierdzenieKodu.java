package com.example.votingapp.Voter.Sejm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votingapp.DBController.DBController;
import com.example.votingapp.R;
import com.example.votingapp.Voter.Senat.WyborSenatu;

import java.util.Objects;

public class PonownePotwierdzenieKodu extends AppCompatActivity {

    public Button DalejBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponowne_potwierdzenie_kodu);

        Bundle extras = this.getIntent().getExtras();
        DalejBtn = findViewById(R.id.button);
        EditText input = (EditText) findViewById(R.id.editTextCode);
        DalejBtn.setOnClickListener(v -> {
            if(!Objects.equals(extras.getString("Code"), input.getText().toString())){
                Intent intent = new Intent(PonownePotwierdzenieKodu.this, PotwierdzenieWyboru.class);
                intent.putExtras(extras);
                Toast toast = Toast.makeText(this, "Podano zły kod", Toast.LENGTH_LONG);
                toast.show();
                startActivity(intent);
                return;
            }
            String guidSejm = extras.getString("ID_KandydatSejm");
            DBController.getInstance(this).changeCodeSejm(extras.getString("Code"), guidSejm);
            Intent intent = new Intent(PonownePotwierdzenieKodu.this, WyborSenatu.class);
            intent.putExtras(extras);
            startActivity(intent);
        });
    }
}