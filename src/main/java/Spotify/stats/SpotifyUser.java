package Spotify.stats;
import com.google.gson.annotations.SerializedName;

public class SpotifyUser {
    @SerializedName("id")
    private String userId;

    public String getUserId() {
        return userId;
    }
}
