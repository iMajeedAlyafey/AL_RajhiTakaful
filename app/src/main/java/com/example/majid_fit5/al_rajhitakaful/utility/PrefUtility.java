package com.example.majid_fit5.al_rajhitakaful.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;

/**
 * Created by BASH on 12/27/2017.
 */

public class PrefUtility {

    private static SharedPreferences preferences;

    public static String getToken(Context context){
        preferences= context.getSharedPreferences(Constants.USER_PREFERENCE,Context.MODE_PRIVATE);
        Log.e(Constants.TOKEN,"authToken "+preferences.getString(Constants.TOKEN,""));
        return "Token "+preferences.getString(Constants.TOKEN,"");
    }


    /**
     * This function saves the user token for the first time when logging successfully.
     * @param context may be an activity or fragment.
     * @param authToken
     */
    public static void saveUserAuthToken(Context context, String authToken) {
        preferences= context.getSharedPreferences(Constants.USER_PREFERENCE,Context.MODE_PRIVATE);
        preferences.edit().putString(Constants.TOKEN,authToken).commit();
        Log.d(Constants.TOKEN,authToken+" is SAVED successfully");
    }
}
