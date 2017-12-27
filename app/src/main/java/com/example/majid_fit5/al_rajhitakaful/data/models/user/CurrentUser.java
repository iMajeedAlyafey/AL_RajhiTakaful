package com.example.majid_fit5.al_rajhitakaful.data.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentUser implements Parcelable
{

    @SerializedName("user")
    @Expose
    private User user;


    @SerializedName("current_order")
    @Expose
    private Order currentOrder;
    public final static Parcelable.Creator<CurrentUser> CREATOR = new Creator<CurrentUser>() {

        @SuppressWarnings({
                "unchecked"
        })
        public CurrentUser createFromParcel(Parcel in) {
            return new CurrentUser(in);
        }

        public CurrentUser[] newArray(int size) {
            return (new CurrentUser[size]);
        }

    }
            ;

    protected CurrentUser(Parcel in) {
        this.user = ((User) in.readValue((User.class.getClassLoader())));
        this.currentOrder = ((Order) in.readValue((Order.class.getClassLoader())));
    }

    public CurrentUser() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(user);
        dest.writeValue(currentOrder);
    }

    public int describeContents() {
        return 0;
    }

}