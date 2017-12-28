package com.example.majid_fit5.al_rajhitakaful;

import android.app.Application;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/28/2017.
 */

public class AlRajhiTakafulApplication extends Application {
    private static AlRajhiTakafulApplication INSTANCE = null;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;
    }

    public static AlRajhiTakafulApplication getInstance(){
        return INSTANCE;
    }
}
