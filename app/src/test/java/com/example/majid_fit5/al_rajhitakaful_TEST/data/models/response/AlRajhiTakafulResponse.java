package com.example.majid_fit5.al_rajhitakaful_TEST.data.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AlRajhiTakafulResponse implements Parcelable {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("status")
    @Expose
    private String status;


    public AlRajhiTakafulResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return status;
    }

    public void setMessage(String message) {
        this.status = message;
    }

    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.status);
    }
    public AlRajhiTakafulResponse(){
    }

    public AlRajhiTakafulResponse (Parcel in){
        this.code=in.readInt();
        this.status=in.readString();
    }
    public static final Creator<AlRajhiTakafulResponse> CREATOR = new Creator<AlRajhiTakafulResponse>() {

        @Override
        public AlRajhiTakafulResponse createFromParcel(Parcel source) {
            return new AlRajhiTakafulResponse(source);
        }

        @Override
        public AlRajhiTakafulResponse[] newArray(int size) {
            return new AlRajhiTakafulResponse[size];
        }
    };
}
