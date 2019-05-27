package com.example.majid_fit5.al_rajhitakaful.data.models.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/26/2017.
 */

public class OrderRequest implements Parcelable {

    @SerializedName("lat")
    @Expose
    private float lat;

    @SerializedName("lng")
    @Expose
    private float lng;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public OrderRequest(float lat, float lng){
        this.lat=lat;
        this.lng=lng;
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.lat);
        dest.writeFloat(this.lng);
    }

    public OrderRequest(Parcel in){
        this.lat=in.readFloat();
        this.lng=in.readFloat();
    }
    public static final Parcelable.Creator<OrderRequest> CREATOR = new Creator<OrderRequest>() {
        @Override
        public OrderRequest createFromParcel(Parcel source) {
            return new OrderRequest(source);
        }
        @Override
        public OrderRequest[] newArray(int size) {
            return new OrderRequest[size];
        }
    };
}
