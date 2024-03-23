package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity2";

    AutoCompleteTextView SelectQualifications;
    ArrayList<String> qualifications;
    ArrayAdapter<String> qualificationAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize ArrayList for qualifications
        qualifications = new ArrayList<>();

        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Reference AutoCompleteTextView
        SelectQualifications = findViewById(R.id.SelectQualifications);

        // Fetch qualifications from server
        fetchQualifications();
    }

    private void fetchQualifications() {
        String url = "http://192.168.43.104/AsteriscEnquiry/qualification.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String qualification = response.getString(i);
                                qualifications.add(qualification);
                            }

                            // Populate AutoCompleteTextView with qualifications
                            qualificationAdapter = new ArrayAdapter<>(MainActivity2.this, android.R.layout.simple_dropdown_item_1line, qualifications);
                            SelectQualifications.setAdapter(qualificationAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity2.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching data: " + (error != null ? error.getMessage() : "Unknown error"));
                        Toast.makeText(MainActivity2.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(request);
    }
}
