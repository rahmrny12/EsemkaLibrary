package com.example.esemkalibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.esemkalibrary.services.AsyncCallBack;
import com.example.esemkalibrary.services.GetAsyncTask;
import com.example.esemkalibrary.services.PostAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.prefs.Preferences;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnSignUp;
    EditText inputEmail, inputPassword;
    ProgressBar loginLoading;
    private String TAG = "DEBUG MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        loginLoading = findViewById(R.id.loginLoading);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isInputEmpty(inputEmail) && !Utils.isInputEmpty(inputPassword)) {
                    LoginUser();
                } else {
                    inputEmail.setText("iclarricoates3@clickbank.net");
                    inputPassword.setText("fTa9aI71rEm");
                    LoginUser();
                }
            }

            private void LoginUser() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("email", inputEmail.getText().toString());
                    jsonObject.put("password", inputPassword.getText().toString());

                    new PostAsyncTask("auth",
                            jsonObject,
                            new AsyncCallBack() {
                        @Override
                        public void OnComplete(int statusCode, String result) {
                            btnLogin.setEnabled(true);
                            loginLoading.setVisibility(View.GONE);
                            if (statusCode == 200) {
                                try {
                                    JSONObject json = new JSONObject(result);
                                    Log.d(TAG, "OnComplete: " + json);
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("token", json.getString("token"));
                                    editor.apply();

                                    finish();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Login gagal. Username atau password tidak sesuai.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void OnLoading() {
                            btnLogin.setEnabled(false);
                            loginLoading.setVisibility(View.VISIBLE);
                        }
                    }).execute();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}