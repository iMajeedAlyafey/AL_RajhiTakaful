package com.example.majid_fit5.al_rajhitakaful.utility;

import android.support.annotation.NonNull;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import com.example.majid_fit5.al_rajhitakaful.login.mobilephoneinsertion.MobilePhoneInsertionFragment;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class ActivityUtility  {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull MobilePhoneInsertionFragment mFragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, mFragment).commit(); // need commit
    }
}
