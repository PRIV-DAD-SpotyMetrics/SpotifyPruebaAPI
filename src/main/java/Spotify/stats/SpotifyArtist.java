package Spotify.stats;

import com.google.gson.annotations.SerializedName;

public class SpotifyArtist {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
    }