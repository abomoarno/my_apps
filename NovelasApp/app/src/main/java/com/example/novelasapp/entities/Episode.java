package com.example.novelasapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.util.Date;

import static com.example.novelasapp.utils.Utils.SDF;

public class Episode implements Parcelable {

    private String episode_id;
    private String title;
    private String resume;
    private String illustration;

    private Date programmation;

    private String serie_id;

    private int views;
    private boolean alert;

    public Episode(String episode_id, String title) {
        this.episode_id = episode_id;
        this.title = title;
    }

    public Episode() {
    }

    protected Episode(Parcel in) {
        episode_id = in.readString();
        title = in.readString();
        resume = in.readString();
        illustration = in.readString();
        serie_id = in.readString();
        views = in.readInt();
        alert = in.readByte() != 0;
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public String getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(String episode_id) {
        this.episode_id = episode_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getSerie_id() {
        return serie_id;
    }

    public void setSerie_id(String serie_id) {
        this.serie_id = serie_id;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public Date getProgrammation() {
        return programmation;
    }

    public void setProgrammation(String programmation) {
        try {
            this.programmation = SDF.parse(programmation);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(episode_id);
        parcel.writeString(title);
        parcel.writeString(resume);
        parcel.writeString(illustration);
        parcel.writeString(serie_id);
        parcel.writeInt(views);
        parcel.writeByte((byte) (alert ? 1 : 0));
    }
}
