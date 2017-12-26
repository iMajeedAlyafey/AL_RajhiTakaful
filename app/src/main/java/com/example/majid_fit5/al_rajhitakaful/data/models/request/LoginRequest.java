package com.example.majid_fit5.al_rajhitakaful.data.models.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.majid_fit5.al_rajhitakaful.data.models.alRajhiTakafulError.AlRajhiTakafulError;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/26/2017.
 */

public class LoginRequest implements Parcelable {

    @SerializedName("phone_number")
    @Expose
    String phone_number;

    @SerializedName("pin")
    @Expose
    String pin;


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public LoginRequest(){
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone_number);
        dest.writeString(this.pin);
    }

    public LoginRequest(Parcel in){
        this.phone_number=in.readString();
        this.pin=in.readString();
    }
    public static final Parcelable.Creator<LoginRequest> CREATOR = new Creator<LoginRequest>() {

        @Override
        public LoginRequest createFromParcel(Parcel source) {
            return new LoginRequest(source);
        }

        @Override
        public LoginRequest[] newArray(int size) {
            return new LoginRequest[size];
        }
    };



}
