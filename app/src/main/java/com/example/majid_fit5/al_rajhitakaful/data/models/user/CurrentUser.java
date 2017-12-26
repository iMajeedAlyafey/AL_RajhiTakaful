package com.example.majid_fit5.al_rajhitakaful.data.models.user;

import com.example.majid_fit5.al_rajhitakaful.data.models.order.CurrentOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentUser {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("current_order")
    @Expose
    private CurrentOrder currentOrder;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CurrentOrder getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(CurrentOrder currentOrder) {
        this.currentOrder = currentOrder;
    }

}