package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.votingapp.DBController.DBController;
import com.example.votingapp.Models.KandydatModel;
import com.example.votingapp.Models.KomitetModel;
import com.example.votingapp.Voter.Sejm.WyborListyWyborczej;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {

    private EditText codeInput;
    private TextView errText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        codeInput = (EditText) findViewById(R.id.editTextCode);
        errText = (TextView) findViewById(R.id.textViewErr);
        btn.setOnClickListener(v ->{
            if(codeInput.getText().toString().isEmpty()){
                errText.setText("Nie podano kodu");
                return;
            }

            new CheckCodeTask().execute();
        });
    }

    class CheckCodeTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params){
            try {
                URL url = new URL("https://10.0.2.2:7298/api/UniqueCode/checkCode");
                setUpCertificate();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String code_ = codeInput.getText().toString();
                byte[] postData = code_.getBytes(StandardCharsets.UTF_8);
                connection.setRequestProperty("Content-Length", String.valueOf(postData.length));
                connection.connect();
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(postData);
                }

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
                return jsonObject.getBoolean("exists");

            }catch(IOException | JSONException e){
                Log.d("Error", e.toString());
                return null;
            }
        }

        protected void onPostExecute(Boolean result){
            if(result == null) {
                Log.d("ErrConnect", "result is null");
                return;
            }

            if(!result) {
                errText.setText("Podano błędny kod!");
                return;
            }

            String userCode = codeInput.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString("Code", userCode);
            Intent intent = new Intent(MainActivity.this, WyborListyWyborczej.class);
            intent.putExtras(bundle);
            startActivity(intent);
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
    /*class SychronizeDBTask extends AsyncTask<String, Void, JSONObject> {
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
                JSONArray array = result.getJSONArray("$values");
                DBController.getInstance(getBaseContext()).deleteKandydaci();
                for(int i = 0; i < array.length(); i++){
                    KandydatModel model = new KandydatModel();
                    model.Name = array.getJSONObject(i).getString("Imie");
                    model.Surname = array.getJSONObject(i).getString("Nazwisko");
                    model.Img = R.drawable.ic_launcher_background;
                    model.Desc = array.getJSONObject(i).getString("Opis");
                    model.Nr_listy = array.getJSONObject(i).getInt("NrListy");
                    model.Okreg = array.getJSONObject(i).getString("NazwaOkregu");
                    model.czySenat = array.getJSONObject(i).getBoolean("czySenat");
                    model.Guid  = array.getJSONObject(i).getString("ID");
                    DBController.getInstance(getBaseContext()).insertKandydat(model);
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
*/
}