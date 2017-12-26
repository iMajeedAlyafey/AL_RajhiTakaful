package com.example.majid_fit5.al_rajhitakaful.data;


import android.app.Application;
import android.content.res.Resources;

import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.data.models.alRajhiTakafulError.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.user.CurrentUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

// Singleton class that responsible on firing retrofit calls.

public class RemoteDataSource implements DataSource {
    private static String BASE_URL = "https://sandbox.morniksa.com/api/v2/";//"https://www.morniksa.com/api/"
    private ApiEndPoints mEndpoints;
    private static RemoteDataSource INSTANCE = null;

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSource();
        return INSTANCE;
    }

    private RemoteDataSource() {
        // Retrofit instantiation
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mEndpoints = retrofit.create(ApiEndPoints.class);
    }

//-------------------------------- getCuttentUser Method--------------------------------
    @Override
    public void getCurrentUser( final GetCurrentUserCallCack callBack) {
        Call<CurrentUser> call = mEndpoints.getCurrentUser();
        call.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                if (response.isSuccessful()){
                    callBack.onGetCurrentUser(response.body());
                }
                else{
                    callBack.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {
                callBack.onFailure(getError(10));
            }
        });
    }
    //-----------handling error----------------------------------------------------------------------
    private AlRajhiTakafulError getError(int errCode) {
        switch (errCode) {
            case 401:
                return new AlRajhiTakafulError(errCode, Resources.getSystem().getString(R.string.error_401));
            case 404:
                return new AlRajhiTakafulError(errCode,Resources.getSystem().getString(R.string.error_404));
        }
        return new AlRajhiTakafulError(errCode, Resources.getSystem().getString(R.string.get_currentuser_error));
    }
}
