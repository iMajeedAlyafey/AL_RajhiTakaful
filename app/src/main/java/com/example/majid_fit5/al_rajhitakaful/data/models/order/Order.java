package com.example.majid_fit5.al_rajhitakaful.data.models.order;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.majid_fit5.al_rajhitakaful.data.models.provider.Provider;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("provider")
    @Expose
    private Provider provider;
    public final static Parcelable.Creator<Order> CREATOR = new Creator<Order>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return (new Order[size]);
        }
    }
            ;

    protected Order(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.provider = ((Provider) in.readValue((Provider.class.getClassLoader())));
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(provider);
    }

    public int describeContents() {
        return 0;
    }

}