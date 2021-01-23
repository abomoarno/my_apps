package africanews.tv.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class DisplayableItem implements Parcelable {

    private int id;
    private String title;
    private String link;
    private int plateform;
    private int type;

    public DisplayableItem() {
    }

    protected DisplayableItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        link = in.readString();
        plateform = in.readInt();
        type = in.readInt();
    }

    public static final Creator<DisplayableItem> CREATOR = new Creator<DisplayableItem>() {
        @Override
        public DisplayableItem createFromParcel(Parcel in) {
            return new DisplayableItem(in);
        }

        @Override
        public DisplayableItem[] newArray(int size) {
            return new DisplayableItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPlateform() {
        return plateform;
    }

    public void setPlateform(int plateform) {
        this.plateform = plateform;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(link);
        parcel.writeInt(plateform);
        parcel.writeInt(type);
    }
}
