package com.example.majid_fit5.al_rajhitakaful_TEST.data.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class    User implements Parcelable
{

    @SerializedName("auth_token")
    @Expose
    private String authToken;
    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
            ;

    protected User(Parcel in) {
        this.authToken = ((String) in.readValue((String.class.getClassLoader())));
    }

    public User() {
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(authToken);
    }

    public int describeContents() {
        return 0;
    }

}