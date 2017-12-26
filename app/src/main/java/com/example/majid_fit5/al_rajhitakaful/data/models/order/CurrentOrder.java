package com.example.majid_fit5.al_rajhitakaful.data.models.order;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.majid_fit5.al_rajhitakaful.data.models.provider.Provider;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentOrder implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("provider")
    @Expose
    private Provider provider;
    public final static Parcelable.Creator<CurrentOrder> CREATOR = new Creator<CurrentOrder>() {

        @SuppressWarnings({
                "unchecked"
        })
        public CurrentOrder createFromParcel(Parcel in) {
            return new CurrentOrder(in);
        }

        public CurrentOrder[] newArray(int size) {
            return (new CurrentOrder[size]);
        }
    }
            ;

    protected CurrentOrder(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.provider = ((Provider) in.readValue((Provider.class.getClassLoader())));
    }

    public CurrentOrder() {
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