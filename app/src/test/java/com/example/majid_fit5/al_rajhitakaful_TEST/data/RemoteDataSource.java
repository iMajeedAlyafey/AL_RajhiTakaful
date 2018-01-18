package com.example.majid_fit5.al_rajhitakaful_TEST.data;

import android.accounts.NetworkErrorException;
import android.content.Intent;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.login.LoginActivity;
import com.example.majid_fit5.al_rajhitakaful.utility.PrefUtility;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.request.OrderRequest;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.response.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.response.CurrentUserResponse;

import org.mockito.internal.matchers.Or;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    private static String BASE_URL = "https://sandbox.morniksa.com/api/alrajhi_takaful/";//"https://www.morniksa.com/api/"
    private ApiEndPoints mEndpoints;
    private static RemoteDataSource INSTANCE = null;
    OkHttpClient.Builder clientBuilder;

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSource();
        return INSTANCE;
    }

    public void changeTokenInRequestHeader(final String token){
        clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type","multipart/form-data")
                        .addHeader("Authorization","Token "+token)
                        .addHeader("Accept","application/json")
                        .addHeader("Accept-Language","en")
                        .addHeader("App-Type","AlrajhiTakaful")
                        .addHeader("Platform","android")
                        .addHeader("App-Version","1.0.0").build();
                return chain.proceed(request);
            }
    });
        instantiateRetrofit();
    }

    public void prepareDefaultClientBuilder(){
        clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type","multipart/form-data")
                        .addHeader("Authorization","Token "+"")
                        .addHeader("Accept","application/json")
                        .addHeader("Accept-Language","en")
                        .addHeader("App-Type","AlrajhiTakaful")
                        .addHeader("Platform","android")
                        .addHeader("App-Version","1.0.0").build();
                return chain.proceed(request);
            }
        });
        instantiateRetrofit();
    }

    public void instantiateRetrofit(){
        OkHttpClient client = clientBuilder.build();
        // Retrofit instantiation
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mEndpoints = retrofit.create(ApiEndPoints.class);
    }

    private RemoteDataSource() {
        prepareDefaultClientBuilder();
    }
    @Override
    public void OtpCall(String phoneNumber, final OTPCallback callback) {

        Call<AlRajhiTakafulResponse> call = mEndpoints.otp(phoneNumber);

        call.enqueue(new Callback<AlRajhiTakafulResponse>() {
            @Override
            public void onResponse(Call<AlRajhiTakafulResponse> call, Response<AlRajhiTakafulResponse> response) {
                if(response.isSuccessful()){
                    callback.onOTPResponse(response);
                } else{ // if there is a response, but response error code.
                    callback.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<AlRajhiTakafulResponse> call, Throwable t) {
                callback.onFailure(getError(t)); // err code 10 for Unknown errors.
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
                    callback.onLoginResponse(response.body()); // response.body() from type CurrentUserResponse
                } else{ // if there is a response, but response error code.
                    callback.onFailure(getError(response.code()));
                }
            }

            @Override
            public void onFailure(Call<CurrentUserResponse> call, Throwable t) {
                callback.onFailure(getError(t)); // err code 10 for Unknown errors.
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
                    callback.onLogoutResponse(response);
                } else{ // if there is a response, but response error code.
                    callback.onFailure(getError(response.code()));
                }
            }
            @Override
            public void onFailure(Call<AlRajhiTakafulResponse> call, Throwable t) {
                callback.onFailure(getError(t)); // err code 10 for Unknown errors.
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
                callback.onFailure(getError(t)); // err code 10 for Unknown errors.
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
                callBack.onFailure(getError(t));
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
                callBack.onFailure(getError(t));
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
                t.printStackTrace();
                callBack.onFailure(getError(t));
            }
        });
    }

    @Override
    public void uploadPhoto(String orderID, String filePath, final UploadPhoto callback) {

        File file = new File(filePath);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", "image", mFile);

        Call<Order> call = mEndpoints.uploadPhoto(orderID,fileToUpload);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    callback.onUploadPhoto(response.body());
                }else{
                    callback.onFailure(getError(response.code()));
                }
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                callback.onFailure(getError(t));

            }
        });


    }

    //-----------handling error 2----------------------------------------------------------------------
    private AlRajhiTakafulError getError(int errCode) {
        switch (errCode) {
            case 401:
                return new AlRajhiTakafulError(errCode, "error_401");
            case 404:
                return new AlRajhiTakafulError(errCode,"error_404");
            case 400:
                return new AlRajhiTakafulError(errCode, "error_400");
            case 503:
                return new AlRajhiTakafulError(errCode, "error_503");
            case 422: // unprocessable entity.
                return new AlRajhiTakafulError(errCode, "error_422");
        }
        return new AlRajhiTakafulError(errCode, "10");
    }

    // ----------------- handling Throwable error --------------------------
    private AlRajhiTakafulError getError(Object anonymous) {
        AlRajhiTakafulError error ;
        if (anonymous instanceof NetworkErrorException
                || anonymous instanceof ConnectException
                || anonymous instanceof UnknownHostException) {
            // Network issue
            error = new AlRajhiTakafulError();
            error.setCode(503);
            error.setMessage(
                    AlRajhiTakafulApplication.getInstance().getString(R.string.error_503));
        } else if (anonymous instanceof SocketTimeoutException) {
            error = new AlRajhiTakafulError();
            error.setCode(408);
            error.setMessage(
                    AlRajhiTakafulApplication.getInstance().getString(R.string.error_408));
        }else {
            error = new AlRajhiTakafulError();
            error.setCode(10);
            error.setMessage(
                    AlRajhiTakafulApplication.getInstance().getString(R.string.msg_unknown_error));
        }
        return error;
    }

    public static void destroyInstance() {
        INSTANCE=null;
    }

    public Order getOrder() {
        return null;
    }
}