package com.example.majid_fit5.al_rajhitakaful.utility;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class ValidationsUtility {

    public static <K> K checkNotNull(K reference){
        if(reference == null)
            throw new NullPointerException();
        return reference;
    }


    public static boolean isEmpty(Object in) {
        return in == null || in.toString().trim().matches("");
    }

    public static boolean isValidPhoneNumberLength(String phoneNumber) {
        if(phoneNumber.length()< 9 ) return false;
        return phoneNumber.length() >= 9; // 9 or 10 is ok

    }
}
