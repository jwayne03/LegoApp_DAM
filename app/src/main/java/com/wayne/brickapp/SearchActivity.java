package com.wayne.brickapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.wayne.brickapp.model.ApiThemes;
import com.wayne.brickapp.model.ThemeAdapter;

public class SearchActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView tvResults, tvYourApiKey;
    ImageButton searchButton, settingsButton;
    RecyclerView recyclerView;
    String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voley);
        progressBar = findViewById(R.id.progressBar);
        tvResults = findViewById(R.id.tvResults);
        tvYourApiKey = findViewById(R.id.tvYourApiKey);
        searchButton = findViewById(R.id.searchImageButton);
        settingsButton = findViewById(R.id.settingsImageButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        apiKey = prefs.getString("apiKey", "");
        String lastSearch = prefs.getString("lastSearch", "");

        SearchView edSearch = (SearchView) menu.findItem(R.id.searchImageButton).getActionView();
        if (!lastSearch.isEmpty()) {
            edSearch.setIconified(false);
            edSearch.setQuery(lastSearch, true);
            search(lastSearch);
        } else {
            search("");
        }

        edSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) search(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settingsImageButton) {
            Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(String query) {
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastSearch", query);
        editor.apply();

        String apiKey = prefs.getString("apiKey", "");
        Log.d("wayne", "API Key = " + apiKey);

        RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                "https://rebrickable.com/api/v3/lego/sets?key=" + apiKey + "&search=" + query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("wayne", "onResponse: " + response);
                        Gson gson = new Gson();
                        ApiThemes themes = gson.fromJson(response, ApiThemes.class);
                        tvResults.setText("TOTAL: " + themes.getCount() + " themes");
                        ThemeAdapter adapter = new ThemeAdapter(
                                SearchActivity.this, themes);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                        Snackbar.make(tvResults, "TOTAL: " + themes.getCount()
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