//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package spot.model.toptracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Album {
    @SerializedName("album_type")
    @Expose
    private String albumType;
    @SerializedName("artists")
    @Expose
    private List<Artist> artists = new ArrayList();
    @SerializedName("external_urls")
    @Expose
    private ExternalUrls__1 externalUrls;
    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList();
    @SerializedName("name")
    @Expose
    private String name;

    public Album() {
    }

    public String getAlbumType() {
        return this.albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public List<Artist> getArtists() {
        return this.artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public ExternalUrls__1 getExternalUrls() {
        return this.externalUrls;
    }

    public void setExternalUrls(ExternalUrls__1 externalUrls) {
        this.externalUrls = externalUrls;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Album.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("albumType");
        sb.append('=');
        sb.append(this.albumType == null ? "<null>" : this.albumType);
        sb.append(',');
        sb.append("artists");
        sb.append('=');
        sb.append(this.artists == null ? "<null>" : this.artists);
        sb.append(',');
        sb.append("externalUrls");
        sb.append('=');
        sb.append(this.externalUrls == null ? "<null>" : this.externalUrls);
        sb.append(',');
        sb.append("images");
        sb.append('=');
        sb.append(this.images == null ? "<null>" : this.images);
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(this.name == null ? "<null>" : this.name);
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
        result = result * 31 + (this.name == null ? 0 : this.name.hashCode());
        result = result * 31 + (this.images == null ? 0 : this.images.hashCode());
        result = result * 31 + (this.externalUrls == null ? 0 : this.externalUrls.hashCode());
        result = result * 31 + (this.artists == null ? 0 : this.artists.hashCode());
        result = result * 31 + (this.albumType == null ? 0 : this.albumType.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Album)) {
            return false;
        } else {
            Album rhs = (Album)other;
            return (this.name == rhs.name || this.name != null && this.name.equals(rhs.name)) && (this.images == rhs.images || this.images != null && this.images.equals(rhs.images)) && (this.externalUrls == rhs.externalUrls || this.externalUrls != null && this.externalUrls.equals(rhs.externalUrls)) && (this.artists == rhs.artists || this.artists != null && this.artists.equals(rhs.artists)) && (this.albumType == rhs.albumType || this.albumType != null && this.albumType.equals(rhs.albumType));
        }
    }
}
