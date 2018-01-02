package com.example.majid_fit5.al_rajhitakaful.data.models.provider;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Provider implements Parcelable
{
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("vehicle")
    @Expose
    private String vehicle;
    @SerializedName("eta")
    @Expose
    private Integer eta;
    public final static Parcelable.Creator<Provider> CREATOR = new Creator<Provider>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Provider createFromParcel(Parcel in) {
            return new Provider(in);
        }

        public Provider[] newArray(int size) {
            return (new Provider[size]);
        }

    }
            ;

    protected Provider(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.phoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.vehicle = ((String) in.readValue((String.class.getClassLoader())));
        this.eta = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Provider() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(phoneNumber);
        dest.writeValue(vehicle);
        dest.writeValue(eta);
    }

    public int describeContents() {
        return 0;
    }

}