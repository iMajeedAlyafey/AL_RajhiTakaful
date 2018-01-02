package com.example.majid_fit5.al_rajhitakaful.base;

import com.example.majid_fit5.al_rajhitakaful.data.DataRepository;
import com.example.majid_fit5.al_rajhitakaful.data.RemoteDataSource;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/25/2017.
 */

public class Injection {
    public static DataRepository provideDataRepository() {
        return DataRepository.getInstance(RemoteDataSource.getInstance());
    }
    public static void deleteProvidedDataRepository() {
        DataRepository.destroyInstance();
        RemoteDataSource.destroyInstance();
    }
}
