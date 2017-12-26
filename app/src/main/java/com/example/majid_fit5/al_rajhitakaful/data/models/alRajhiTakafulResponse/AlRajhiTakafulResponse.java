package com.example.majid_fit5.al_rajhitakaful.data.models.alRajhiTakafulResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class AlRajhiTakafulResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("status")
    @Expose
    private String message;

    public AlRajhiTakafulResponse() {
    }

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
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
