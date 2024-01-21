package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingapp.DBController.DBController;
import com.example.votingapp.Models.CodeModel;
import com.example.votingapp.Voter.Sejm.WyborListyWyborczej;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class AdminLoginActivity extends AppCompatActivity {
    public EditText loginInput;
    public EditText passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        Button btn = findViewById(R.id.loginBtn);
        loginInput = findViewById(R.id.loginInput);
        passwordInput = findViewById(R.id.passwordInput);
        findViewById(R.id.backBtn2).setOnClickListener(v ->{
            Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
        btn.setOnClickListener(v -> {
            String login = loginInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (login.isEmpty() || password.isEmpty()) {
                Toast toast = Toast.makeText(this, "Niepoprawne dane logowania", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            new CheckCodeTask().execute();
        });
    }

    class CheckCodeTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params){
            try {
                URL url = new URL("https://10.0.2.2:7298/api/UniqueCode/loginUser");
                setUpCertificate();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                String login = loginInput.getText().toString();
                String password = passwordInput.getText().toString();
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("login", login);
                postDataParams.put("password", password);

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
                return jsonObject.getBoolean("correct");

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
            if(!result){
                Toast toast = Toast.makeText(getBaseContext(), "Niepoprawne dane logowania", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            CodeModel codeModel = new CodeModel();
            DBController.getInstance(getBaseContext()).insertCode(codeModel);
            Intent intent = new Intent(AdminLoginActivity.this, AdminPanelActivity.class);
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

    class StopTheVote extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params){
            try {
                URL url = new URL("https://10.0.2.2:7298/api/UniqueCode/loginUser");
                setUpCertificate();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                String login = loginInput.getText().toString();
                String password = passwordInput.getText().toString();
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("login", login);
                postDataParams.put("password", password);

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
                return jsonObject.getBoolean("correct");

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
            if(!result){
                Toast toast = Toast.makeText(getBaseContext(), "Niepoprawne dane logowania", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            CodeModel codeModel = new CodeModel();
            DBController.getInstance(getBaseContext()).insertCode(codeModel);
            Intent intent = new Intent(AdminLoginActivity.this, AdminPanelActivity.class);
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
}