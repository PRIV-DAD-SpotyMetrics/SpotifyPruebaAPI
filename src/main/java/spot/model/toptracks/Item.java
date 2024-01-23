//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package spot.model.toptracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("album")
    @Expose
    private Album album;
    @SerializedName("external_urls")
    @Expose
    private ExternalUrls__2 externalUrls;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private Integer popularity;
    @SerializedName("preview_url")
    @Expose
    private String previewUrl;

    public Item() {
    }

    public Album getAlbum() {
        return this.album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public ExternalUrls__2 getExternalUrls() {
        return this.externalUrls;
    }

    public void setExternalUrls(ExternalUrls__2 externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopularity() {
        return this.popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Item.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("album");
        sb.append('=');
        sb.append(this.album == null ? "<null>" : this.album);
        sb.append(',');
        sb.append("externalUrls");
        sb.append('=');
        sb.append(this.externalUrls == null ? "<null>" : this.externalUrls);
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(this.name == null ? "<null>" : this.name);
        sb.append(',');
        sb.append("popularity");
        sb.append('=');
        sb.append(this.popularity == null ? "<null>" : this.popularity);
        sb.append(',');
        sb.append("previewUrl");
        sb.append('=');
        sb.append(this.previewUrl == null ? "<null>" : this.previewUrl);
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
        result = result * 31 + (this.externalUrls == null ? 0 : this.externalUrls.hashCode());
        result = result * 31 + (this.previewUrl == null ? 0 : this.previewUrl.hashCode());
        result = result * 31 + (this.album == null ? 0 : this.album.hashCode());
        result = result * 31 + (this.popularity == null ? 0 : this.popularity.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Item)) {
            return false;
        } else {
            Item rhs = (Item)other;
            return (this.name == rhs.name || this.name != null && this.name.equals(rhs.name)) && (this.externalUrls == rhs.externalUrls || this.externalUrls != null && this.externalUrls.equals(rhs.externalUrls)) && (this.previewUrl == rhs.previewUrl || this.previewUrl != null && this.previewUrl.equals(rhs.previewUrl)) && (this.album == rhs.album || this.album != null && this.album.equals(rhs.album)) && (this.popularity == rhs.popularity || this.popularity != null && this.popularity.equals(rhs.popularity));
        }
    }
}
