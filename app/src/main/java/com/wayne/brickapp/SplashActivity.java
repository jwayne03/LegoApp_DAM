package com.wayne.brickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity implements Runnable {

    TextView tvCurrentStep;

    Handler handler;
    int stepCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvCurrentStep = findViewById(R.id.tvCurrentStep);

        // Program and fire the handler immediately
        handler = new Handler();
        stepCounter = 0;
        handler.postDelayed(this, 0);
    }

    @Override
    public void run() {
        if (stepCounter == 0) {
            // First step: only text
            tvCurrentStep.setText(R.string.lbl_step_initializing);
            stepCounter++;
            handler.postDelayed(this, 1000);
        } else if (stepCounter == 1) {
            // Second step: only text
            tvCurrentStep.setText(R.string.lbl_step_checking_api_key);
            stepCounter++;
            handler.postDelayed(this, 1000);
        } else {
            // Third step: checking the API key
            SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
            String apiKey = prefs.getString("apiKey", "");
            Log.d("flx", "API Key = " + apiKey);
            Intent intent;
            if (apiKey.isEmpty()) {
                // The API key is not working properly or there is no network connection
                intent = new Intent(this, EnterApiKeyActivity.class);
            } else {
                // All went well, continue to the main activity
                intent = new Intent(this, SearchActivity.class);
            }
            // Replace the current activity so the back button
            // will exit the app after loading the new activity
            finish();
            startActivity(intent);
        }
    }
}