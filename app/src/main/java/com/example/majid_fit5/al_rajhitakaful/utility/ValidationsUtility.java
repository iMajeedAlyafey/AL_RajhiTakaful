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


}
