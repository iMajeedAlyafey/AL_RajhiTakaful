package com.example.majid_fit5.al_rajhitakaful_TEST.data.models.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/26/2017.
 */

public class OTPRequest implements Parcelable {
    @SerializedName("phone_number")
    @Expose
    private String phone;

    public OTPRequest() {
    }

    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone);
    }

    protected OTPRequest(Parcel in) {
        this.phone = in.readString();
    }

    public static final Creator<OTPRequest> CREATOR = new Creator<OTPRequest>() {
        @Override
        public OTPRequest createFromParcel(Parcel source) {
            return new OTPRequest(source);
        }

        @Override
        public OTPRequest[] newArray(int size) {
            return new OTPRequest[size];
        }
    };
}
