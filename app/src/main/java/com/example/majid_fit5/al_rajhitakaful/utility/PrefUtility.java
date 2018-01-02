package com.example.majid_fit5.al_rajhitakaful.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
/**
 * Created by BASH on 12/27/2017.
 */

public class PrefUtility {

    private static SharedPreferences preferences;

    public static boolean isLogedIn() {
        //review shared preference
        SharedPreferences pref = AlRajhiTakafulApplication.getInstance().getSharedPreferences(Constants.USER_PREFERENCE, Context.MODE_PRIVATE);
        String tokenToCheck = pref.getString(Constants.TOKEN, null);
        if (tokenToCheck != null && !tokenToCheck.trim().isEmpty()) {
            return true;
        }
        return false;
    }
    public static String getToken(Context context){
        preferences= context.getSharedPreferences(Constants.USER_PREFERENCE,Context.MODE_PRIVATE);
        Log.e(Constants.TOKEN,"authToken "+preferences.getString(Constants.TOKEN,""));
        return "Token "+preferences.getString(Constants.TOKEN,"");
    }

    public static void destroyToken(Context context){
        preferences= context.getSharedPreferences(Constants.USER_PREFERENCE,Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    /**
     * This function saves the user token for the first time when logging successfully.
     * @param context may be an activity or fragment.
     * @param authToken
     */
    public static void saveUserAuthToken(Context context, String authToken) {
        preferences= context.getSharedPreferences(Constants.USER_PREFERENCE,Context.MODE_PRIVATE);
        preferences.edit().putString(Constants.TOKEN,authToken).apply();
        Log.d(Constants.TOKEN,authToken+" is SAVED successfully");
    }
}
