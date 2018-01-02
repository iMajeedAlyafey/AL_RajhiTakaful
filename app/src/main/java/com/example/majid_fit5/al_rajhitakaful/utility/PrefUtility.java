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
        return "Token "+"N6jiohwNmneubuZfmzH2";
    }
    public static boolean isLogedIn(){
        //review shared preference
        SharedPreferences pref = AlRajhiTakafulApplication.getInstance().getSharedPreferences(Constants.USER_PREFERENCE,Context.MODE_PRIVATE);
        String tokenToCheck = pref.getString(Constants.TOKEN,null);
        if (tokenToCheck != null && !tokenToCheck.trim().isEmpty()){
            return true;
        }
        return false;

    }
}
