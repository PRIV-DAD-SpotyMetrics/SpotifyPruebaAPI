//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package spot.model.toptracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private Integer width;

    public Image() {
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Image.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("height");
        sb.append('=');
        sb.append(this.height == null ? "<null>" : this.height);
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(this.url == null ? "<null>" : this.url);
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(this.width == null ? "<null>" : this.width);
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
        result = result * 31 + (this.width == null ? 0 : this.width.hashCode());
        result = result * 31 + (this.url == null ? 0 : this.url.hashCode());
        result = result * 31 + (this.height == null ? 0 : this.height.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Image)) {
            return false;
        } else {
            Image rhs = (Image)other;
            return (this.width == rhs.width || this.width != null && this.width.equals(rhs.width)) && (this.url == rhs.url || this.url != null && this.url.equals(rhs.url)) && (this.height == rhs.height || this.height != null && this.height.equals(rhs.height));
        }
    }
}
