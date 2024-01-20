package Spotify.stats;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SpotifyTrackInfo {
    @SerializedName("name")
    private String name;

    @SerializedName("artists")
    private List<SpotifyArtist> artists;

    public String getName() {
        return name;
    }

    public List<SpotifyArtist> getArtists() {
        return artists;
    }
}