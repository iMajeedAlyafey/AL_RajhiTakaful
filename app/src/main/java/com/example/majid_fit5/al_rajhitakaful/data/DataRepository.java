package com.example.majid_fit5.al_rajhitakaful.data;

import android.support.annotation.NonNull;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

// This will class will be called form the presenters..
    // It implements Data Source interface to use its methods as APi for the presenters.

public class DataRepository implements DataSource {// Singleton class
    private static DataRepository INSTANCE = null;
    private final DataSource mRemoteDataSource;

    public static DataRepository getInstance(DataSource remoteDataSource){
        if(INSTANCE==null)
            INSTANCE= new DataRepository(remoteDataSource);
        return INSTANCE;
    }

    public DataRepository(@NonNull DataSource mRemoteDataSource){
        this.mRemoteDataSource=mRemoteDataSource;
    }

    @Override
    public void getCurrentUser(GetCurrentUserCallCack callCack) {
        mRemoteDataSource.getCurrentUser(callCack);
    }
}
