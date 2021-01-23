package africanews.tv.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class TvChannel implements Parcelable {

    private int tv_id;
    private String name;
    private String link;
    private String illustration;
    private String plateform;
    private int language;
    private int channel;

    private boolean selected;

    public TvChannel() {
    }

    protected TvChannel(Parcel in) {
        tv_id = in.readInt();
        name = in.readString();
        link = in.readString();
        illustration = in.readString();
        plateform = in.readString();
        language = in.readInt();
        selected = in.readByte() != 0;
        channel = in.readInt();
    }

    public static final Creator<TvChannel> CREATOR = new Creator<TvChannel>() {
        @Override
        public TvChannel createFromParcel(Parcel in) {
            return new TvChannel(in);
        }

        @Override
        public TvChannel[] newArray(int size) {
            return new TvChannel[size];
        }
    };

    public int getTv_id() {
        return tv_id;
    }

    public void setTv_id(int tv_id) {
        this.tv_id = tv_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getPlateform() {
        return plateform;
    }

    public void setPlateform(String plateform) {
        this.plateform = plateform;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(tv_id);
        parcel.writeString(name);
        parcel.writeString(link);
        parcel.writeString(illustration);
        parcel.writeString(plateform);
        parcel.writeInt(language);
        parcel.writeByte((byte) (selected ? 1 : 0));
        parcel.writeInt(channel);
    }
}
