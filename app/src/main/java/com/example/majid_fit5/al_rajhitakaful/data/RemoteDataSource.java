package com.example.majid_fit5.al_rajhitakaful.data;


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
/*
    // My Part
    @Override
    public void getBlogs(String url, final GetBlogsCallBack callBack) {
        Call<List<Blog>> call= mEndpoints.getBlogs(url);
        call.enqueue(new Callback<List<Blog>>() {
            @Override
            public void onResponse(Call<List<Blog>> call, Response<List<Blog>> response) {
                if(response.isSuccessful()){
                    callBack.onGetBlogs(response.body());
                }else{
                    callBack.onFailure(getError(response.code()));
                }
            }
            @Override
            public void onFailure(Call<List<Blog>> call, Throwable t) {
                callBack.onFailure(getError(4077));
            }
        });
    }*/


}
