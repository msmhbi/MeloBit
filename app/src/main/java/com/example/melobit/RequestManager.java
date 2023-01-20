package com.example.melobit;

import androidx.annotation.NonNull;

import com.example.melobit.models.ArtistsResponse;
import com.example.melobit.models.Song;
import com.example.melobit.models.SongsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RequestManager {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api-beta.melobit.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    interface ApiCalls {

        @GET("song/new/0/11")
        Call<SongsResponse> getNewSongs();

        @GET("artist/trending/0/4")
        Call<ArtistsResponse> getTopSingers();

        @GET("song/{id}")
        Call<Song> getSong(@Path("id") String id);

    }

    ApiCalls apiCalls = retrofit.create(ApiCalls.class);

    public void getLatestSongs(SongsRequestListener listener) {
        Call<SongsResponse> call = apiCalls.getNewSongs();
        call.enqueue(new Callback<SongsResponse>() {
            @Override
            public void onResponse(@NonNull Call<SongsResponse> call, @NonNull Response<SongsResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<SongsResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getTopSingers(ArtistsRequestListener listener) {
        Call<ArtistsResponse> topSingers = apiCalls.getTopSingers();
        topSingers.enqueue(new Callback<ArtistsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArtistsResponse> call, @NonNull Response<ArtistsResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArtistsResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getSong(SongRequestListener listener, String id) {
        Call<Song> song = apiCalls.getSong(id);
        song.enqueue(new Callback<Song>() {
            @Override
            public void onResponse(@NonNull Call<Song> call, @NonNull Response<Song> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Song> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
}

interface SongsRequestListener {
    void didFetch(SongsResponse response);

    void didError(String status);
}

interface ArtistsRequestListener {
    void didFetch(ArtistsResponse response);

    void didError(String errorMessage);
}
interface SongRequestListener {
    void didFetch(Song response);

    void didError(String status);
}
