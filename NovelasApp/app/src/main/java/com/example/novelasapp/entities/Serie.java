package com.example.novelasapp.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Serie implements Parcelable {

    private String serie_id;
    private String title;
    private String description;
    private String illustration;


    public Serie(String serie_id, String title) {
        this.serie_id = serie_id;
        this.title = title;
    }

    public Serie() {
    }

    protected Serie(Parcel in) {
        serie_id = in.readString();
        title = in.readString();
        description = in.readString();
        illustration = in.readString();
    }

    public static final Creator<Serie> CREATOR = new Creator<Serie>() {
        @Override
        public Serie createFromParcel(Parcel in) {
            return new Serie(in);
        }

        @Override
        public Serie[] newArray(int size) {
            return new Serie[size];
        }
    };

    public String getSerie_id() {
        return serie_id;
    }

    public void setSerie_id(String serie_id) {
        this.serie_id = serie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(serie_id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(illustration);
    }
}
