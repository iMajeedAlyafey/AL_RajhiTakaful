package com.example.majid_fit5.al_rajhitakaful.base;

import android.support.annotation.NonNull;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public interface BasePresenter <V extends BaseView> {
    void onBind(@NonNull V view);
    void onDestroy();
}
