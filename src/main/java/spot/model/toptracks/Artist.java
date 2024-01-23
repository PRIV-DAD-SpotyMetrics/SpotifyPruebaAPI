//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package spot.model.toptracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist {
    @SerializedName("external_urls")
    @Expose
    private ExternalUrls externalUrls;
    @SerializedName("name")
    @Expose
    private String name;

    public Artist() {
    }

    public ExternalUrls getExternalUrls() {
        return this.externalUrls;
    }

    public void setExternalUrls(ExternalUrls externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Artist.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("externalUrls");
        sb.append('=');
        sb.append(this.externalUrls == null ? "<null>" : this.externalUrls);
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
        result = result * 31 + (this.externalUrls == null ? 0 : this.externalUrls.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Artist)) {
            return false;
        } else {
            Artist rhs = (Artist)other;
            return (this.name == rhs.name || this.name != null && this.name.equals(rhs.name)) && (this.externalUrls == rhs.externalUrls || this.externalUrls != null && this.externalUrls.equals(rhs.externalUrls));
        }
    }
}
