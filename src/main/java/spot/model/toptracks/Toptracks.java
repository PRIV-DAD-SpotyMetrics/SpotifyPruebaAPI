//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package spot.model.toptracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Toptracks {
    @SerializedName("items")
    @Expose
    private List<Item> items = new ArrayList();

    public Toptracks() {
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Toptracks.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("items");
        sb.append('=');
        sb.append(this.items == null ? "<null>" : this.items);
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
        result = result * 31 + (this.items == null ? 0 : this.items.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Toptracks)) {
            return false;
        } else {
            Toptracks rhs = (Toptracks)other;
            return this.items == rhs.items || this.items != null && this.items.equals(rhs.items);
        }
    }
}
