package com.example.melobit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{

    private TextView res;
    private RecyclerView newSongs, TopSinger;
    private EditText etQuery;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = findViewById(R.id.res);
        newSongs = findViewById(R.id.new_songs);
        TopSinger = findViewById(R.id.top_singers);
        Button hitsBtn = findViewById(R.id.btn);
        FloatingActionButton btnSearch = findViewById(R.id.btn_search);
        etQuery = findViewById(R.id.et_query);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newSongs.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        TopSinger.setLayoutManager(layoutManager1);


    }
}