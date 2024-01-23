//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package spot.model.toptracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalUrls__2 {
    @SerializedName("spotify")
    @Expose
    private String spotify;

    public ExternalUrls__2() {
    }

    public String getSpotify() {
        return this.spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ExternalUrls__2.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("spotify");
        sb.append('=');
        sb.append(this.spotify == null ? "<null>" : this.spotify);
        sb.append(',');
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.setCharAt(sb.length() - 1, ']');
        } else {
            sb.append(']');
        }

        return sb.toString();
    }

    public int hashCode() {
        int result = 1;
        result = result * 31 + (this.spotify == null ? 0 : this.spotify.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof ExternalUrls__2)) {
            return false;
        } else {
            ExternalUrls__2 rhs = (ExternalUrls__2)other;
            return this.spotify == rhs.spotify || this.spotify != null && this.spotify.equals(rhs.spotify);
        }
    }
}
