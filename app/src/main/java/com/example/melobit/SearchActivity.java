package com.example.melobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    TextView error;
    RecyclerView results;
    EditText searchTerm;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        error = findViewById(R.id.error);
        results = findViewById(R.id.results);
        searchTerm = findViewById(R.id.search_term);
        search = findViewById(R.id.button_search);
    }
}