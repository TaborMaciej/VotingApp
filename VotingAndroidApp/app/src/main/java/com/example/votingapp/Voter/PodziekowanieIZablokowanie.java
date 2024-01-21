package com.example.votingapp.Voter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.votingapp.AdminLoginActivity;
import com.example.votingapp.AdminPanelActivity;
import com.example.votingapp.DBController.DBController;
import com.example.votingapp.MainActivity;
import com.example.votingapp.Models.CodeModel;
import com.example.votingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PodziekowanieIZablokowanie extends AppCompatActivity {

    public CodeModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podziekowanie_izablokowanie);
        String code = getIntent().getExtras().getString("Code");
        model =  DBController.getInstance(this).getSingleCode(code).get(0);

        findViewById(R.id.resetBtn).setOnClickListener(v ->{
            Intent intent = new Intent(PodziekowanieIZablokowanie.this, MainActivity.class);
            startActivity(intent);
        });

        new CheckLoginTask().execute();
    }

    class CheckLoginTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params){
            try {
                URL url = new URL("https://10.0.2.2:7298/api/UniqueCode/postVote");
                setUpCertificate();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("code", model.Code);
                postDataParams.put("IDsejm", model.IDKandydatSejmu);
                postDataParams.put("IDsenat", model.IDKandydatSenatu);
                String postData = postDataParams.toString();

                connection.connect();
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (!(responseCode == HttpURLConnection.HTTP_OK)) {
                    Log.d("ERR_response","Error in HTTP request, response code: " + responseCode);
                    connection.disconnect();
                    return false;
                }

                connection.disconnect();
                return true;
            }catch(IOException | JSONException e){
                Log.d("Error", e.toString());
                return false;
            }
        }

        protected void onPostExecute(Boolean result){
            if(result == null)
                return;

            Log.d("Glosowanie", "Poprawnie przeslano glos");
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