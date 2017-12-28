package com.example.majid_fit5.al_rajhitakaful.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;

/**
 * Created by BASH on 12/27/2017.
 */

public class PrefUtility {

    public static String getToken(){

        //get token
        return "token";
    }
    public static boolean isLogedIn(){
        SharedPreferences pref = AlRajhiTakafulApplication.getInstance().getSharedPreferences(Constants.USER_PREFERENCE,Context.MODE_PRIVATE);
        if (pref.getString(Constants.TOKEN,"").equals("") || pref.getString(Constants.TOKEN,null).equals(null)){
            return false;
        }
        return true;

    }
}
