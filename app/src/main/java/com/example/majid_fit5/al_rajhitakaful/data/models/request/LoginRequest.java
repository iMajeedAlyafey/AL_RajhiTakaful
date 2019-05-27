package com.example.majid_fit5.al_rajhitakaful.data.models.request;

import android.os.Parcel;
import android.os.Parcelable;


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
    String code;


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LoginRequest(){
    }
    public LoginRequest(String phone_number,String code){
        this.phone_number=phone_number;
        this.code =code;
    }
    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone_number);
        dest.writeString(this.code);
    }

    public LoginRequest(Parcel in){
        this.phone_number=in.readString();
        this.code =in.readString();
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
