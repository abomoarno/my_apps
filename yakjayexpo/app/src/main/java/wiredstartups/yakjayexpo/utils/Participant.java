package wiredstartups.yakjayexpo.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Participant implements Parcelable {

    private String user_code;
    private String name;
    private String email;
    private String phone;
    private String ville;
    private String gender;
    private String qr_message;

    public Participant(String user_code) {
        this.user_code = user_code;
    }

    protected Participant(Parcel in) {
        user_code = in.readString();
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        ville = in.readString();
        gender = in.readString();
        qr_message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_code);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(ville);
        dest.writeString(gender);
        dest.writeString(qr_message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Participant> CREATOR = new Creator<Participant>() {
        @Override
        public Participant createFromParcel(Parcel in) {
            return new Participant(in);
        }

        @Override
        public Participant[] newArray(int size) {
            return new Participant[size];
        }
    };

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getQr_message() {
        return qr_message;
    }

    public void setQr_message(String qr_message) {
        this.qr_message = qr_message;
    }
}