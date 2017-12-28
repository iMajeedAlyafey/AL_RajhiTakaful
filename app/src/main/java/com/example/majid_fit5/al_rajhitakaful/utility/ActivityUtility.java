package com.example.majid_fit5.al_rajhitakaful.utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;

import com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion.MobilePhoneInsertionFragment;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class ActivityUtility  {

    /**
     * This is to replace in fragment in frame layout that exists in the passed activity.
     * @param fragmentManager
     * @param mFragment
     * @param frameId
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment mFragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, mFragment).commit(); // need commit
    }

    /**
     * This is shared function to go to any activity.
     * @param context
     * @param Class
     * @param bundle
     */
    public static void goToActivity(Context context, Class<?> Class, Bundle bundle) {
        Intent intent = new Intent(context,Class);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
