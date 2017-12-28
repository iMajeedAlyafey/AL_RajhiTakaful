package com.example.majid_fit5.al_rajhitakaful.data;


import android.content.res.Resources;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OTPRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OrderRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;
import com.example.majid_fit5.al_rajhitakaful.utility.PrefUtility;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type","application/json")
                        .addHeader("Authorization", PrefUtility.getToken())
                        .addHeader("Accept","application/json")
                        .addHeader("Accept-Language","en")
                        .addHeader("App-Type","AlrajhiTakaful")
                        .addHeader("Platform","android")
                        .addHeader("App-Version","1.0.0").build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = clientBuilder.build();
        // Retrofit instantiation
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
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
        Call<CurrentUserResponse> call = mEndpoints.login(loginRequest);
        call.enqueue(new Callback<CurrentUserResponse>() {
            @Override
            public void onResponse(Call<CurrentUserResponse> call, Response<CurrentUserResponse> response) {
                if(response.isSuccessful()){
                   callback.onLoginResponse(response.body());
                } else{ // if there is a response, but response error code.
                    callback.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<CurrentUserResponse> call, Throwable t) {
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
        Call<Order> call = mEndpoints.createOrder(request);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    callback.onCreateOrderResponse(response.body());
                } else{ // if there is a response, but response error code.
                    callback.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                callback.onFailure(getError(10)); // err code 10 for Unknown errors.
            }
        });
    }

    //-------------------------------- getCuttentUser Method--------------------------------
    @Override
    public void getCurrentUser( final GetCurrentUserCallBack callBack) {
        Call<CurrentUserResponse> call = mEndpoints.getCurrentUser();
        call.enqueue(new Callback<CurrentUserResponse>() {
            @Override
            public void onResponse(Call<CurrentUserResponse> call, Response<CurrentUserResponse> response) {
                if (response.isSuccessful()){
                    callBack.onGetCurrentUser(response.body());
                }
                else{
                    callBack.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<CurrentUserResponse> call, Throwable t) {
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
        Call<Order> call = mEndpoints.getOrder(orderID);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()){
                    callBack.onGetOrder(response.body());
                }
                else {
                    callBack.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
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
                return new AlRajhiTakafulError(errCode, AlRajhiTakafulApplication.getInstance().getString(R.string.error_404));
        }
        return new AlRajhiTakafulError(errCode, Resources.getSystem().getString(R.string.get_currentuser_error));
    }
}
