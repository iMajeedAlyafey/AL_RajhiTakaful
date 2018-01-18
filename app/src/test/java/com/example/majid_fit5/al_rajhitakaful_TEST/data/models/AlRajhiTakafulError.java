package com.example.majid_fit5.al_rajhitakaful_TEST.data.models;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public class AlRajhiTakafulError {
    private int code;
    private String message="";

    public AlRajhiTakafulError(){

    }

    public AlRajhiTakafulError(int code, String message){
        this.code=code;
        this.message=message;
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
