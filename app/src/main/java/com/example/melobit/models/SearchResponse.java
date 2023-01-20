package com.example.melobit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("total")
    private int total;

    @SerializedName("results")
    private List<SearchResult> results;

    public int getTotal(){
        return total;
    }

    public List<SearchResult> getResults(){
        return results;
    }
}