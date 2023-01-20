package com.example.melobit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melobit.adapter.SongAdapter;
import com.example.melobit.models.Song;
import com.example.melobit.models.SongsResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    TextView error;
    RecyclerView results;
    EditText searchTerm;
    FloatingActionButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        error = findViewById(R.id.error);
        results = findViewById(R.id.results);
        searchTerm = findViewById(R.id.search_term);
        search = findViewById(R.id.button_search);

        RequestManager manager = new RequestManager();
        results.setLayoutManager(new GridLayoutManager(this, 2));
        SearchRequestListener listener = new SearchRequestListener() {
            @Override
            public void didFetch(List<Song> response) {
                results.setAdapter(new SongAdapter(getApplicationContext(), response, (position, v, id) -> {
                    Intent intent = new Intent(SearchActivity.this, SongActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }));
            }

            @Override
            public void didError(String errorMessage) {
                error.setText(errorMessage);
            }
        };
        search.setOnClickListener(view -> {
            if (!searchTerm.getText().toString().isEmpty()) {
                manager.search(listener, searchTerm.getText().toString());
            }
        });

    }
}