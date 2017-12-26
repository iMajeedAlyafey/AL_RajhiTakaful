package com.example.majid_fit5.al_rajhitakaful.data.models.alRajhiTakafulError;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public class AlRajhiTakafulError implements Parcelable {
    private int code;
    private String message="";


    public AlRajhiTakafulError(int code, String message){
        this.code=code;
        this.message=message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
    }
    public AlRajhiTakafulError(){
    }

    public AlRajhiTakafulError(Parcel in){
        this.code=in.readInt();
        this.message=in.readString();
    }
    public static final Parcelable.Creator<AlRajhiTakafulError> CREATOR = new Creator<AlRajhiTakafulError>() {

        @Override
        public AlRajhiTakafulError createFromParcel(Parcel source) {
            return new AlRajhiTakafulError(source);
        }

        @Override
        public AlRajhiTakafulError[] newArray(int size) {
            return new AlRajhiTakafulError[size];
        }
    };




}