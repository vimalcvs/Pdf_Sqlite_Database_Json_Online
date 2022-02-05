package com.example.jsonexample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jsonexample.helper.SharedPref;
import com.example.jsonexample.helper.Tools;
import com.example.jsonexample.home.adapter.HomeAdapter;
import com.example.jsonexample.home.database.DBHomeHelper;
import com.example.jsonexample.home.model.HomeModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    HomeAdapter homeAdapter;
    SwitchMaterial switchTheme;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.getTheme(this);
        setContentView(R.layout.activity_main);
        sharedPref = new SharedPref(this);

        DBHomeHelper DBHomeHelper = new DBHomeHelper(MainActivity.this);
        List<HomeModel> homeModels = DBHomeHelper.getMovies();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        homeAdapter = new HomeAdapter(MainActivity.this, homeModels);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initView();
    }

    private void initView() {
        switchTheme = findViewById(R.id.switchCompat);
        switchTheme.setChecked(sharedPref.getIsDarkTheme());
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPref.setIsDarkTheme(isChecked);
            overridePendingTransition(0, 0);
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}