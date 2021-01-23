package africanews.tv.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Replay extends DisplayableItem{

    private int replay_id;
    private String title;
    private String link;
    private String illustration;
    private String plateform;
    private int channel_id;
    private String channel_name;
    private int category;
    private String language;
    private String duration;
    private String query_date;
    private String display_date;

    private boolean bookmarked;


    public Replay(int replay_id) {
        this.replay_id = replay_id;
    }

    public Replay(int replay_id, String title) {
        this.replay_id = replay_id;
        this.title = title;
    }

    public Replay() {
    }

    @Override
    protected Replay(Parcel in) {
        replay_id = in.readInt();
        title = in.readString();
        link = in.readString();
        illustration = in.readString();
        plateform = in.readString();
        channel_id = in.readInt();
        channel_name = in.readString();
        category = in.readInt();
        language = in.readString();
        duration = in.readString();
        query_date = in.readString();
        display_date = in.readString();
        bookmarked = in.readByte() != 0;
    }


    public int getReplay_id() {
        return replay_id;
    }

    public void setReplay_id(int replay_id) {
        this.replay_id = replay_id;
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

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getQuery_date() {
        return query_date;
    }
    public void setQuery_date(String query_date) {
        this.query_date = query_date;
    }

    public String getDisplay_date() {
        return display_date;
    }

    public void setDisplay_date(String display_date) {
        this.display_date = display_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(replay_id);
        parcel.writeString(title);
        parcel.writeString(link);
        parcel.writeString(illustration);
        parcel.writeString(plateform);
        parcel.writeInt(channel_id);
        parcel.writeString(channel_name);
        parcel.writeInt(category);
        parcel.writeString(language);
        parcel.writeString(duration);
        parcel.writeString(query_date);
        parcel.writeString(display_date);
        parcel.writeByte((byte) (bookmarked ? 1 : 0));
    }
}
