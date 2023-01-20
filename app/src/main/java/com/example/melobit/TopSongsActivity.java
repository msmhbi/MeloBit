package com.example.melobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.melobit.adapter.SongAdapter;
import com.example.melobit.models.SongsResponse;

public class TopSongsActivity extends AppCompatActivity {

    private RecyclerView topToday, topThisWeek;
    private TextView error,textView,textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_songs);
        topToday = findViewById(R.id.top_today);
        topThisWeek = findViewById(R.id.top_this_week);
        error = findViewById(R.id.error);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topToday.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topThisWeek.setLayoutManager(layoutManager1);

        RequestManager manager = new RequestManager();
        SongsRequestListener todayListener = new SongsRequestListener() {
            @Override
            public void didFetch(SongsResponse response) {
                textView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                topToday.setAdapter(new SongAdapter(TopSongsActivity.this, response.getResults(),
                        (position, v, id) -> {
                            Intent intent = new Intent(TopSongsActivity.this, SongActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }));
            }

            @Override
            public void didError(String errorMessage) {
                error.setText(errorMessage);
            }
        };
        SongsRequestListener thisWeekListener = new SongsRequestListener() {
            @Override
            public void didFetch(SongsResponse response) {
                topThisWeek.setAdapter(new SongAdapter(TopSongsActivity.this, response.getResults(),
                        (position, v, id) -> {
                            Intent intent = new Intent(TopSongsActivity.this, SongActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }));
            }

            @Override
            public void didError(String errorMessage) {
                error.setText(errorMessage);
            }
        };
        manager.getTop10Today(todayListener);
        manager.getTop10ThisWeek(thisWeekListener);
    }
}