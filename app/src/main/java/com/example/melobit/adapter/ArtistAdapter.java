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
import com.example.melobit.models.Artist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    Context context;
    List<Artist> artists;

    public ArtistAdapter(Context context, List<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
        }
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArtistViewHolder(LayoutInflater.from(context).inflate(R.layout.artist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        holder.name.setText(artists.get(position).getFullName());
        Picasso.get().load(artists.get(position).getImage().getCover().getUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}