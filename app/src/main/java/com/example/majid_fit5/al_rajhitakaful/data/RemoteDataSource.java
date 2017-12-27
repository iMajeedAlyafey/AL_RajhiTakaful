package com.example.majid_fit5.al_rajhitakaful.data;


import android.app.Application;
import android.content.res.Resources;

import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.data.models.alRajhiTakafulError.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.alRajhiTakafulResponse.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.CurrentOrder;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OTPRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OrderRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.CurrentOrder;
import com.example.majid_fit5.al_rajhitakaful.data.models.user.CurrentUser;

import okhttp3.ResponseBody;
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


    @Override
    public void OtpCall(OTPRequest request, final OTPCallback callback) {
        Call<AlRajhiTakafulResponse> call = mEndpoints.otp(request);
        call.enqueue(new Callback<AlRajhiTakafulResponse>() {
            @Override
            public void onResponse(Call<AlRajhiTakafulResponse> call, Response<AlRajhiTakafulResponse> response) {
                if(response.isSuccessful()){
                    callback.onOTPResponse(response.body());
                } else{ // if there is a response, but response error code.
                    callback.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<AlRajhiTakafulResponse> call, Throwable t) {
                callback.onFailure(getError(10)); // err code 10 for Unknown errors.
            }
        });
    }

    @Override
    public void login(LoginRequest loginRequest, final LoginCallback callback) {
        Call<CurrentUser> call = mEndpoints.login(loginRequest);
        call.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                if(response.isSuccessful()){
                   callback.onLoginResponse(response.body());
                } else{ // if there is a response, but response error code.
                    callback.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {
                callback.onFailure(getError(10)); // err code 10 for Unknown errors.
            }
        });
    }

    @Override
    public void logout(final LogoutCallback callback) {
        Call<AlRajhiTakafulResponse> call = mEndpoints.logout();
        call.enqueue(new Callback<AlRajhiTakafulResponse>() {
            @Override
            public void onResponse(Call<AlRajhiTakafulResponse> call, Response<AlRajhiTakafulResponse> response) {
                if(response.isSuccessful()){
                    callback.onLogoutResponse(response.body());
                } else{ // if there is a response, but response error code.
                    callback.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<AlRajhiTakafulResponse> call, Throwable t) {
                callback.onFailure(getError(10)); // err code 10 for Unknown errors.
            }
        });
    }

    @Override
    public void createOrder(final OrderRequest request, final CreateOrderCallback callback) {
        Call<CurrentOrder> call = mEndpoints.createOrder(request);
        call.enqueue(new Callback<CurrentOrder>() {
            @Override
            public void onResponse(Call<CurrentOrder> call, Response<CurrentOrder> response) {
                if(response.isSuccessful()){
                    callback.onCreateOrderResponse(response.body());
                } else{ // if there is a response, but response error code.
                    callback.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<CurrentOrder> call, Throwable t) {
                callback.onFailure(getError(10)); // err code 10 for Unknown errors.
            }
        });
    }

    //-------------------------------- getCuttentUser Method--------------------------------
    @Override
    public void getCurrentUser( final GetCurrentUserCallBack callBack) {
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

    @Override
    public void cancelOrderC(String orderID, final CancelOrderCallBack callBack) {
        Call<Void> call = mEndpoints.CancelOrder(orderID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    callBack.onOrderCanceled();
                }
                else {
                    callBack.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailure(getError(10));
            }
        });
    }

    //---------------------------------------------------------------------------------------------
    @Override
    public void getOrder(String orderID, final GetOrderCallBack callBack) {
        Call<CurrentOrder> call = mEndpoints.getOrder(orderID);
        call.enqueue(new Callback<CurrentOrder>() {
            @Override
            public void onResponse(Call<CurrentOrder> call, Response<CurrentOrder> response) {
                if (response.isSuccessful()){
                    callBack.onGetOrder(response.body());
                }
                else {
                    callBack.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<CurrentOrder> call, Throwable t) {
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
