package com.example.esemkalibrary.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.esemkalibrary.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAsyncTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private String endPoint;
    private AsyncCallBack asyncCallBack;

    public GetAsyncTask(Context context, String endPoint, AsyncCallBack asyncCallBack) {
        this.context = context;
        this.endPoint = endPoint;
        this.asyncCallBack = asyncCallBack;
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

            String token = Utils.getToken(context);
            conn.setRequestProperty("Authorization", "Bearer " + token);

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
            statusCode = 400;
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
