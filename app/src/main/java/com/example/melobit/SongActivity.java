package com.example.melobit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.melobit.models.Song;
import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDate;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SongActivity extends AppCompatActivity {

    private TextView error, songName, singer, downloadCount,
            releaseDate, textViewlyrics,textView,textView1,textView2;
    private ImageView cover,play;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        textView = findViewById(R.id.textView7);
        textView1 = findViewById(R.id.textView6);
        textView2 = findViewById(R.id.textView5);
        error = findViewById(R.id.error);
        songName = findViewById(R.id.song_name);
        singer = findViewById(R.id.singer_name);
        downloadCount = findViewById(R.id.download_count);
        releaseDate = findViewById(R.id.release_date);
        textViewlyrics = findViewById(R.id.lyrics);
        cover = findViewById(R.id.image);
        play = findViewById(R.id.play);
        seekBar = findViewById(R.id.seekBar);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        RequestManager manager = new RequestManager();
        SongRequestListener listener = new SongRequestListener() {
            @Override
            public void didFetch(Song response) {
                textView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                songName.setText(response.getTitle());
                singer.setText(response.getArtists().get(0).getFullName());
                downloadCount.setText(response.getDownloadCount());
                releaseDate.setText(toJalali(response.getReleaseDate().split("T")[0]));
                String lyrics = response.getLyrics();
                if (!lyrics.isEmpty()){
                    textView2.setVisibility(View.VISIBLE);
                    textViewlyrics.setText(lyrics);
                }
                Picasso.get()
                        .load(response.getImage().getCover().getUrl())
                        .into(cover);
                setUpMediaPlayer(response.getAudio().getMedium().getUrl());
            }
            @Override
            public void didError(String status) {
                error.setText(status);
            }
        };
        manager.getSong(listener, id);
        play.setOnClickListener(view -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            } else {
                mediaPlayer.start();
                play.setImageResource(R.drawable.ic_baseline_pause_24);
            }
        });
        seekBar.setOnTouchListener((view, motionEvent) -> true);
    }

    private String toJalali(String date) {
        String[] releaseDate = date.split("-");
        int year = Integer.parseInt(releaseDate[0]);
        int month = Integer.parseInt(releaseDate[1]);
        int day = Integer.parseInt(releaseDate[2]);
        DateConverter dateConverter = new DateConverter();
        JalaliDate jalaliDate = dateConverter.gregorianToJalali(year,month,day);
        return jalaliDate.format(new JalaliDateFormatter("yyyy M dd", JalaliDateFormatter.FORMAT_IN_PERSIAN));
    }

    private void setUpMediaPlayer(String url) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);

        } catch (IOException e) {
            e.printStackTrace();
        }
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition()/1000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }}, 0, 1000);
    }
    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onBackPressed();
    }
}