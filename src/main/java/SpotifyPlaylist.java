
import com.google.gson.annotations.SerializedName;

import Spotify.stats.SpotifyTrack;

import java.util.List;

public class SpotifyPlaylist {
    @SerializedName("items")
    private List<SpotifyTrack> items;

    public List<SpotifyTrack> getItems() {
        return items;
    }
}





