package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.votingapp.DBController.DBController;
import com.example.votingapp.Models.KandydatModel;
import com.example.votingapp.Models.KomitetModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class AdminPanelActivity extends AppCompatActivity {

    Button endVoteBtn, syncBtn, backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        endVoteBtn = findViewById(R.id.EndVoteBtn);
        syncBtn = findViewById(R.id.SyncBtn);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v ->{
            Intent intent = new Intent(AdminPanelActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });

        syncBtn.setOnClickListener(v ->{
            new SychronizeDBTask().execute();
        });

        endVoteBtn.setOnClickListener(v ->{
            new StopVote().execute();
        });
    }

    class SychronizeDBTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params){
            try {
                URL url = new URL("https://10.0.2.2:7298/api/UniqueCode/getKandydaci");
                setUpCertificate();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                int responseCode = connection.getResponseCode();
                if (!(responseCode == HttpURLConnection.HTTP_OK)) {
                    Log.d("ERR_response","Error in HTTP request, response code: " + responseCode);
                    connection.disconnect();
                    return null;
                }

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                JSONObject jsonObject = new JSONObject(response.toString());
                connection.disconnect();

                return jsonObject;

            }catch(IOException | JSONException e){
                Log.d("Error", e.toString());
                return null;
            }
        }

        protected void onPostExecute(JSONObject result){
            if(result == null)
                Log.d("ErrConnect", "result is null");
            try {
                JSONArray arrayKomitet = result.getJSONObject("Komitet").getJSONArray("$values");
                JSONArray arrayKandydat = result.getJSONObject("Kandydaci").getJSONArray("$values");
                DBController.getInstance(getBaseContext()).clearBeforeSychronizing();
                for(int i = 0; i < arrayKandydat.length(); i++){
                    KandydatModel model = new KandydatModel();
                    model.ID = arrayKandydat.getJSONObject(i).getString("ID");
                    model.Imie = arrayKandydat.getJSONObject(i).getString("Imie");
                    model.Nazwisko = arrayKandydat.getJSONObject(i).getString("Nazwisko");
                    if(model.Imie.endsWith("a"))
                        model.Zdjecie = R.drawable.woman_person;
                    else
                        model.Zdjecie = R.drawable.man_person;
                    model.Opis = arrayKandydat.getJSONObject(i).getString("Opis");
                    model.czySenat = arrayKandydat.getJSONObject(i).getBoolean("czySenat");
                    model.Nr_listy = arrayKandydat.getJSONObject(i).getInt("NrListy");
                    model.IDKomitetu = arrayKandydat.getJSONObject(i).getString("IDKomitetu");
                    model.IDokreg = arrayKandydat.getJSONObject(i).getString("IDokreg");
                    model.Okreg = arrayKandydat.getJSONObject(i).getString("NazwaOkregu");
                    DBController.getInstance(getBaseContext()).insertKandydat(model);
                }

                for(int i = 0; i < arrayKomitet.length(); i++){
                    KomitetModel model = new KomitetModel();
                    model.ID = arrayKomitet.getJSONObject(i).getString("ID");
                    model.Nazwa = arrayKomitet.getJSONObject(i).getString("Nazwa");
                    model.LogoNazwa = R.drawable.partia;
                    model.Nrlisty = arrayKomitet.getJSONObject(i).getInt("NrListy");
                    DBController.getInstance(getBaseContext()).insertKomitet(model);
                }
            } catch (JSONException | NullPointerException e) {
                Log.d("ErrConnect", e.toString());
            }
            ArrayList<KandydatModel> x2 = DBController.getInstance(getBaseContext()).getKandydaci();
        }

        private void setUpCertificate(){
            try {
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class StopVote extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params){
            try {
                URL url = new URL("https://10.0.2.2:7298/api/UniqueCode/stopVote");
                setUpCertificate();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                connection.disconnect();

                return responseCode == HttpURLConnection.HTTP_OK;

            }catch(IOException e){
                Log.d("Error", e.toString());
                return null;
            }
        }

        protected void onPostExecute(Boolean result){
            if(result == null){
                Log.d("ErrConnect", "result is null");
                return;
            }

            if(!result){
                Toast toast = Toast.makeText(getBaseContext(), "Wystąpił błąd", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            Toast toast = Toast.makeText(getBaseContext(), "Zakończono proces głosowania", Toast.LENGTH_SHORT);
            toast.show();
        }

        private void setUpCertificate(){
            try {
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

