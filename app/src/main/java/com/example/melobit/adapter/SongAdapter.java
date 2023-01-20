package com.example.melobit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.melobit.R;
import com.example.melobit.models.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    Context context;
    List<Song> songs;
    private final ClickListener clickListener;

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cover;
        TextView name, artist;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            name = itemView.findViewById(R.id.name);
            artist = itemView.findViewById(R.id.artist);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position >= 0) {
                clickListener.onSongClick(position, view, songs.get(position).getId());
            }
        }
    }
    public interface ClickListener {
        void onSongClick(int position, View v, String id);
    }

    public SongAdapter(Context context, List<Song> songs ,ClickListener clickListener) {
        this.context = context;
        this.songs = songs;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(context).inflate(R.layout.song_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.name.setText(songs.get(position).getTitle());
        holder.artist.setText(songs.get(position).getArtists().get(0).getFullName());
        Picasso.get().load(songs.get(position).getImage().getCover().getUrl()).into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
