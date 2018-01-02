package com.example.majid_fit5.al_rajhitakaful.data;

import android.net.Uri;
import android.support.annotation.NonNull;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OTPRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OrderRequest;

import retrofit2.Call;

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
    public void OtpCall(String phoneNumber, OTPCallback callback) {
        mRemoteDataSource.OtpCall(phoneNumber,callback);
    }

    @Override
    public void login(LoginRequest loginRequest, LoginCallback callback) {
        mRemoteDataSource.login(loginRequest,callback);

    }

    @Override
    public void logout(LogoutCallback logoutCallback) {
        mRemoteDataSource.logout(logoutCallback);

    }

    @Override
    public void createOrder(OrderRequest request, CreateOrderCallback callback) {
        mRemoteDataSource.createOrder(request,callback);
    }

    @Override
    public void getCurrentUser(GetCurrentUserCallBack callCack) {
        mRemoteDataSource.getCurrentUser(callCack);
    }

    @Override
    public void cancelOrderC(String orderID, CancelOrderCallBack callBack) {
        mRemoteDataSource.cancelOrderC(orderID,callBack);
    }

    @Override
    public void getOrder(String orderID, GetOrderCallBack callBack) {
        mRemoteDataSource.getOrder(orderID,callBack);
    }

    @Override
    public void uploadPhoto(String orderID, Uri filePath, UploadPhoto callback) {
        mRemoteDataSource.uploadPhoto(orderID,filePath,callback);
    }


    public static void destroyInstance() {
            INSTANCE=null;
    }
}
