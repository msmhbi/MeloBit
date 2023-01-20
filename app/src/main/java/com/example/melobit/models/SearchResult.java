package com.example.melobit.models;

import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("song")
    private Song song;

    @SerializedName("position")
    private int position;

    @SerializedName("type")
    private String type;

    @SerializedName("artist")
    private Artist artist;

    public Song getSong(){
        return song;
    }

    public int getPosition(){
        return position;
    }

    public String getType(){
        return type;
    }

    public Artist getArtist(){
        return artist;
    }
}
