package com.wayne.brickapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

public class VoleyActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voley);
//        setTitle(getString(R.string.lbl_settings));
        progressBar = findViewById(R.id.progressBar);
        tvResults = findViewById(R.id.tvResults);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET,
                "https://rebrickable.com/api/v3/lego/sets?" +
                        "key=e282dd2514128fc2060dd55ba6138664&search=mario",
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
                                tvResults, "ERROR" + error.networkResponse.statusCode,
                                3000).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
        queue.add(request);
        progressBar.setVisibility(View.VISIBLE);
    }
}