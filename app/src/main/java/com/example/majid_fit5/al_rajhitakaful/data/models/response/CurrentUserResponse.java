package com.example.majid_fit5.al_rajhitakaful.data.models.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.data.models.user.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentUserResponse implements Parcelable
{

    @SerializedName("user")
    @Expose
    private User user;


    @SerializedName("current_order")
    @Expose
    private Order order;
    public final static Parcelable.Creator<CurrentUserResponse> CREATOR = new Creator<CurrentUserResponse>() {

        @SuppressWarnings({
                "unchecked"
        })
        public CurrentUserResponse createFromParcel(Parcel in) {
            return new CurrentUserResponse(in);
        }

        public CurrentUserResponse[] newArray(int size) {
            return (new CurrentUserResponse[size]);
        }

    }
            ;

    protected CurrentUserResponse(Parcel in) {
        this.user = ((User) in.readValue((User.class.getClassLoader())));
        this.order = ((Order) in.readValue((Order.class.getClassLoader())));
    }

    public CurrentUserResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(user);
        dest.writeValue(order);
    }

    public int describeContents() {
        return 0;
    }

}