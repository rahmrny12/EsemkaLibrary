package com.example.esemkalibrary.services;

import android.os.AsyncTask;

import com.example.esemkalibrary.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostAsyncTask extends AsyncTask<Void, Void, String> {
    private String endPoint;
    private JSONObject jsonObject;
    private AsyncCallBack asyncCallBack;

    public PostAsyncTask(String endPoint, JSONObject jsonObject, AsyncCallBack asyncCallBack) {
        this.endPoint = endPoint;
        this.asyncCallBack = asyncCallBack;
        this.jsonObject = jsonObject;
    }

    private int statusCode;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        asyncCallBack.OnLoading();
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(Utils.BASE_URL + endPoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = conn.getOutputStream();
            byte[] bytes = jsonObject.toString().getBytes();
            outputStream.write(bytes);
//            outputStream.flush();
//            outputStream.close();


            statusCode = conn.getResponseCode();

            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return "Terjadi kesalahan. Error : " + e.getMessage();
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        asyncCallBack.OnComplete(statusCode, s);
    }
}
