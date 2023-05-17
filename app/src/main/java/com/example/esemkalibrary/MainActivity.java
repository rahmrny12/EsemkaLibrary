package com.example.esemkalibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.esemkalibrary.adapter.BookAdapter;
import com.example.esemkalibrary.adapter.RecyclerViewOnClickListener;
import com.example.esemkalibrary.models.Book;
import com.example.esemkalibrary.services.AsyncCallBack;
import com.example.esemkalibrary.services.GetAsyncTask;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        toHome();
                        return true;
                    case R.id.action_profile:
                        toProfile();
                        return true;
                }
                return false;
            }

            private void toHome() {
                HomeFragment fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, fragment).commit();
            }

            private void toProfile() {
                HomeFragment fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, fragment).commit();
            }
        });
    }
}