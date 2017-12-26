package com.example.majid_fit5.al_rajhitakaful.data.models.order;

import com.example.majid_fit5.al_rajhitakaful.data.models.provider.Provider;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentOrder {

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

}
