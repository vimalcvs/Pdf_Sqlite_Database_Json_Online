package com.example.jsonexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.jsonexample.home.database.DBHomeHelper;
import com.example.jsonexample.home.model.HomeModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    final String JSON_URL = "https://www.technovimal.in/apps/Movie.json";

    public static final String SHARED_PREFS_NAME = "login data";
    public static final String DOWNLOADED_KEY = "data downloaded successfully";

    RequestQueue requestQueue;
    boolean error_occurred;
    DBHomeHelper DBHomeHelper;
    SharedPreferences sharedPreferences;
    Intent movie_list_intent;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MobileAds.initialize(SplashActivity.this, initializationStatus -> {
            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(SplashActivity.this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd = interstitialAd;
                    Log.i("TAG", "onAdLoaded");
                }
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    mInterstitialAd = null;
                    Log.i("TAG", loadAdError.getMessage());

                }
            });
        });


        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        movie_list_intent = new Intent(SplashActivity.this, MainActivity.class);

        if(!(sharedPreferences.getBoolean(DOWNLOADED_KEY, false)))
            parse_JSON_to_SQLite();
        else {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SplashActivity.this);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            overridePendingTransition(0, 0);
                            startActivity(movie_list_intent);
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            mInterstitialAd = null;
                        }
                    });
                } else {
                    overridePendingTransition(0, 0);
                    startActivity(movie_list_intent);
                    overridePendingTransition(0, 0);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }, 500);
        }
    }

    void parse_JSON_to_SQLite() {
        requestQueue = Volley.newRequestQueue(this);
        error_occurred = false;
        DBHomeHelper = new DBHomeHelper(SplashActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null,
                response -> {
                    for(int i = 0; i < response.length(); i++) { HomeModel homeModel;
                        try {
                            JSONObject jsonObjHome = response.getJSONObject(i);

                            int idHome = jsonObjHome.getInt("id");
                            String title = jsonObjHome.getString("name");
                            String imageUrl = jsonObjHome.getString("img_url");
                            String pdfURL = jsonObjHome.getString("pdf_url");
                            String time = jsonObjHome.getString("time");

                            homeModel = new HomeModel(idHome, title, imageUrl, pdfURL, time);

                            if(DBHomeHelper.add_movie(homeModel) == -1)
                                error_occurred = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("SplashActivity Errors", "JSON error");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Log.i("SplashActivity Errors", "Probably SQLite error");
                        }
                    }
                    if(!error_occurred) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(DOWNLOADED_KEY, true);
                        editor.apply();
                    }
                    DBHomeHelper.close();
                    startActivity(movie_list_intent);
                    finish();
                }, error -> {
            error.printStackTrace();
            Log.i("SplashActivity Errors", "JSON response error");
        });
        requestQueue.add(jsonArrayRequest);
    }

}