package com.wayne.brickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

public class SearchActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView tvResults, tvYourApiKey;
    EditText editTextSearch;
    ImageButton searchButton, settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voley);
        progressBar = findViewById(R.id.progressBar);
        tvResults = findViewById(R.id.tvResults);
        editTextSearch = findViewById(R.id.editTextSearch);
        tvYourApiKey = findViewById(R.id.tvYourApiKey);
        searchButton = findViewById(R.id.searchImageButton);
        settingsButton = findViewById(R.id.settingsImageButton);

        search();
        settings();
    }

    private void search() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                String apiKey = prefs.getString("apiKey", "");
                Log.d("wayne", "API Key = " + apiKey);

                RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
                StringRequest request = new StringRequest(Request.Method.GET,
                        "https://rebrickable.com/api/v3/lego/sets?" +
                                "key=" + apiKey + "&search=" + editTextSearch.getText(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("wayne", "onResponse: " + response);
                                tvResults.setText(response);
                                progressBar.setVisibility(View.GONE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("wayne", "onErrorResponse: " + error.networkResponse);
                                Snackbar.make(
                                        tvResults, "ERROR " + error.networkResponse.statusCode
                                                + ": no internet or your apikey is wrong",
                                        3000).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                queue.add(request);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void settings() {
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}