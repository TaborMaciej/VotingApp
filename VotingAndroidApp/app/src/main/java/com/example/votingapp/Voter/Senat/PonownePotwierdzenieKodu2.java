package com.example.votingapp.Voter.Senat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votingapp.DBController.DBController;
import com.example.votingapp.MainActivity;
import com.example.votingapp.R;
import com.example.votingapp.Voter.PodziekowanieIZablokowanie;
import com.example.votingapp.Voter.Sejm.PotwierdzenieWyboru;

import java.util.Objects;

public class PonownePotwierdzenieKodu2 extends AppCompatActivity {
    public Button DalejBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potwierdzenie_kodu2);

        Bundle extras = this.getIntent().getExtras();
        DalejBtn = findViewById(R.id.button);
        EditText input = (EditText) findViewById(R.id.editTextCode);
        DalejBtn.setOnClickListener(v -> {
            if(!Objects.equals(extras.getString("Code"), input.getText().toString())){
                Intent intent = new Intent(PonownePotwierdzenieKodu2.this, PotwierdzenieWyboruSenatora.class);
                intent.putExtras(extras);
                Toast toast = Toast.makeText(this, "Podano zły kod", Toast.LENGTH_LONG);
                toast.show();
                startActivity(intent);
                return;
            }
            String guidSejm = extras.getString("ID_KandydatSenat");
            DBController.getInstance(this).changeCodeSenat(extras.getString("Code"), guidSejm);
            DBController.getInstance(this).useCode(extras.getString("Code"));
            Intent intent = new Intent(PonownePotwierdzenieKodu2.this, PodziekowanieIZablokowanie.class);
            intent.putExtras(extras);
            startActivity(intent);
        });
    }
}