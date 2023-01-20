package com.example.melobit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melobit.adapter.ArtistAdapter;
import com.example.melobit.adapter.SongAdapter;
import com.example.melobit.models.ArtistsResponse;
import com.example.melobit.models.SongsResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{

    private TextView error;
    private RecyclerView newSongs, TopSinger;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        error = findViewById(R.id.error);
        newSongs = findViewById(R.id.new_songs);
        TopSinger = findViewById(R.id.top_singers);
        FloatingActionButton hitsBtn = findViewById(R.id.btn);
        FloatingActionButton btnSearch = findViewById(R.id.btn_search);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newSongs.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        TopSinger.setLayoutManager(layoutManager1);

        hitsBtn.setOnClickListener(view -> {
            //todo
        });
        btnSearch.setOnClickListener(view -> {
            //todo
        });

        RequestManager manager = new RequestManager();

        SongsRequestListener latestListener = new SongsRequestListener() {
            @Override
            public void didFetch(SongsResponse response) {
                newSongs.setAdapter(new SongAdapter(MainActivity.this, response.getResults(),
                        (position, v, id) -> {
                            Intent intent = new Intent(MainActivity.this, SongActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }));
            }
            @Override
            public void didError(String errorMessage) {
                error.setText(errorMessage);
            }
        };

        ArtistsRequestListener artistsListener = new ArtistsRequestListener() {
            @Override
            public void didFetch(ArtistsResponse response) {
                TopSinger.setAdapter(new ArtistAdapter(MainActivity.this, response.getResults()));
            }
            @Override
            public void didError(String errorMessage) {
                error.setText(errorMessage);
            }
        };
        manager.getLatestSongs(latestListener);
        manager.getTopSingers(artistsListener);
    }
}