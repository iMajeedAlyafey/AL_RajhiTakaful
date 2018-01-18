package com.example.majid_fit5.al_rajhitakaful_TEST.data.models.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.provider.Provider;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order implements Parcelable {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("localized_status")
    @Expose
    private String localizedStatus;

    @SerializedName("provider")
    @Expose
    private Provider provider;

    public final static Creator<Order> CREATOR = new Creator<Order>() {


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
    private final static long serialVersionUID = 7294689573040419109L;

    protected Order(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.localizedStatus = ((String) in.readValue((String.class.getClassLoader())));
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocalizedStatus() {
        return localizedStatus;
    }

    public void setLocalizedStatus(String localizedStatus) {
        this.localizedStatus = localizedStatus;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(status);
        dest.writeValue(localizedStatus);
        dest.writeValue(provider);
    }

    public int describeContents() {
        return 0;
    }


}