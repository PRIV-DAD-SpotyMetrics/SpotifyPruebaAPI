package Spotify.stats;

import com.google.gson.annotations.SerializedName;

public class SpotifyTrack {
    @SerializedName("track")
    private SpotifyTrackInfo trackInfo;

    public SpotifyTrackInfo getTrackInfo() {
        return trackInfo;
    }
}