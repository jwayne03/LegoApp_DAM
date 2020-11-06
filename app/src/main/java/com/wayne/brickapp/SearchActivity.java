package com.wayne.brickapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.wayne.brickapp.model.ApiThemes;
import com.wayne.brickapp.model.Theme;
import com.wayne.brickapp.model.ThemeAdapter;
import com.wayne.brickapp.model.Themes;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView tvResults, tvYourApiKey;
    EditText editTextSearch;
    ImageButton searchButton, settingsButton;
    RecyclerView recyclerView;

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
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(SearchActivity.this, "Click en theme id: " + id,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
        search();
        settings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String search = prefs.getString("search", " ");
        editTextSearch.setText(search);
        search();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();

        Editable search = editTextSearch.getText();
        ed.putString("search", String.valueOf(search));
        ed.apply();
    }

    private void search() {
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                String apiKey = prefs.getString("apiKey", "");
                Log.d("wayne", "API Key = " + apiKey);

                RequestQueue queue = Volley.newRequestQueue(this);
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        "https://rebrickable.com/api/v3/lego/sets?key=" + apiKey,
//                                ?" +
//                        "key=" + apiKey + "&search=" + editTextSearch.getText(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("wayne", "onResponse: " + response);
                                Gson gson = new Gson();
                                Themes themes = gson.fromJson(response, Themes.class);
                                tvResults.setText("TOTAL: " + themes.size() + " themes");
                                ThemeAdapter adapter = new ThemeAdapter(
                                        SearchActivity.this, themes, R.layout.item_theme);
                                recyclerView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(tvResults, "TOTAL: " + themes.size()
                                        + " themes", 2000)
                                        .show();
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
//        });
//    }

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